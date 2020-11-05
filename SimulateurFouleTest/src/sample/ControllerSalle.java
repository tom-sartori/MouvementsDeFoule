package sample;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.List;
import java.util.Random;

public class ControllerSalle extends Parent{
    private Salle salle;


    public ControllerSalle (Salle s) {
        this.salle = s;

        Rectangle salleGraphique = new Rectangle(salle.getLargeur(), salle.getHauteur());
        salleGraphique.setFill(Color.LIGHTCYAN);
        this.getChildren().add(salle);


        // Event utilisé pour ajouter des Personne en cliquant.
        salle.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!salle.isRunning()){
                    createPersonne(event.getX(),event.getY());
                }
            }
        });

        getChildren().add(salleGraphique);
    }

    public ControllerSalle(double width, double height){
        salle = new Salle(width,height);

/*
        for (int i = 0; i < 10000; i++) {
            Random ran = new Random();
            double x = ran.nextInt(970 - 25 + 1) + 20;
            double y = ran.nextInt(540 - 25 + 1) + 20;
            System.out.println(x + " " + y);
            salle.addPersonne(new Personne(x,y));
        }
        //salle.addPersonne(new Personne(200, 300));
*/

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
    public void afficherControllerObstacle(ControllerObstacleRectangle controllerObstacleRectangle) {
        getChildren().add(controllerObstacleRectangle);
    }

    public void afficherSortie(ControllerSortie controllerSortie) {
        getChildren().add(controllerSortie);
    }

    public Salle getSalle(){
        return salle;
    }

}
