package sample.controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import sample.Obstacle;
import sample.ObstacleTriangle;

public class ControllerObstacleTriangle extends ControllerObstacle {
    private Obstacle obstacle;
    
    public ControllerObstacleTriangle(ObstacleTriangle obstacleTriangle) {
        obstacle = obstacleTriangle;
        
        Polygon triangleGraphique = new Polygon();
        triangleGraphique.getPoints().addAll(
                obstacleTriangle.getListePoints().get(0).getX(), obstacleTriangle.getListePoints().get(0).getY(),
                obstacleTriangle.getListePoints().get(1).getX(), obstacleTriangle.getListePoints().get(1).getY(),
                obstacleTriangle.getListePoints().get(2).getX(), obstacleTriangle.getListePoints().get(2).getY());

        triangleGraphique.setFill(Color.DARKCYAN);
        getChildren().add(triangleGraphique);

    }

    @Override
    public Obstacle getObstacle(){
        return obstacle;
    }
}
