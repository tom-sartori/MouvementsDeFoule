package sample;

import java.util.List;

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

//<<<<<<< Joachim
        controller.getCS().addSortie(new ControllerSortie(2,60,50));
        controller.getCS().addSortie(new ControllerSortie(3, 60, 80));

        controller.getCS().addObstacle(new ControllerObstacleRectangle(150, 200, 60, 70));
        controller.getCS().addObstacle(new ControllerObstacleRectangle(300, 400, 50, 50));
        controller.getCS().addObstacle(new ControllerObstacleRectangle(600, 100, 70, 60));

        Graphe graphe = new Graphe(controller.getCS().getSalle());
        Point depart = controller.getCS().getSalle().getListObstacles().get(0).getCoins().get(3);
        Point arrive = controller.getCS().getSalle().getListSorties().get(0).getPoint1();
        System.out.println(arrive);
        graphe.creerPlusCourtChemin(depart, arrive);


        List<Point> listeChemin = graphe.getListePointsCheminPlusCourt(depart, arrive);
        controller.getCS().addGraphe(graphe.afficher(listeChemin));

        graphe.creerTousLesChemins();
        controller.getCS().addGraphe(graphe.afficher());


        primaryStage.setTitle("Simulateur de foule");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }

}
