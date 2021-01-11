package sample.controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import sample.Obstacle;
import sample.ObstaclePolygone;
import sample.Personne;
import sample.Point;

import java.util.List;

public class ControllerObstaclePolygone extends ControllerObstacle {
    private Obstacle obstacle;

    public ControllerObstaclePolygone(ObstaclePolygone obstaclePolygone) {
        obstacle = obstaclePolygone;

        Polygon polygoneGraphique = new Polygon();

        for (Point point : obstaclePolygone.getListePointsGraphiques()) {
            polygoneGraphique.getPoints().addAll(point.getX(), point.getY());
        }

        //Random ran = new Random();
        //polygoneGraphique.setFill(Color.rgb(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)));
        polygoneGraphique.setFill(Color.DARKCYAN);
        this.getChildren().add(polygoneGraphique);
    }

    public ControllerObstaclePolygone (List<Point> listeSommets) {
        Polygon polygoneGraphique = new Polygon();

        for (Point point : listeSommets)
            polygoneGraphique.getPoints().addAll(point.getX(), point.getY());

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
