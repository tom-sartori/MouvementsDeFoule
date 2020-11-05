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

public class Salle {
    private double largeur;
    private double hauteur;
    private List<Personne> listPersonnes;
    private List<Sortie> listSorties;
    private List<ObstacleRectangle> listObstacles;
    private Timeline loop;
    private Graphe graphe;

    private ControllerSalle cSalle;


    public Salle(double lar, double hau) {  // Créée une salle rectangle avec une marge
        this.largeur = lar;
        this.hauteur = hau;
        this.listPersonnes = new ArrayList<>(); // HashList surement apres
        this.listSorties = new ArrayList<>();
        this.listObstacles = new ArrayList<ObstacleRectangle>();

        graphe = new Graphe(this);
    }

    // Permet d'initialiser le controller de la salle
    // Créée les controllers des obstacles et sprties, puis les ajoutent au controller de la salle
    public ControllerSalle afficher() {
        cSalle = new ControllerSalle(this);

        for (ObstacleRectangle obstacleRectangle : listObstacles)
            cSalle.afficherControllerObstacle(obstacleRectangle.afficher());

        for (Sortie sortie : listSorties)
            cSalle.afficherSortie(sortie.afficher());

        for (Personne personne : listPersonnes)
            cSalle.afficherPersonne(personne.afficher());

        return cSalle;
    }

    // Permet d'ajouter au controller de la salle, les controllers de tous les obstacles et sorties.
    // Si afficher() a déjà été appelé, ceci superpose les controllers sur ceux deja existants.
    public void refreshAffichage() {
        for (ObstacleRectangle obstacleRectangle : listObstacles)
            cSalle.afficherControllerObstacle(obstacleRectangle.afficher());

        for (Sortie sortie : listSorties)
            cSalle.afficherSortie(sortie.afficher());
    }


    public void addObstacle (ObstacleRectangle obstacle){
        listObstacles.add(obstacle);
    }

    // Permet d'ajouter une sortie à la salle et la place correctement
    // Modifie x1 y1 x2 y2 de la sortie correspondante pour lui donner uniquement les coordonnées utiles
    // (donc pas les coords exterrieurs à la salle)
    public void addSortie (Sortie sortie) {
        listSorties.add(sortie);

        if (sortie.getMur() == 1) {     // Mur haut
            sortie.getPoint1().setPoint(sortie.getDistance(), 0);
            sortie.getPoint2().setPoint( sortie.getDistance() + sortie.getLongueur(), 0);
        }
        else if (sortie.getMur() == 2) {     // Mur droit
            sortie.getPoint1().setPoint(largeur, sortie.getDistance());
            sortie.getPoint2().setPoint(largeur,sortie.getDistance() + sortie.getLongueur());
        }
        else if (sortie.getMur() == 3) {     // Mur bas
            sortie.getPoint1().setPoint(sortie.getDistance(), hauteur);
            sortie.getPoint2().setPoint(sortie.getDistance() + sortie.getLongueur(), hauteur);
        }
        else if (sortie.getMur() == 4) {     // Mur gauche
            sortie.getPoint1().setPoint(0, sortie.getDistance());
            sortie.getPoint2().setPoint(0,sortie.getDistance() + sortie.getLongueur());
        }
        else
            System.out.println("Salle, addSortie, problème de mur. ");
    }

    public void addPersonne (Personne personne) {
        listPersonnes.add(personne);
    }

    public void removePersonne (Personne personne) {
        listPersonnes.remove(personne);
        if (listPersonnes.isEmpty())
            loop.pause();
        cSalle.retirerPersonne(personne);
    }

    public void removeAllPersonne(){
        pause();
        while(!listPersonnes.isEmpty())
            removePersonne(listPersonnes.get(0));
    }


