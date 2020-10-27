package sample;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        double largeur = 1000;
        double hauteur = 600;
        Controller controller = new Controller(largeur, hauteur);
        Scene scene = new Scene(controller, largeur, hauteur, Color.LIGHTGRAY);

        controller.getCS().addSortie(new ControllerSortie(2,60,50));
        controller.getCS().addSortie(new ControllerSortie(3, 60, 80));

        controller.getCS().addObstacle(new ControllerObstacleRectangle(150, 200, 60, 70));
        controller.getCS().addObstacle(new ControllerObstacleRectangle(300, 400, 50, 50));
        controller.getCS().addObstacle(new ControllerObstacleRectangle(600, 100, 70, 60));
        
        primaryStage.setTitle("TEST");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
