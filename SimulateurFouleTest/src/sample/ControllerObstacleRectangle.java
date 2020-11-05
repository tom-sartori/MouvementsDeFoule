package sample;

import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class ControllerObstacleRectangle extends Parent{
    private ObstacleRectangle obstacleRectangle;


    public ControllerObstacleRectangle(ObstacleRectangle obstacleRect) {
        obstacleRectangle = obstacleRect;

        Rectangle obstacleGraphique = new Rectangle(obstacleRectangle.getPoint1().getX(), obstacleRectangle.getPoint1().getY(), obstacleRectangle.getLargeur(), obstacleRectangle.getLargeur());
        obstacleGraphique.setFill(Color.DARKCYAN);
        this.getChildren().add(obstacleGraphique);
    }
}