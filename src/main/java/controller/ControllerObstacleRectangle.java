package controller;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import physique.Obstacle;
import physique.ObstacleRectangle;
import physique.Personne;

public class ControllerObstacleRectangle extends ControllerObstacle{
    private Obstacle obstacle;
    
    public ControllerObstacleRectangle(ObstacleRectangle obstacleRectangle) {
        obstacle = obstacleRectangle;

        double rayon = new Personne(0,0).getRayon();
        Rectangle obstacleGraphique = new Rectangle();
        obstacleGraphique.setX(obstacleRectangle.getListePointsPhysique().get(0).getX()+rayon);
        obstacleGraphique.setY(obstacleRectangle.getListePointsPhysique().get(0).getY()+rayon);
        obstacleGraphique.setWidth(obstacleRectangle.getListePointsPhysique().get(1).getX() - obstacleRectangle.getListePointsPhysique().get(0).getX() - 2 * rayon);
        obstacleGraphique.setHeight(obstacleRectangle.getListePointsPhysique().get(2).getY() - obstacleRectangle.getListePointsPhysique().get(0).getY() - 2 * rayon);

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
