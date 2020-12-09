package sample.controller;

import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;
import sample.Obstacle;
import sample.ObstacleRectangle;
import sample.Personne;

import java.util.Random;

public class ControllerObstacleRectangle extends ControllerObstacle{
    private Obstacle obstacle;
    
    public ControllerObstacleRectangle(ObstacleRectangle obstacleRectangle) {
        obstacle = obstacleRectangle;

        double rayon = new Personne(0,0).getRayon();
        Rectangle obstacleGraphique = new Rectangle();
        obstacleGraphique.setX(obstacleRectangle.getListePoints().get(0).getX()+rayon);
        obstacleGraphique.setY(obstacleRectangle.getListePoints().get(0).getY()+rayon);
        obstacleGraphique.setWidth(obstacleRectangle.getListePoints().get(1).getX() - obstacleRectangle.getListePoints().get(0).getX() - 2 * rayon);
        obstacleGraphique.setHeight(obstacleRectangle.getListePoints().get(2).getY() - obstacleRectangle.getListePoints().get(0).getY() - 2 * rayon);

        //Random ran = new Random();
        //obstacleGraphique.setFill(Color.rgb(ran.nextInt(255), ran.nextInt(255), ran.nextInt(255)));
        obstacleGraphique.setFill(Color.DARKCYAN);
        this.getChildren().add(obstacleGraphique);
    }

    @Override
    public Obstacle getObstacle(){
        return obstacle;
    }
}