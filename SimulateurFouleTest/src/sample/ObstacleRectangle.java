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
    private List<Point> listcoins;
    private List<Point> listDiagonales;

    public ObstacleRectangle(double x, double y, double larg, double haut) {
        largeur = larg;
        hauteur = haut;

        point1 = new Point(x, y);   // haut gauche
        point2 = new Point(x + largeur, y);     // haut droit
        point3 = new Point(x + largeur, y + hauteur);   // bas droit
        point4 = new Point(x, y + hauteur);     // bas gauche

        listcoins= new ArrayList<>();
        listcoins.add(point1);
        listcoins.add(point2);
        listcoins.add(point3);
        listcoins.add(point4);

        listDiagonales= new ArrayList<>();
        listDiagonales.add(point1);
        listDiagonales.add(point3);
        listDiagonales.add(point2);
        listDiagonales.add(point4);

        System.out.println("point 1" + point1);
        System.out.println("point 2" + point2);
        System.out.println("point 3" + point3);
        System.out.println("point 4" + point4);

    }

    public ControllerObstacleRectangle afficher () {
        return new ControllerObstacleRectangle(this);
    }

    public double getLargeur() {
        return largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public List<Point> getCoins() {
        return listcoins;
    }

    public List<Point> getDiagonales() {
        return listDiagonales;
    }

    public Point getPoint1() {
        return point1;
    }
}
