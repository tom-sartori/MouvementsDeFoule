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
        //salle.addObstacle(new ObstacleRectangle(300, 400, 50, 50));
        salle.addObstacle(new ObstacleRectangle(600, 100, 15, 200));
        salle.addObstacle(new ObstacleRectangle(30, 500, 200, 60));
        //salle.addObstacle(new ObstacleRectangle(400, 430, 200, 50));
        salle.addObstacle(new ObstacleRectangle(700, 300, 80, 100));
        salle.addObstacle(new ObstacleRectangle(820, 30, 70, 70));
        salle.addObstacle(new ObstacleRectangle(330, 80, 150, 150));
        salle.addObstacle(new ObstacleTriangle(new Point(300, 480), new Point(350, 480), new Point(400, 520)));

        salle.addSortie(1, 80, 20);
        //salle.addSortie(2,400,40);
        //salle.addSortie(3, 20, 80);
        //salle.addSortie(4, 180, 100);


        //salle.addRandomPersonnes(100);

      
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
