package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.util.Duration;
import sample.controller.ControllerSalle;

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
        this.listObstacles = new ArrayList<>();

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

        listSorties.add(new Sortie(point1, point2));
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
            //System.out.println(x + " " + y);
            Personne personne = new Personne(x,y);
            cSalle.afficherPersonne(personne.afficher());
            addPersonne(personne);
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

    public void demarrer(boolean collisionActive, boolean rayonActive) {
        initialisationGrapheSansAffichage();

        if (!listPersonnes.isEmpty()) {
            for (Personne personne : listPersonnes) {   // Pour chaque personne de la salle

                if(rayonActive){
                    personne.setObjectifRayon(this);
                    personne.setDxDyNormalise(personne.getObjectifRayon());
                } else{
                    personne.setObjectif(this);
                    personne.setDxDyNormalise(personne.getObjectif());
                }
            }

            Salle salle = this; // Pas sur de la propreté de cette ligne mais ne fonctionnait pas dans la timeline sans

            if (loop == null || loop.getStatus()==Status.STOPPED) {
                loop = new Timeline(new KeyFrame(Duration.millis(20), e -> runAction(salle, collisionActive, rayonActive)));
                loop.setCycleCount(Timeline.INDEFINITE);
                loop.play();
            } else if (loop.getStatus() == Animation.Status.PAUSED) {
                loop.play();
            }
        }
    }


    // Appelé à chaque frame.
    // A pour but de faire évoluer la personne au sein du système.
    public void runAction(Salle salle, boolean collisionActive, boolean rayonActive){       
        boolean bloque;
        int y;
        for (int i = 0; i < listPersonnes.size(); i++) {
            if (listPersonnes.get(i).estSorti())
                removePersonne(listPersonnes.get(i));
            else {
                if (listPersonnes.get(i).objectifAteint()) {
                    if(rayonActive){
                        listPersonnes.get(i).setObjectifRayon(salle);
                        listPersonnes.get(i).setDxDyNormalise(listPersonnes.get(i).getObjectifRayon());
                    }else{
                        listPersonnes.get(i).setObjectif(salle);
                        listPersonnes.get(i).setDxDyNormalise(listPersonnes.get(i).getObjectif());
                    }
                } else {    
                    if(collisionActive) {
                        bloque = false;
                        y = 0;
                        while ((!bloque) && y < listPersonnes.size()) {
                            bloque = estBloque(listPersonnes.get(i), listPersonnes.get(y));
                            y++;
                        }
                        //si n'est pas bloqué alors, avance normalement
                        if (!bloque) {
                            listPersonnes.get(i).avancer();
                            cSalle.deplacerPersonne(listPersonnes.get(i));
                        } else{
                            listPersonnes.get(i).setDecalage(listPersonnes.get(i).getCoordCourant());
                        }
                    }
                    else { // Sans collisions
                        if(rayonActive)
                            listPersonnes.get(i).avancerRayon();
                        else
                            listPersonnes.get(i).avancer();
                        cSalle.deplacerPersonne(listPersonnes.get(i));
                    }
                }
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
        //graphe.afficherDiagonalesObstacle();
        graphe.creerTousLesPlusCourtsChemins();
        cSalle.afficherGraphe(graphe.afficher());
    }


    public void play(Boolean collisionActive, boolean rayonActive){
        if(loop != null && loop.getStatus() == Status.PAUSED){
            loop.play();
        } else if(loop == null || loop.getStatus() == Status.STOPPED){
            demarrer(collisionActive, rayonActive);
        }
    }


    public Point findPointSortiePlusProcheDirect(Point A) {
        double distance;

        double distanceCourte = 1000000;
        Point plusProche = null;
        Point courant;

        if (!listSorties.isEmpty()) {
            for (Sortie sortie : listSorties) {
                courant = sortie.findPointSortieDirect(this, A, 5); // Mettre le rayon de personne
                if (courant != null) {
                    distance = MathsCalcule.distance(A, courant);

                    if (distance < distanceCourte) {
                        distanceCourte = distance;
                        plusProche = new Point(courant);    // A check
                    }
                }
            }
        }
        return plusProche;
    }

    public boolean intersecObstacle(Point coordA,Point coordB) {
        boolean b = false;
        for(Obstacle obstacle: listObstacles) {
            if (MathsCalcule.coordSegments(coordA, coordB, obstacle).isEmpty()) {
                b=false;
            }  else return true;
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

    public boolean estBloque(Personne p, Personne compare){
        if (!p.equals(compare) && MathsCalcule.distance(p.getProchainMouvement(), compare.getCoordCourant()) <= p.getRayon()*2){
            return true;
        } else {
            return false;
        }
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

