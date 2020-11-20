package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Controller extends Parent{
    private ControllerSalle cSalle;
    private ControllerPanel cPanel;
    private Salle salle;


    public Controller (Salle salle) {
        double marge = 30;
        this.salle = salle;
        
        cSalle = salle.afficher();
        cSalle.setTranslateX(marge);
        cSalle.setTranslateY(marge);

        cPanel = new ControllerPanel();
        cPanel.setTranslateY(salle.getHauteur() + (3 * marge));
        

        cPanel.getPlayButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.setVitessePersonnes(cPanel.getVitesseValue());
                if(cPanel.getCollisionStatus()){
                    if(cPanel.getRayonStatus()) salle.play(true, true);
                    else salle.play(true, false);
                }else if(cPanel.getRayonStatus()) salle.play(false, true);
                else salle.play(false, false);
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

        cPanel.getAddPersonButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                createPopup("Personne");
            }
        });

        getChildren().add(cSalle);
        getChildren().add(cPanel);
    }

    public void createPopup(String objet){
        Group root = new Group();
        Stage popup = new Stage(); 
        Scene scene = new Scene(root, 400, 100, Color.LIGHTGRAY);
        if(objet.equalsIgnoreCase("Personne")){
            popup.setTitle("Ajouter personne");
            TextField value = new TextField("0");
            value.setMinWidth(80);
            Button confirm = cPanel.createButton("Ajouter", 110);

            confirm.setOnAction(new EventHandler<ActionEvent>() {
                @Override public void handle(ActionEvent e) {
                    int v = Integer.parseInt(value.getText());
                    salle.addRandomPersonnes(v);
                    popup.close();
                }
            });

            root.getChildren().addAll(value, confirm);
        }
        popup.setScene(scene);
        popup.show();
    }
}
