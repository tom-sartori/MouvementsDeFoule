package sample.controller;

import java.util.ArrayList;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.ObstaclePolygone;
import sample.Point;
import sample.Salle;

public class Controller extends Parent{
    private ControllerSalle cSalle;
    private ControllerPanel cPanel;
    private List<Point> creerObstacle;
    private boolean creationObstacle;
    private Salle salle;


    public Controller (Salle salle) {
        double marge = 30;
        this.salle = salle;
        
        cSalle = salle.afficher();
        cSalle.setTranslateX(marge);
        cSalle.setTranslateY(marge);

        cPanel = new ControllerPanel();
        cPanel.setTranslateY(salle.getHauteur() + (3 * marge));

        creerObstacle = new ArrayList<>();
        
        // Event utilisé pour ajouter des Personne en cliquant.
        cSalle.getSalleGraphique().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!salle.isRunning() && !creationObstacle){
                    cSalle.afficherPersonne(cSalle.createPersonne(event.getX(),event.getY()));
                } else if(!salle.isRunning() && creationObstacle){
                    creerObstacle.add(new Point(event.getX(), event.getY()));
                }
            }
        });

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

        cPanel.getAddObstacleButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                creationObstacle = true;
                cPanel.getPlayButton().setVisible(false);
                cPanel.getPauseButton().setVisible(false);
                cPanel.getClearButton().setVisible(false);
                cPanel.getValiderObstacleButton().setVisible(true);
                cPanel.getAddObstacleButton().setVisible(false);
            }
        });

        cPanel.getValiderObstacleButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                creationObstacle = false;
                cPanel.getPlayButton().setVisible(true);
                cPanel.getPauseButton().setVisible(true);
                cPanel.getClearButton().setVisible(true);
                cPanel.getValiderObstacleButton().setVisible(false);
                cPanel.getAddObstacleButton().setVisible(true);
                if(!creerObstacle.isEmpty()){
                    salle.addObstacle(new ObstaclePolygone(creerObstacle));
                }
                creerObstacle.clear();
                salle.refreshAffichage();
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
