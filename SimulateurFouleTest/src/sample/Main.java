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

        /*
        Group root = new Group();   // Creation du groupe qui va tout contenir


        Salle salle = new Salle(largeur,hauteur);   // Creation salle


        salle.addPersonne(new Personne(100, 400));
        salle.addPersonne(new Personne(790, 200));
        salle.addPersonne(new Personne(150, 300));
        salle.addPersonne(new Personne(400, 350));
        salle.addPersonne(new Personne(450, 50));


        Sortie sortie = new Sortie(4,60,50);
        Sortie sortie1 = new Sortie(3, 60, 80);
        salle.addSortie(sortie);
        salle.addSortie(sortie1);


        //Obstacle obstacle = new ObstacleRectangle(100, 100, 200, 150);
        //salle.addObstacle(obstacle);


        salle.demarrer();


        root.getChildren().addAll(salle);   // ajoute les elements au groupe
        Scene scene = new Scene(root, largeur, hauteur, Color.LIGHTGRAY);

        primaryStage.setTitle("TEST");
        primaryStage.setScene(scene);
        primaryStage.show();

        */

        Controller controller = new Controller(largeur, hauteur);
        Scene scene = new Scene(controller, largeur, hauteur, Color.LIGHTGRAY);

        primaryStage.setTitle("Simulateur de foule");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }
}
