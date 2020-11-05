package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ControllerSortie extends Parent {

    private Sortie sortie;

    public ControllerSortie (Sortie s) {
        sortie = s;
        double epaisseurGraphique = 15;
        int mur = sortie.getMur();


        Rectangle sortieGraphique = new Rectangle();

        // Placement du rectangle par rapport au ControllerSalle
        if (mur == 1) {
            sortieGraphique.setX(sortie.getPoint1().getX());
            sortieGraphique.setY(- epaisseurGraphique);
        }
        if (mur == 2 || mur == 3) {
            sortieGraphique.setX(sortie.getPoint1().getX());
            sortieGraphique.setY(sortie.getPoint1().getY());
        }
        else if (mur == 4) {
            sortieGraphique.setX(- epaisseurGraphique);
            sortieGraphique.setY(sortie.getPoint1().getY());
        }
        else
            System.out.println("ControllerSortie, constructeur, erreur1. ");


        // Forme du rectangle suivant la sortie sur la quelle il est.
        if(mur==1 || mur==3){
            sortieGraphique.setWidth(sortie.getLongueur());
            sortieGraphique.setHeight(epaisseurGraphique);
        }
        else if(mur==2 || mur==4){
            sortieGraphique.setHeight(sortie.getLongueur());
            sortieGraphique.setWidth(epaisseurGraphique);
        }
        else {
            System.out.println("ControllerSortie, constructeur, erreur2. ");
            return;
        }

        sortieGraphique.setFill(Color.NAVY);
        getChildren().add(sortieGraphique);
    }
}
