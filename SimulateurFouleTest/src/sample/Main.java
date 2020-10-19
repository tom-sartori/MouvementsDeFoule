package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation.Status;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.awt.*;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        /*Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();*/
        double largeur = 1000;
        double hauteur = 600;

        Group root = new Group();   // Creation du groupe qui va tout contenir


        Salle salle = new Salle(largeur,hauteur);   // Creation salle


        Sortie sortie = new Sortie(2,60,50);
        Sortie sortie1 = new Sortie(3, 60, 80);
        salle.addSortie(sortie);
        salle.addSortie(sortie1);

        
        //Obstacle obstacle = new ObstacleRectangle(100, 100, 200, 150);
        //salle.addObstacle(obstacle);
            salle.setOnMouseClicked(new EventHandler<MouseEvent>() {

                @Override
                public void handle(MouseEvent event) {
                    if(!salle.isRunning()){
                    Controller.createPersonne(event.getX(),event.getY(), salle);
                    }
                }
            });

        Controller controller = new Controller();

        controller.getPlayButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.demarrer();
                if(salle.isRunning()){
                    controller.setStatusLabel(true);
                }
            }
        });

        controller.getPauseButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.pause();
                controller.setStatusLabel(false);
            }
        });

        controller.getClearButton().setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {
                salle.removeAllPersonne();
                controller.setStatusLabel(false);
            }
        });
        controller.setTranslateY(hauteur);
        controller.minWidth(largeur);
        controller.getStatusLabel().setTranslateX(largeur-200);
        root.getChildren().addAll(salle, controller);   // ajoute les elements au groupe
        Scene scene = new Scene(root, largeur, hauteur+40, Color.LIGHTGRAY);

        primaryStage.setTitle("Simulateur de foule");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
