package sample;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;

import java.util.List;

public class ControllerSalle extends Parent{
    private Salle salle;

    public Salle getSalle(){
        return salle;
    }

    public ControllerSalle(double width, double height){
        salle = new Salle(width,height);

        //salle.addPersonne(new Personne(200, 300));

        salle.addSortie(new Sortie(2,60,50));
        salle.addSortie(new Sortie(3, 60, 80));

        salle.addObstacle(new ObstacleRectangle(150, 200, 60, 70));
        salle.addObstacle(new ObstacleRectangle(300, 400, 50, 50));
        salle.addObstacle(new ObstacleRectangle(600, 100, 70, 60));
        salle.addObstacle(new ObstacleRectangle(450, 100, 30, 30));

        //Graphe graphe = new Graphe(salle);
        /*
        Point depart = salle.getListObstacles().get(0).getCoins().get(0);
        Point depart1 = salle.getListObstacles().get(2).getCoins().get(1);
        Point depart2 = salle.getListObstacles().get(1).getCoins().get(2);
        Point arrive = salle.getListSorties().get(0).getPoint1();
        Point arrive1 = salle.getListSorties().get(1).getPoint2();
        graphe.creerPlusCourtChemin(depart1);
        */
        //graphe.creerCheminPlusCourtAvecSortie();

        salle.getGraphe().creerCheminPlusCourtAvecSortie();
        //salle.addGraphe(graphe);
        salle.afficherGraphe(salle.getGraphe().afficher());



        //List<Point> listeChemin = graphe.getListePointsCheminPlusCourt(depart1, arrive);
        //salle.addGraphe(graphe.afficher(listeChemin));



        //graphe.creerTousLesChemins();
        //salle.addGraphe(graphe.afficher());


        salle.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(!salle.isRunning()){
                    createPersonne(event.getX(),event.getY());
                }
            }
        });
        
        this.getChildren().add(salle);
    }   

    public void createPersonne(double x, double y){
        Personne personne = new Personne(x, y);
        salle.addPersonne(personne);
    }
}
