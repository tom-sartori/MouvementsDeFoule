package sample;

import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class ControllerObstacleRectangle extends Parent{
    private ObstacleRectangle obstacleRectangle;


    public ControllerObstacleRectangle(ObstacleRectangle obstacleRect) {
        obstacleRectangle = obstacleRect;

        Rectangle obstacleGraphique = new Rectangle();
        obstacleGraphique.setX(obstacleRectangle.getListcoins().get(0).getX());
        obstacleGraphique.setY(obstacleRectangle.getListcoins().get(0).getY());
        obstacleGraphique.setWidth(obstacleRectangle.getLargeur());
        obstacleGraphique.setHeight(obstacleRectangle.getHauteur());

        obstacleGraphique.setFill(Color.DARKCYAN);
        this.getChildren().add(obstacleGraphique);
    }
}