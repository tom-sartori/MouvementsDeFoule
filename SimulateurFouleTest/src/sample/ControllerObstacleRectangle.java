package sample;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.Random;

public class ControllerObstacleRectangle extends ControllerObstacle{

    public ControllerObstacleRectangle(ObstacleRectangle obstacleRectangle) {
        Rectangle obstacleGraphique = new Rectangle();
        obstacleGraphique.setX(obstacleRectangle.getListePoints().get(0).getX());
        obstacleGraphique.setY(obstacleRectangle.getListePoints().get(0).getY());
        obstacleGraphique.setWidth(obstacleRectangle.getListePoints().get(1).getX() - obstacleRectangle.getListePoints().get(0).getX());
        obstacleGraphique.setHeight(obstacleRectangle.getListePoints().get(2).getY() - obstacleRectangle.getListePoints().get(0).getY());

        //Random ran = new Random();
        //obstacleGraphique.setFill(Color.rgb(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)));
        obstacleGraphique.setFill(Color.DARKCYAN);
        this.getChildren().add(obstacleGraphique);
    }
}