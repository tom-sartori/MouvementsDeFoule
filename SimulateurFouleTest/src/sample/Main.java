package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.util.Random;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 1500, 800, Color.LIGHTGRAY);


        Salle salle = new Salle(1000, 600);

        salle.addObstacle(new ObstacleRectangle(150, 200, 70, 30));
        salle.addObstacle(new ObstacleRectangle(300, 400, 50, 50));
        salle.addObstacle(new ObstacleRectangle(600, 100, 10, 200));

        salle.addSortie(new Sortie(2,60,50));
        salle.addSortie(new Sortie(3, 60, 80));
        salle.addSortie(new Sortie(3, 40, 600));

        salle.addPersonne(new Personne(300, 300));
        salle.addPersonne(new Personne(200, 50));

        for (int i = 0; i < 100; i++) {
            Random ran = new Random();
            double x = ran.nextInt(1000);
            double y = ran.nextInt(600);
            System.out.println(x + " " + y);
            salle.addPersonne(new Personne(x,y));
        }




        Controller controller = new Controller(salle);
        root.getChildren().add(controller);


        primaryStage.setTitle("Simulateur de foule");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();


    }


    public static void main(String[] args) {
        launch(args);
    }

}
