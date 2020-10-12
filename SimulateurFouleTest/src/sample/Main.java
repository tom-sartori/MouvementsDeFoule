package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
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

        Group root = new Group();

        Salle salle = new Salle(largeur,hauteur);

        Sortie sortie = new Sortie(2,60,50);
        Sortie sortie1 = new Sortie(3, 60, 80);
        salle.addSortie(sortie);
        salle.addSortie(sortie1);

        Obstacle obstacle = new ObstacleRectangle(100, 100, 200, 150);
        salle.addObstacle(obstacle);


        Personne personne = new Personne(500, 200);
        personne.avancer(salle);

        Personne personne1 = new Personne(50, 200);
        personne1.avancer(salle);


        root.getChildren().addAll(salle,personne, personne1);
        Scene scene = new Scene(root, largeur, hauteur, Color.LIGHTGRAY);


        primaryStage.setTitle("TEST");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
