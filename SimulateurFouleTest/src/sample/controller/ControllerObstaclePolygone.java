package sample.controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import sample.ObstaclePolygone;
import sample.ObstacleRectangle;
import sample.Personne;
import sample.Point;

public class ControllerObstaclePolygone extends ControllerObstacle {

    public ControllerObstaclePolygone(ObstaclePolygone obstaclePolygone) {
        Polygon polygoneGraphique = new Polygon();
        for (Point point : obstaclePolygone.getListePoints()) {
            polygoneGraphique.getPoints().addAll(point.getX(), point.getY());
        }

        //Random ran = new Random();
        //polygoneGraphique.setFill(Color.rgb(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)));
        polygoneGraphique.setFill(Color.DARKCYAN);
        this.getChildren().add(polygoneGraphique);
    }
}
