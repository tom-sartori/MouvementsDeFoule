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
                salle.setVitessePersonnes(cPanel.getVitesseValue());
                if(cPanel.getCollisionStatus())
                    salle.play(true);
                else salle.play(false);
            }
        });

        cPanel.getPauseButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.pause();
            }
        });

        cPanel.getClearButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.removeAllPersonne();
            }
        });

        cPanel.getGrapheCB().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                if(cPanel.getGrapheCB().isSelected())
                    salle.initialisationGrapheAvecAffichage();
                else cSalle.cacherGraphe();
            }
        });

        getChildren().add(cSalle);
        getChildren().add(cPanel);
    }
}
