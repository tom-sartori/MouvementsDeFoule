package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class Salle {
    private double largeur;
    private double hauteur;
    private List<Personne> listPersonnes;
    private List<Sortie> listSorties;
    private List<Obstacle> listObstacles;
    private Timeline loop;
    private Graphe graphe;

    private ControllerSalle cSalle;


    public Salle(double lar, double hau) {  // Créée une salle rectangle avec une marge
        this.largeur = lar;
        this.hauteur = hau;
        this.listPersonnes = new ArrayList<>(); // HashList surement apres
        this.listSorties = new ArrayList<>();
        this.listObstacles = new ArrayList<Obstacle>();

        graphe = new Graphe(this);
    }

    // Permet d'initialiser le controller de la salle
    // Créée les controllers des obstacles et sprties, puis les ajoutent au controller de la salle
    public ControllerSalle afficher() {
        cSalle = new ControllerSalle(this);

        for (Obstacle obstacle : listObstacles)
            cSalle.afficherControllerObstacle(obstacle.afficher());

        for (Sortie sortie : listSorties)
            cSalle.afficherSortie(sortie.afficher());

        for (Personne personne : listPersonnes)
            cSalle.afficherPersonne(personne.afficher());

        return cSalle;
    }

    // Permet d'ajouter au controller de la salle, les controllers de tous les obstacles et sorties.
    // Si afficher() a déjà été appelé, ceci superpose les controllers sur ceux deja existants.
    public void refreshAffichage() {
        for (Obstacle obstacle : listObstacles)
            cSalle.afficherControllerObstacle(obstacle.afficher());

        for (Sortie sortie : listSorties)
            cSalle.afficherSortie(sortie.afficher());
    }


    public void addObstacle (Obstacle obstacle){
        listObstacles.add(obstacle);
    }

    // Permet d'ajouter une sortie à la salle et la place correctement
    // Modifie x1 y1 x2 y2 de la sortie correspondante pour lui donner uniquement les coordonnées utiles
    // (donc pas les coords exterrieurs à la salle)
    public void addSortie (int mur, int largeurPorte, int distanceOrigine) {
        Point point1 = new Point();
        Point point2 = new Point();
        point1.setEstSortie(true);
        point2.setEstSortie(true);

        if (mur == 1) {     // Mur haut
            point1.setPoint(distanceOrigine, 0);
            point2.setPoint( distanceOrigine + largeurPorte, 0);
        }
        else if (mur == 2) {     // Mur droit
            point1.setPoint(largeur, distanceOrigine);
            point2.setPoint(largeur,distanceOrigine + largeurPorte);
        }
        else if (mur == 3) {     // Mur bas
            point1.setPoint(distanceOrigine, hauteur);
            point2.setPoint(distanceOrigine + largeurPorte, hauteur);
        }
        else if (mur == 4) {     // Mur gauche
            point1.setPoint(0, distanceOrigine);
            point2.setPoint(0,distanceOrigine + largeurPorte);
        }
        else
            System.out.println("Salle, addSortie, problème de mur. ");

        List<Point> list = new ArrayList<>();
        list.add(point1);
        list.add(point2);

        listSorties.add(new Sortie(list));
    }

    public void addPersonne (Personne personne) {
        listPersonnes.add(personne);
    }

    public void addRandomPersonnes (int n) {
        for (int i = 0; i < n; i++) {
            boolean dansObstacle;
            double x, y;
            do {
                dansObstacle = false;
                Random ran = new Random();
                x = ran.nextInt(1000);
                y = ran.nextInt(600);


                for (Obstacle obstacle : listObstacles) {
                    if (obstacle.estDansObstacle(new Point(x, y)))
                        dansObstacle = true;
                }
            }
            while (dansObstacle);
            System.out.println(x + " " + y);
            addPersonne(new Personne(x,y));
        }
    }

    public void removePersonne (Personne personne) {
        listPersonnes.remove(personne);
        if (listPersonnes.isEmpty() && loop != null)
            loop.stop();
        cSalle.retirerPersonne(personne);
    }

    public void removeAllPersonne(){
        while(!listPersonnes.isEmpty())
            removePersonne(listPersonnes.get(0));
    }


    public void demarrer() {
        initialisationGrapheBasique();

        if (!listPersonnes.isEmpty()) {
            for (Personne personne : listPersonnes) {   // Pour chaque personne de la salle
              
                //personne.setObjectif(this);
                //personne.setDxDyNormalise(personne.getObjectif());
                personne.setObjectifAvecRayon(this);
                personne.setDxDyNormalise(personne.getObjectifRayon());
            }

            Salle salle = this; // Pas sur de la propreté de cette ligne mais ne fonctionnait pas dans la timeline sans

            if (loop == null || loop.getStatus()==Status.STOPPED) {

                loop = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent arg) {

                        boolean colision;
                        int y;
                        for (int i = 0; i < listPersonnes.size(); i++) {
                            if (listPersonnes.get(i).estSorti(salle))
                                removePersonne(listPersonnes.get(i));
                            else {
                                if (listPersonnes.get(i).objectifAteint()) {
                                  
                                    //listPersonnes.get(i).setObjectif(salle);
                                    //listPersonnes.get(i).setDxDyNormalise(listPersonnes.get(i).getObjectif());
                                    listPersonnes.get(i).setObjectifAvecRayon(salle);
                                    listPersonnes.get(i).setDxDyNormalise(listPersonnes.get(i).getObjectifRayon());
                                }

                             else {
                                    listPersonnes.get(i).avancerRayon();
                                    cSalle.deplacerPersonne(listPersonnes.get(i));

                                }
                            }
                        }
                    }
                }));
                loop.setCycleCount(Timeline.INDEFINITE);
                loop.play();
            } else if (loop.getStatus() == Animation.Status.PAUSED) {
                loop.play();
            }
        }
    }

    public void demarrerAvecCollisions () {
        initialisationGrapheSansAffichage();

        if (!listPersonnes.isEmpty()) {
            for (Personne personne : listPersonnes) {   // Pour chaque personne de la salle
                personne.setObjectifAvecRayon(this);
                personne.setDxDyNormalise(personne.getObjectifRayon());
            }

            Salle salle = this; // Pas sur de la propreté de cette ligne mais ne fonctionnait pas dans la timeline sans

            if (loop == null || loop.getStatus()==Status.STOPPED) {

                loop = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent arg) {

                        boolean collision;
                        int y;

                        for (int i = 0; i < listPersonnes.size(); i++) {
                            System.out.println(listPersonnes.get(i).getObjectif());
                            if (listPersonnes.get(i).estSorti(salle))
                                removePersonne(listPersonnes.get(i));
                            else {
                                if (listPersonnes.get(i).objectifAteint()) {
                                    listPersonnes.get(i).setObjectifAvecRayon(salle);
                                    listPersonnes.get(i).setDxDyNormalise(listPersonnes.get(i).getObjectifRayon());
                                }
                                else {
                                    collision = false;
                                    y = 0;
                                    while (!collision && y < listPersonnes.size()) {
                                        collision = colision2personnes(listPersonnes.get(i), listPersonnes.get(y));
                                        y++;
                                    }
                                    if (!collision) {
                                        listPersonnes.get(i).avancerRayon();
                                        cSalle.deplacerPersonne(listPersonnes.get(i));
                                    }
                                }
                            }
                        }
                    }
                }));
                loop.setCycleCount(Timeline.INDEFINITE);
                loop.play();
            } else if (loop.getStatus() == Animation.Status.PAUSED) {
                loop.play();
            }
        }
    }

    public void pause(){
        if(loop != null && loop.getStatus() == Status.RUNNING){
            loop.pause();
        }
    }

    public boolean isRunning(){
        if(loop!=null && loop.getStatus()!= Animation.Status.STOPPED) return true;
        else return false;
    }


    // Utilisé à chaque fois qu'on appuie sur "play", donc qu'on lance démarer.
    // Pas possible d'initialiser avant car les obstacles, sorties et persos ne sont pas encore ajoutés au graphe
    public void initialisationGrapheSansAffichage () {
        graphe = new Graphe(this);
        graphe.creerTousLesPlusCourtsChemins();
    }


    // Utilisé à chaque fois qu'on appuie sur "play", donc qu'on lance démarer.
    // Pas possible d'initialiser avant car les obstacles, sorties et persos ne sont pas encore ajoutés au graphe
    public void initialisationGrapheAvecAffichage () {
        graphe = new Graphe(this);
        graphe.creerTousLesPlusCourtsChemins();
        cSalle.afficherGraphe(graphe.afficher());
    }

    public void initialisationGrapheBasique () {
        graphe = new Graphe(this);
        //graphe.creerPlusCourtChemin(graphe.getListePointsSorties().get(1));
        //graphe.afficherPrecedentsListPointsObstacles();
        graphe.creerTousLesPlusCourtsChemins();


        cSalle.afficherGraphe(graphe.afficher());
    }

    public void play(Boolean collisionActive){
        if(loop != null && loop.getStatus() == Status.PAUSED){
            loop.play();
        } else if(loop == null || loop.getStatus() == Status.STOPPED){
            if(collisionActive)
                demarrerAvecCollisions();
            else
                demarrer();
        }
    }


    // Ne prend pas en compte les obstacles
    public Point findPointSortiePlusProcheIndirecte(Point A) {
        double distance;

        Point courant = new Point();
        double distanceCourte = 1000000;
        Point plusProche = new Point();

        if (!listSorties.isEmpty()) {
            for (Sortie sortie : listSorties) {
                courant = sortie.findPointSortie(A);
                distance = MathsCalcule.distance(A, courant);

                if (distance < distanceCourte) {
                    distanceCourte = distance;
                    plusProche = new Point(courant);    // A check
                    //plusProche.setPrecedent(A);
                }
            }
        }
        else
            System.out.println("Pas de sorties dans la salle");
        //plusProche.setPrecedent(A);
        return plusProche;
    }

    public Point findPointSortiePlusProcheDirect(Point A) {
        double distance;

        double distanceCourte = 1000000;
        Point plusProche = null;
        Point courant;

        if (!listSorties.isEmpty()) {
            for (Sortie sortie : listSorties) {
                courant = sortie.findPointSortieDirect(this, A, 0); // Probleme ici
                if (courant != null) {
                    distance = MathsCalcule.distance(A, courant);

                    if (distance < distanceCourte) {
                        distanceCourte = distance;
                        plusProche = new Point(courant);    // A check
                    }
                }
            }
        }
        else
            System.out.println("Pas de sorties dans la salle");
        //plusProche.setPrecedent(new Point());
        return plusProche;
    }

    public boolean intersecObstacle(Point coordA,Point coordB) {
        boolean b = false;
        for(Obstacle obstacle: listObstacles) {
            if (MathsCalcule.coordSegments(coordA, coordB, obstacle).isEmpty()) {
                b = false;
            } else
                return true;
        }

        return b;
    }

    // Permet de renvoyer tous les points directes au Point A en parametre.
    // Ce point peut etre un perso car il est ajouté puis retiré de la liste principale.
    // Attention, le point directe le plus proche du perso est lui meme (donc à distance 0).
    public List<Point> getListePointsDirectes(Point A) {
        List<Point> listePointsDirectes = new ArrayList<>();

        Point pointSortieProche = findPointSortiePlusProcheDirect(A);
        if (pointSortieProche != null)
            listePointsDirectes.add(pointSortieProche);

        for (Obstacle obstacle : listObstacles) {
            for (Point point : obstacle.getListePoints()) {
                if (!intersecObstacle(A, point))
                    listePointsDirectes.add(point);
            }
        }
        return listePointsDirectes;
    }


    public boolean colision2personnes(Personne p, Personne compare){
        if (MathsCalcule.distance(p.getCoordCourant(), compare.getCoordCourant()) == 0){
            return false;
        } else if (MathsCalcule.distance(p.getCoordCourant(), compare.getCoordCourant()) <= p.getRayon()*2){
            return true;
        } else {
            return false;
        }
    }


    public List<Sortie> getListSorties() {
        return listSorties;
    }

    public Graphe getGraphe() {
        return graphe;
    }

    public List<Obstacle> getListObstacles(){
        return listObstacles;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public void setVitessePersonnes(double v){
        for(Personne personne : listPersonnes){
            personne.setVitesse(v);

        }
    }
}

