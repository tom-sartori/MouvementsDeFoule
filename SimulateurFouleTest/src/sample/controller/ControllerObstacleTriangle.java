package sample.controller;


import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import sample.ObstacleTriangle;

public class ControllerObstacleTriangle extends ControllerObstacle {

    public ControllerObstacleTriangle(ObstacleTriangle obstacleTriangle) {
        Polygon triangleGraphique = new Polygon();
        triangleGraphique.getPoints().addAll(
                obstacleTriangle.getListePoints().get(0).getX(), obstacleTriangle.getListePoints().get(0).getY(),
                obstacleTriangle.getListePoints().get(1).getX(), obstacleTriangle.getListePoints().get(1).getY(),
                obstacleTriangle.getListePoints().get(2).getX(), obstacleTriangle.getListePoints().get(2).getY());

        triangleGraphique.setFill(Color.DARKCYAN);
        getChildren().add(triangleGraphique);

    }
}
