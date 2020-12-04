package sample;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sample.controller.Controller;

import java.util.ArrayList;
import java.util.List;


public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        Group root = new Group();
        Scene scene = new Scene(root, 1200, 800, Color.LIGHTGRAY);

        Salle salle = new Salle(1000, 600);

        salle.addObstacle(new ObstacleRectangle(150, 200, 70, 30));
        salle.addObstacle(new ObstacleRectangle(300, 400, 50, 50));
        salle.addObstacle(new ObstacleRectangle(600, 100, 45, 200));
        salle.addObstacle(new ObstacleRectangle(30, 500, 200, 60));
        salle.addObstacle(new ObstacleRectangle(400, 430, 200, 50));
        salle.addObstacle(new ObstacleRectangle(700, 302, 80, 100));
        salle.addObstacle(new ObstacleRectangle(820, 30, 70, 70));
        salle.addObstacle(new ObstacleRectangle(300, 80, 150, 150));
        //salle.addObstacle(new ObstacleTriangle(new Point(260,70),new Point(134,90),new Point(324,80)));


        List<Point> points = new ArrayList<>();
        points.add(new Point(100, 60));
        points.add(new Point(180, 80));
        points.add(new Point(160, 140));
        points.add(new Point(140, 180));
        points.add(new Point(70, 160));
        points.add(new Point(20, 140));
        points.add(new Point(30, 100));
        salle.addObstacle(new ObstaclePolygone(points));

        salle.addSortie(1, 150, 250);
        //salle.addSortie(2,404,41);
        //salle.addSortie(3, 802, 81);
        //salle.addSortie(4, 300, 13);

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
