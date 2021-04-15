package controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import physique.Obstacle;
import physique.ObstacleTriangle;

public class ControllerObstacleTriangle extends ControllerObstacle {
    private Obstacle obstacle;
    
    public ControllerObstacleTriangle(ObstacleTriangle obstacleTriangle) {
        obstacle = obstacleTriangle;
        
        Polygon triangleGraphique = new Polygon();
        triangleGraphique.getPoints().addAll(
                obstacleTriangle.getListePointsPhysique().get(0).getX(), obstacleTriangle.getListePointsPhysique().get(0).getY(),
                obstacleTriangle.getListePointsPhysique().get(1).getX(), obstacleTriangle.getListePointsPhysique().get(1).getY(),
                obstacleTriangle.getListePointsPhysique().get(2).getX(), obstacleTriangle.getListePointsPhysique().get(2).getY());

        triangleGraphique.setFill(Color.DARKCYAN);
        getChildren().add(triangleGraphique);

    }

    @Override
    public Obstacle getObstacle(){
        return obstacle;
    }
}
