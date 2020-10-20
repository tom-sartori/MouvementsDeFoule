package sample;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

public class ControllerSalle extends Parent{
    private Salle salle;

    public Salle getSalle(){
        return salle;
    }

    public ControllerSalle(double width, double height){
        salle = new Salle(width,height);
        Sortie sortie = new Sortie(2,60,50);
        Sortie sortie1 = new Sortie(3, 60, 80);
        salle.addSortie(sortie);
        salle.addSortie(sortie1);
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
