package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class ObstacleRectangle extends Obstacle {

    private double x;
    private double y;
    private double largeur;
    private double hauteur;
    private List<Point> listcoins;

    public ObstacleRectangle(double xx, double yy, double larg, double haut) {
        x = xx;
        y = yy;
        largeur = larg;
        hauteur = haut;
        listcoins= new ArrayList<>();
        listcoins.add(new Point(x,y));
        listcoins.add(new Point((x+largeur),y));
        listcoins.add(new Point((x+largeur),(y+hauteur)));
        listcoins.add(new Point(x,(y+hauteur)));
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

    public List<Point> getCoins() {
        return listcoins;
    }

}
