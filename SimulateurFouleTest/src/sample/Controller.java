package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;

public class Controller extends Parent{
    private ControllerSalle cSalle;
    private ControllerPanel cPanel;


    public Controller (Salle salle) {
        double marge = 30;

        cSalle = salle.afficher();
        cSalle.setTranslateX(marge);
        cSalle.setTranslateY(marge);

        cPanel = new ControllerPanel();
        cPanel.setTranslateY(salle.getHauteur() + (3 * marge));



        cPanel.getPlayButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.demarrerV2();
                if(salle.isRunning()){
                    cPanel.setStatusLabel(true);
                }
            }
        });

        cPanel.getPauseButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.pause();
                cPanel.setStatusLabel(false);
            }
        });

        cPanel.getClearButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.removeAllPersonne();
                cPanel.setStatusLabel(false);
            }
        });


        getChildren().add(cSalle);
        getChildren().add(cPanel);
    }

    public void addControllerObstacle(ControllerObstacleRectangle controllerObstacleRectangle) {
        cSalle.afficherControllerObstacle(controllerObstacleRectangle);
    }

    public void addControllerSortie(ControllerSortie controllerSortie) {
        //cp.afficherSortie(controllerSortie);
        getChildren().add(controllerSortie);
    }


    public ControllerSalle getcSalle() {
        return cSalle;
    }

    public ControllerPanel getcPanel() {
        return cPanel;
    }
}
