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

        Personne personne = new Personne(500, 200);
        Personne personne1 = new Personne(50, 200);

        root.getChildren().addAll(salle,personne, personne1);
        Scene scene = new Scene(root, largeur, hauteur, Color.LIGHTGRAY);



        double [] coordSortie = personne.findCoordSortie(salle);
        System.out.println("xArrive : " + coordSortie[0]);
        System.out.println("yArrive : " + coordSortie[1]);
        double [] coordDxDy = personne.findDxDy(coordSortie[0], coordSortie[1], (int)coordSortie[2]);

        double dx = coordDxDy[0];
        double dy = coordDxDy[1];


        double[] coordDxDy1 = personne1.getDxDy(salle);
        double dx1 = coordDxDy1[0];
        double dy1 = coordDxDy1[1];

        //double dx = personne.findDx(coordSortie[0], coordSortie[1], (int)coordSortie[2]);
        //double dy = personne.findDy(coordSortie[0], coordSortie[1], (int)coordSortie[2]);


        Timeline loop = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
            public void handle(ActionEvent arg) {

                personne.setTranslateX(personne.getTranslateX() + dx);
                personne.setTranslateY(personne.getTranslateY() + dy);

                personne1.setTranslateX(personne1.getTranslateX() + dx1);
                personne1.setTranslateY(personne1.getTranslateY() + dy1);

            }
        }));

        loop.setCycleCount(Timeline.INDEFINITE);
        loop.play();

        primaryStage.setTitle("TEST");
        primaryStage.setScene(scene);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
