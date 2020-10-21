package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ObstacleRectangle extends Obstacle {

    private double x;
    private double y;
    private double largeur;
    private double hauteur;
    private double[][] coins;

    public ObstacleRectangle(double xx, double yy, double larg, double haut) {
        x = xx;
        y = yy;
        largeur = larg;
        hauteur = haut;

        coins = new double[][]{{x, y},{x + largeur, y},{x + largeur, y + hauteur},{x, y + hauteur}};
        Rectangle obstacle = new Rectangle(x, y, largeur, hauteur);
        obstacle.setFill(Color.DARKCYAN);
        getChildren().add(obstacle);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getLargeur() {
        return largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public double[][] getCoins() {
        return coins;
    }

}
