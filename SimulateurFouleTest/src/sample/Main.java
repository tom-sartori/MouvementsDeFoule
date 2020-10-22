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

        Sortie sortie = new Sortie(1,60,50);
        Sortie sortie1 = new Sortie(4, 60, 80);
        controller.getCS().getSalle().addSortie(sortie);
        controller.getCS().getSalle().addSortie(sortie1);
        
        primaryStage.setTitle("TEST");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
