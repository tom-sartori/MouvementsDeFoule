package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

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

    public ObstacleRectangle(double x, double y, double larg, double haut) {
        largeur = larg;
        hauteur = haut;

        point1 = new Point(x, y);   // haut gauche
        point2 = new Point(x + largeur, y);     // haut droit
        point3 = new Point(x + largeur, y + hauteur);   // bas droit
        point4 = new Point(x, y + hauteur);     // bas gauche

        listcoins= new ArrayList<>();
        listcoins.add(point1);  //addAll similaire ?
        listcoins.add(point2);
        listcoins.add(point3);
        listcoins.add(point4);

        Rectangle obstacle = new Rectangle(point1.getX(), point1.getY(), largeur, hauteur);
        obstacle.setFill(Color.DARKCYAN);
        getChildren().add(obstacle);
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

}
