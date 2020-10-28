package sample;

import java.util.ArrayList;
import java.util.List;

public class ObstacleRectangle extends Obstacle {

    private Point point1;
    private Point point2;
    private Point point3;
    private Point point4;
    private double largeur;
    private double hauteur;
    private List<Point> listCoins;
    private List<Point> listDiagonales;

    public ObstacleRectangle(double x, double y, double larg, double haut) {
        largeur = larg;
        hauteur = haut;

        point1 = new Point(x, y);
        point2 = new Point(x + largeur, y);
        point3 = new Point(x + largeur, y + hauteur);
        point4 = new Point(x, y + hauteur);

        listCoins= new ArrayList<>();
        listCoins.add(point1);  //addAll similaire ?
        listCoins.add(point2);
        listCoins.add(point3);
        listCoins.add(point4);

        listDiagonales= new ArrayList<>();
        listDiagonales.add(point1);
        listDiagonales.add(point3);
        listDiagonales.add(point2);
        listDiagonales.add(point4);
    }


    public double getLargeur() {
        return largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public List<Point> getCoins() {
        return listCoins;
    }

    public List<Point> getDiagonales() {
        return listDiagonales;
    }
}