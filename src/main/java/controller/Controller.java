package controller;

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

import physique.*;

import java.util.ArrayList;
import java.util.List;

public class Controller extends Parent{
    private ControllerSalle cSalle;
    private ControllerPanel cPanel;
    private Salle salle;
    private List<Point> creerObstacle; //Liste des points placés par l'utilisateur lors de la création d'un obstacle
    private List<Circle> pointObstacle; //Affichage des points (creerObstacle) en cercle
    private List<Line> ligneObstacle; //Affichage des lignes entre les points (creerObstacle) pour avoir un aperçu de la forme avant de valider
    private boolean creationObstacle; //Permet de savoir si l'utilisateur utilise l'option de creation d'obstacle
    private boolean suppressionObstacle; //Permet de savoir si l'utilisateur utilise l'option de suppression d'obstacle
    


    public Controller (Salle salle) {
        double marge = 30;
        this.salle = salle;
        
        cSalle = salle.afficher();
        cSalle.setTranslateX(marge);
        cSalle.setTranslateY(marge);
        cSalle.setController(this);

        cPanel = new ControllerPanel();
        cPanel.setTranslateY(salle.getHauteur() + (3 * marge));

        creerObstacle = new ArrayList<>();
        pointObstacle = new ArrayList<>();
        ligneObstacle = new ArrayList<>();
        suppressionObstacle = false;
        
        // Event utilisé pour ajouter des Personnes, Obstacles en lors d'un clique.
        // Seulement lorsque la simulation n'est pas lancée
        cSalle.getSalleGraphique().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                double rayon = new Personne().getRayon();
                double x = event.getX();
                double y = event.getY();
                if((x+marge > marge+rayon && x < salle.getLargeur()-rayon)){
                    if(!salle.isRunning() && !creationObstacle && !suppressionObstacle){
                        cSalle.afficherPersonne(cSalle.createPersonne(x,y));
                    } else if(!salle.isRunning() && creationObstacle && !suppressionObstacle){
                        Circle c = new Circle(x+marge, y+marge, 10);
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
            }
        });

