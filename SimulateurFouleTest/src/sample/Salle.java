package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class Salle extends Parent {
    private double largeur;
    private double hauteur;
    private double marge = 20;
    private List<Sortie> listSorties;
    private List<Obstacle> listObstacles;

    public Salle(double lar, double hau) {
        this.largeur = lar - (2 * marge);
        this.hauteur = hau - (2 * marge);
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

    public void addObstacle (Obstacle obstacle) {
        listObstacles.add(obstacle);
        this.getChildren().add(obstacle);
    }
}