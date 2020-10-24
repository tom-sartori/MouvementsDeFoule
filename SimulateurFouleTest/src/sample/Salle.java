package sample;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.List;

public class Salle extends Parent {
    private double largeur;
    private double hauteur;
    private double marge = 20;
    private List<Personne> listPersonnes;
    private List<Sortie> listSorties;
    private List<Obstacle> listObstacles;
    private Timeline loop;

    public Salle(double lar, double hau) {  // Créée une salle rectangle avec une marge
        this.largeur = lar - (2 * marge);
        this.hauteur = hau - (2 * marge);
        this.listPersonnes = new ArrayList<>(); // HashList surement apres
        this.listSorties = new ArrayList<>();
        this.listObstacles = new ArrayList<>();

        Rectangle salle = new Rectangle(largeur, hauteur);
        salle.setTranslateX(marge);
        salle.setTranslateY(marge);
        salle.setFill(Color.LIGHTCYAN);
        this.getChildren().add(salle);
    }

    public List<Sortie> getListSorties() {
        return listSorties;
    }



    // Permet d'ajouter une sortie à la salle et la place correctement
    // Modifie x1 y1 x2 y2 de la sortie correspondante pour lui donner uniquement les coordonnées utiles
    // (donc pas les coords exterrieurs à la salle)
    public void addSortie (Sortie sortie) {
        listSorties.add(sortie);

        if (sortie.getMur() == 1) {
            sortie.setTranslateX(marge + sortie.getDistance());
            sortie.setTranslateY(marge - sortie.getEpaisseur());

            sortie.setX1(marge + sortie.getDistance());
            sortie.setY1(marge);
            sortie.setX2(sortie.getX1() + sortie.getLongueur());
            sortie.setY2(marge);
        }
        if (sortie.getMur() == 2) {
            sortie.setTranslateX(marge + largeur);
            sortie.setTranslateY(marge + sortie.getDistance());

            sortie.setX1(marge + largeur);
            sortie.setY1(marge + sortie.getDistance());
            sortie.setX2(marge + largeur);
            sortie.setY2(sortie.getY1() + sortie.getLongueur());
        }
        if (sortie.getMur() == 3) {
            sortie.setTranslateX(marge + sortie.getDistance());
            sortie.setTranslateY(marge + hauteur);

            sortie.setX1(marge + sortie.getDistance());
            sortie.setY1(marge + hauteur);
            sortie.setX2(sortie.getX1() + sortie.getLongueur());
            sortie.setY2(marge + hauteur);
        }
        if (sortie.getMur() == 4) {
            sortie.setTranslateX(marge - sortie.getEpaisseur());
            sortie.setTranslateY(marge + sortie.getDistance());

            sortie.setX1(marge);
            sortie.setY1(marge + sortie.getDistance());
            sortie.setX2(marge);
            sortie.setY2(sortie.getY1() + sortie.getLongueur());
        }

        this.getChildren().add(sortie);
    }

    public void addPersonne (Personne personne) {
        listPersonnes.add(personne);
        getChildren().add(personne);
    }

    public void addObstacle (Obstacle obstacle){
        listObstacles.add(obstacle);
        this.getChildren().add(obstacle);
    }

    public List<Obstacle> getListObstacles(){
        return listObstacles;
    }

    public void demarrer () {
        if (!listPersonnes.isEmpty()) {
            for (Personne personne : listPersonnes) {   // Pour chaque personne de la salle
                personne.setDxDyNormalise(this);         // Initialise dx et dy
            }

            Salle salle = this; // Pas sur de la propreté de cette ligne mais ne fonctionnait pas dans la timeline sans

            if (loop == null) {

                loop = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent arg) {

                        for (int i = 0; i < listPersonnes.size(); i++) {
                            if (listPersonnes.get(i).estSorti(salle))
                                removePersonne(listPersonnes.get(i));
                            else
                                listPersonnes.get(i).avancer();
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


    public double getLargeur() {
        return largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public double getMarge() {
        return marge;
    }

    public void removePersonne (Personne personne) {
        listPersonnes.remove(personne);
        getChildren().remove(personne);
        if (listPersonnes.isEmpty())
            loop.pause();

    }

    public void removeAllPersonne(){
        pause();
        while(!listPersonnes.isEmpty())
            removePersonne(listPersonnes.get(0));
    }

    public boolean isRunning(){
        if(loop!=null && loop.getStatus()== Animation.Status.RUNNING) return true;
        else return false;
    }

    public void addGraphe(ControllerGraphe controllerGraphe) {
        getChildren().add(controllerGraphe);
    }

    public Point findSortiePlusProche(Point A) {
        double distance1 = -1;
        double distance2 = -1;

        double distanceCourte = 1000000;
        Point plusProche = new Point();

        if (!listSorties.isEmpty()) {

            for (Sortie sortie : listSorties) {
                sortie.setPoint1(new Point(sortie.getX1(), sortie.getY1()));    //temporaire pour tester
                sortie.setPoint2(new Point(sortie.getX2(), sortie.getY2()));

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

    public boolean intersecObstacle(Point coordA,Point coordB) {
        boolean b = false;
        for(Obstacle obstacle: listObstacles){
            if(MathsCalcule.coordSegments(coordA,coordB,obstacle).isEmpty()){
                b= false;
            }
            else
                return true;
        }
        return b;
    }
}
