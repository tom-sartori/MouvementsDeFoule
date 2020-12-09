package sample.controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import sample.Obstacle;
import sample.ObstaclePolygone;
import sample.ObstacleRectangle;
import sample.Personne;
import sample.Point;

public class ControllerObstaclePolygone extends ControllerObstacle {
    private Obstacle obstacle;

    public ControllerObstaclePolygone(ObstaclePolygone obstaclePolygone) {
        obstacle = obstaclePolygone;

        Polygon polygoneGraphique = new Polygon();
        double rayon = new Personne(-1, -1).getRayon();

        for (Point point : obstaclePolygone.getListePoints()) {
            polygoneGraphique.getPoints().addAll(point.getX(), point.getY());
        }

        //Random ran = new Random();
        //polygoneGraphique.setFill(Color.rgb(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)));
        polygoneGraphique.setFill(Color.DARKCYAN);
        this.getChildren().add(polygoneGraphique);
    }

    @Override
    public Obstacle getObstacle(){
        return obstacle;
    }
}
