package sample;

import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class ControllerObstacleRectangle extends Parent{
    private ObstacleRectangle obstacle;

    public ObstacleRectangle getObstacle(){
        return obstacle;
    }
    
    public ControllerObstacleRectangle(double x, double y, double larg, double haut){
        obstacle = new ObstacleRectangle(x, y, larg, haut);
        
        Rectangle obstacle = new Rectangle(x, y, larg, haut);
        obstacle.setFill(Color.DARKCYAN);
        this.getChildren().add(obstacle);
    }

}
