package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ObstacleRectangle extends Obstacle {

    private double x;
    private double y;
    private double largeur;
    private double hauteur;
    private double[] coin1;
    private double[] coin2;
    private double[] coin3;
    private double[] coin4;

    public ObstacleRectangle(double xx, double yy, double larg, double haut) {
        x = xx;
        y = yy;
        largeur = larg;
        hauteur = haut;

        coin1 = new double[]{x, y};
        coin2 = new double[]{x + largeur, y};
        coin3 = new double[]{x + largeur, y + hauteur};
        coin4 = new double[]{x, y + hauteur};

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

    public double[] getCoin1() {
        return coin1;
    }

    public double[] getCoin2() {
        return coin2;
    }

    public double[] getCoin3() {
        return coin3;
    }

    public double[] getCoin4() {
        return coin4;
    }
}