    public void demarrerV2 () {
        initialisationGrapheSansAffichage();

        if (!listPersonnes.isEmpty()) {
            for (Personne personne : listPersonnes) {   // Pour chaque personne de la salle
                personne.setObjectif(this);
                personne.setDxDyNormalise(personne.getObjectif());
            }

            Salle salle = this; // Pas sur de la propreté de cette ligne mais ne fonctionnait pas dans la timeline sans

            if (loop == null || loop.getStatus()==Status.STOPPED) {

                loop = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent arg) {

                        for (int i = 0; i < listPersonnes.size(); i++) {
                            if (listPersonnes.get(i).estSorti(salle))
                                removePersonne(listPersonnes.get(i));
                            else {
                                if (listPersonnes.get(i).objectifAteint()) {
                                    listPersonnes.get(i).setObjectif(salle);
                                    listPersonnes.get(i).setDxDyNormalise(listPersonnes.get(i).getObjectif());
                                }
                                else {
                                    listPersonnes.get(i).avancer();
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

    public void pause(){
        if(loop != null && loop.getStatus() == Status.RUNNING){
            loop.pause();
        }
    }

    public boolean isRunning(){
        if(loop!=null && loop.getStatus()== Animation.Status.RUNNING) return true;
        else return false;
    }


    // Utilisé à chaque fois qu'on appuie sur "play", donc qu'on lance démarer.
    // Pas possible d'initialiser avant car les obstacles, sorties et persos ne sont pas encore ajoutés au graphe
    public void initialisationGrapheSansAffichage () {
        graphe = new Graphe(this);
        graphe.creerCheminPlusCourtAvecSortie(this);
    }


    // Utilisé à chaque fois qu'on appuie sur "play", donc qu'on lance démarer.
    // Pas possible d'initialiser avant car les obstacles, sorties et persos ne sont pas encore ajoutés au graphe
    public void initialisationGrapheAvecAffichage () {
        graphe = new Graphe(this);
        graphe.creerCheminPlusCourtAvecSortie(this);
        cSalle.afficherGraphe(graphe.afficher());
    }

    public void play(){
        if(loop != null && loop.getStatus() == Status.PAUSED){
            loop.play();
        } else if(loop == null || loop.getStatus() == Status.STOPPED){
            demarrerV2();
        }
    }

  
    // Ne prend pas en compte les obstacles
    public Point findSortiePlusProcheIndirecte(Point A) {
        double distance1 = -1;
        double distance2 = -1;

        double distanceCourte = 1000000;
        Point plusProche = new Point();

        if (!listSorties.isEmpty()) {

            for (Sortie sortie : listSorties) {

                distance1 = MathsCalcule.distance(A, sortie.getPoint1());
                distance2 = MathsCalcule.distance(A, sortie.getPoint2());

                if (Math.min(distance1, distance2) < distanceCourte) {
                    if (distance1 < distance2) {
                        distanceCourte = distance1;
                        plusProche = sortie.getPoint1();
                    } else {
                        distanceCourte = distance2;
                        plusProche = sortie.getPoint2();
                    }
                }
            }
        }
        else
            System.out.println("Pas de sorties dans la salle");
        return plusProche;
    }

    public Point findSortiePlusProcheDirecte(Point A) {
        double distance;

        double distanceCourte = 1000000;
        Point plusProche = null;

        if (!listSorties.isEmpty()) {

            for (Sortie sortie : listSorties) {
                for (Point pointSortie : sortie.getListePointsSortie()) {
                    if (!intersecObstacle(A, pointSortie)) {
                        distance = MathsCalcule.distance(A, pointSortie);
                        if (distance < distanceCourte) {
                            distanceCourte = distance;
                            plusProche = pointSortie;
                        }
                    }
                }
            }
            return plusProche;
        }
        else {
            System.out.println("Pas de sorties dans la salle");
            return null;
        }
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


    public List<Sortie> getListSorties() {
        return listSorties;
    }

    public Graphe getGraphe() {
        return graphe;
    }

    public List<ObstacleRectangle> getListObstacles(){
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
