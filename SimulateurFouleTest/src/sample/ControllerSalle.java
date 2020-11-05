package sample;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import java.util.List;
import java.util.Random;

public class ControllerSalle extends Parent{
    private Salle salle;

    public Salle getSalle(){
        return salle;
    }

    public ControllerSalle(double width, double height){
        salle = new Salle(width,height);

        salle.addSortie(new Sortie(1,60,190));


        salle.addObstacle(new ObstacleRectangle(150, 200, 120, 70));
        salle.addObstacle(new ObstacleRectangle(300, 400, 50, 50));
        salle.addObstacle(new ObstacleRectangle(600, 100, 100, 400));
        salle.addObstacle(new ObstacleRectangle(450, 80, 30, 300));
        salle.addObstacle(new ObstacleRectangle(60, 400, 70, 100));
        salle.addObstacle(new ObstacleRectangle(30, 40, 70, 100));


        /*
        for (int i = 0; i < 500; i++) {
            Random ran = new Random();
            double x = ran.nextInt(970 - 25 + 1) + 20;
            double y = ran.nextInt(540 - 25 + 1) + 20;
            System.out.println(x + " " + y);
            salle.addPersonne(new Personne(x,y));
        }*/
        //salle.addPersonne(new Personne(200, 300));


        salle.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(!salle.isRunning()){
                    createPersonne(event.getX(),event.getY());
                }
            }
        });
        
        this.getChildren().add(salle);
    }   

    public void createPersonne(double x, double y){
        Personne personne = new Personne(x, y);
        salle.addPersonne(personne);
    }
}
