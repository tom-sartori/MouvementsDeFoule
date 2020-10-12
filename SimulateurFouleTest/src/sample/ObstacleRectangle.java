package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class ObstacleRectangle extends Obstacle {

    private double x;
    private double y;
    private double largeur;
    private double hauteur;

    public ObstacleRectangle(double x, double y, double largeur, double hauteur) {
        this.x = x;
        this.y = y;
        this.largeur = largeur;
        this.hauteur = hauteur;

        Rectangle obstacle = new Rectangle(x, y, largeur, hauteur);
        obstacle.setFill(Color.DARKCYAN);
        getChildren().add(obstacle);
    }
}
