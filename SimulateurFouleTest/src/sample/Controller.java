package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public class Controller extends Parent{
    private ControllerSalle cs;
    private ControllerPanel cp;

    public Controller(double width, double height){
        cs = new ControllerSalle(width, height-40);
        cp = new ControllerPanel();
        cp.setTranslateY(height-40);
        cp.minWidth(width);
        
        Salle salle = cs.getSalle();

        cp.getPlayButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.demarrerV2();
                if(salle.isRunning()){
                    cp.setStatusLabel(true);
                }
            }
        });

        cp.getPauseButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.pause();
                cp.setStatusLabel(false);
            }
        });

        cp.getClearButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.removeAllPersonne();
                cp.setStatusLabel(false);
            }
        });

        this.getChildren().addAll(cs, cp);
    }
    
}
