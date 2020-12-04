package sample.controller;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Point;
import sample.Sortie;

public class ControllerSortie extends Parent {


    public ControllerSortie (Sortie sortie) {
        double epaisseurGraphique = 15;

        Point point1Sortie = sortie.getPoint1();
        Rectangle sortieGraphique = new Rectangle();

        // Placement du rectangle par rapport au ControllerSalle
        if (point1Sortie.getY() == 0) {     // mur == 1
            sortieGraphique.setX(point1Sortie.getX());
            sortieGraphique.setY(- epaisseurGraphique);
        }
        else if (point1Sortie.getX() == 0) {     // mur = 4
            sortieGraphique.setX(- epaisseurGraphique);
            sortieGraphique.setY(point1Sortie.getY());
        }
        else  { // mur 2 3
            sortieGraphique.setX(point1Sortie.getX());
            sortieGraphique.setY(point1Sortie.getY());
        }

        // Forme du rectangle suivant la sortie sur la quelle il est.
        Point point2Sortie = sortie.getPoint2();
        if(point1Sortie.getY() == point2Sortie.getY()){   // mur==1 || mur==3
            sortieGraphique.setWidth(sortie.getLargeurPorte());
            sortieGraphique.setHeight(epaisseurGraphique);
        }
        else if(point1Sortie.getX() == point2Sortie.getX()){  //mur==2 || mur==4
            sortieGraphique.setHeight(sortie.getLargeurPorte());
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
