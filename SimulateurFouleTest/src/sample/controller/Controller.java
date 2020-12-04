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
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;
import sample.Obstacle;
import sample.ObstaclePolygone;
import sample.Point;
import sample.Salle;

public class Controller extends Parent{
    private ControllerSalle cSalle;
    private ControllerPanel cPanel;
    private List<Point> creerObstacle;
    private List<Circle> pointObstacle;
    private List<Line> ligneObstacle;
    private boolean creationObstacle;
    private boolean suppressionObstacle;
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
        pointObstacle = new ArrayList<>();
        ligneObstacle = new ArrayList<>();
        suppressionObstacle = false;
        
        // Event utilisé pour ajouter des Personnes, Obstacles en cliquant.
        cSalle.getSalleGraphique().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!salle.isRunning() && !creationObstacle && !suppressionObstacle){
                    cSalle.afficherPersonne(cSalle.createPersonne(event.getX(),event.getY()));
                } else if(!salle.isRunning() && creationObstacle && !suppressionObstacle){
                    Circle c = new Circle(event.getX()+marge, event.getY()+marge, 10);
                    getChildren().add(c);
                    pointObstacle.add(c);
                    if(pointObstacle.size()>1){
                        Line l = new Line(pointObstacle.get(pointObstacle.size()-2).getCenterX(),pointObstacle.get(pointObstacle.size()-2).getCenterY() , event.getX()+marge, event.getY()+marge);
                        getChildren().add(l);
                        ligneObstacle.add(l);
                    }
                    if(pointObstacle.size()>2){
                        if(pointObstacle.size()>3) getChildren().remove(getChildren().get(getChildren().size()-3));
                        Line l = new Line(event.getX()+marge, event.getY()+marge, pointObstacle.get(0).getCenterX(), pointObstacle.get(0).getCenterY());
                        getChildren().add(l);
                        ligneObstacle.add(l);
                    }
                    
                    creerObstacle.add(new Point(event.getX(), event.getY()));
                }
            }
        });

    // Event utilisé pour supprimer un obstacle en cliquant.
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!salle.isRunning() && suppressionObstacle){
                    for(ControllerObstacle o : cSalle.getListObstacles()){
                        if(o.contains(event.getX(), event.getY())){
                            salle.removeObstacle(o.getObstacle());  
                            suppressionObstacle = false;
                            cPanel.visibility(true);
                        }
                    }
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
                cPanel.visibility(false);
            }
        });

        cPanel.getValiderObstacleButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                creationObstacle = false;
                cPanel.visibility(true);
                if(!creerObstacle.isEmpty()){
                    salle.addObstacle(new ObstaclePolygone(creerObstacle));
                }
                creerObstacle.clear();
                for(Circle c : pointObstacle) getChildren().remove(c);
                for(Line l : ligneObstacle) getChildren().remove(l);
                pointObstacle.clear();
                ligneObstacle.clear();
                salle.refreshAffichage();
            }
        });

        cPanel.getSupprimerObstacleButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                suppressionObstacle = true;
                cPanel.visibility(false);
                cPanel.getValiderObstacleButton().setVisible(false);
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