        // Event utilisé pour supprimer un obstacle en cliquant.
        // Seulement lorsque la simulation n'est pas lancée
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!salle.isRunning() && suppressionObstacle){
                    for(ControllerObstacle o : cSalle.getListObstacles()){
                        if(o.contains(event.getX()-marge, event.getY()-marge)){
                            salle.removeObstacle(o.getObstacle());  
                            suppressionObstacle = false;
                            cPanel.visibility(true);
                            cPanel.getGroupeGraphe().getSelectedToggle().setSelected(false);
                            cPanel.getEnleverGrapheRB().setSelected(true);
                            cSalle.cacherGraphe();
                        }
                    }
                }
            }
        });

        // Event qui lance la simulation lorsque l'utilisateur appuie sur le bouton play.
        // Prends en compte les différents paramètres (checkbox) de gestion des collisions.
        cPanel.getPlayButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                salle.setVitessePersonnes(cPanel.getVitesseValue());
                if(cPanel.getCollisionStatus()){
                    salle.play(true);
                }
                else
                    salle.play(false);
                if(salle.isRunning())
                    cPanel.setLoopTimer(0);
            }
        });

        // Event qui met en pause la simulation lorsque l'utilisateur clique sur le bouton pause.
        cPanel.getPauseButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                salle.pause();
                cPanel.setLoopTimer(1);
            }
        });

        // Event qui supprime toutes les personnes de la salle et stoppe la simulation lorsque l'utilisateur clique sur clear.
        cPanel.getClearButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                salle.removeAllPersonne();
                cPanel.setLoopTimer(2);
            }
        });

        // Event qui affiche ou cache le graphe des chemins les plus courts lorsque l'utilisateur active ou désactive la checkbox correspondante.
        cPanel.getGrapheRB().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                cSalle.cacherGraphe();
                salle.initialisationGrapheAvecAffichage("");
            }
        });

        cPanel.getGrapheDiagonaleRB().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                cSalle.cacherGraphe();
                salle.initialisationGrapheAvecAffichage("Diagonale");
            }
        });

        cPanel.getGrapheCartesienRB().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                cSalle.cacherGraphe();
                salle.initialisationGrapheAvecAffichage("Cartesien");
            }
        });

        cPanel.getGraphePhysiqueRB().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                cSalle.cacherGraphe();
                salle.initialisationGrapheAvecAffichage("Physique");
            }
        });

        cPanel.getEnleverGrapheRB().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                cSalle.cacherGraphe();
            }
        });

        // Event qui ouvre un popup pour ajouter un nombre de personnes définis lorsque l'utilisateur clique sur le bouton correspondant.
        cPanel.getAddPersonButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(!salle.isRunning())
                    createPopup("Personne");
            }
        });

        // Event qui permet d'activer l'option de creation d'obstacle lorsque l'utilisateur clique sur le bouton correspondant.
        cPanel.getAddObstacleButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(!salle.isRunning()){
                    creationObstacle = true;
                    cPanel.visibility(false);
                }
            }
        });

         // Event qui permet de valider la creation d'un obstacle lorsque l'utilisateur clique sur le bouton valider.
         cPanel.getValiderObstacleButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                boolean intersec = false;
                double rayon = new Personne().getRayon();
                for(int i=0;i<pointObstacle.size() && !intersec;i++){
                    Point A = new Point(pointObstacle.get(i).getCenterX()-marge,pointObstacle.get(i).getCenterY()-marge);
                    Point B;
                    if(i==pointObstacle.size()-1) B = new Point(pointObstacle.get(0).getCenterX()-marge,pointObstacle.get(0).getCenterY()-marge);
                    else B = new Point(pointObstacle.get(i+1).getCenterX()-marge,pointObstacle.get(i+1).getCenterY()-marge);
                    if(salle.intersecObstacle(A, B)) {
                        intersec = true;
                    }
                }
                creationObstacle = false;
                cPanel.visibility(true);
                if(!intersec){
                    if(!creerObstacle.isEmpty() && creerObstacle.size()>2){
                        Obstacle obstacle = new ObstaclePolygone(creerObstacle);
                        ControllerObstacle obstacleGraphique = obstacle.afficher();
                        salle.addObstacle(obstacle);
                        cSalle.afficherControllerObstacle(obstacleGraphique);
                        List<ControllerObstacle> obstacles = cSalle.getListObstacles();
                        for(ControllerObstacle o : obstacles){
                                if(!obstacleGraphique.equals(o)){
                                    if(obstacleGraphique.contains(o.getObstacle().getListePointsGraphiques().get(0).getX(),o.getObstacle().getListePointsGraphiques().get(0).getY())) salle.removeObstacle(o.getObstacle());
                                }
                        }
                        cPanel.getGroupeGraphe().getSelectedToggle().setSelected(false);
                        cPanel.getEnleverGrapheRB().setSelected(true);
                        cSalle.cacherGraphe();
                    }
                }
                creerObstacle.clear();
                for(Circle c : pointObstacle) getChildren().remove(c);
                for(Line l : ligneObstacle) getChildren().remove(l);
                pointObstacle.clear();
                ligneObstacle.clear();
            }
        });
        // Event qui permet d'activer l'option pour supprimer un obstacle lorsque l'utilisateur clique sur le bouton correspondant.
        cPanel.getSupprimerObstacleButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                if(!salle.isRunning() && !salle.getListObstacles().isEmpty()){
                    suppressionObstacle = true;
                    cPanel.visibility(false);
                    cPanel.getValiderObstacleButton().setVisible(false);
                }
            }
        });

        getChildren().addAll(cSalle, cPanel);
    }

    // Ouvre un popup permettant d'ajouter des personnes aléatoirement dans la salle.
    public void createPopup(String objet){
        Group root = new Group();
        Stage popup = new Stage(); 
        Scene scene = new Scene(root, 400, 100, Color.LIGHTGRAY);
        if(objet.equalsIgnoreCase("Personne")){
            popup.setTitle("Ajouter personne");
            TextField value = new TextField("0");
            value.setPrefWidth(80);
            value.setTranslateX(160);
            Button confirm = cPanel.createButton("Ajouter", 150);
            confirm.setPrefWidth(100);
            confirm.setTranslateY(50);

            confirm.setOnAction(new EventHandler<ActionEvent>() {
                @Override
                public void handle(ActionEvent e) {
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

    public ControllerPanel getControllerPanel(){
        return cPanel;
    }
}
