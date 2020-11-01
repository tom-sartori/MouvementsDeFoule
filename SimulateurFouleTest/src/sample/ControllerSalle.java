package sample;

import java.util.ArrayList;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.animation.Animation;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.util.Duration;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

import java.util.List;

public class ControllerSalle extends Parent{
    private Salle salle;
    private double marge = 20;
    private List<ControllerSortie> listSorties;
    private List<ControllerPersonne> listPersonnes;
    private List<ControllerObstacleRectangle> listObstacles;
    private Timeline loop;
    
    public Timeline getLoop(){
        return loop;
    }
    public Salle getSalle(){
        return salle;   
    }

//<<<<<<< Joachim
    public List<ControllerSortie> getListSorties(){
        return listSorties;
    }
    public List<ControllerPersonne> getListPersonnes(){
        return listPersonnes;
    }
    public ControllerSalle(double lar, double hau){
        salle = new Salle(lar,hau);
        this.listSorties = new ArrayList<>();
        this.listPersonnes = new ArrayList<>();
        this.listObstacles = new ArrayList<>();
        Rectangle salleGraphic = new Rectangle(lar - (2 * marge), hau - (2 * marge));
        salleGraphic.setTranslateX(marge);
        salleGraphic.setTranslateY(marge);
        salleGraphic.setFill(Color.LIGHTCYAN);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {
//=======
    public ControllerSalle(double width, double height){
        salle = new Salle(width,height);

        //salle.addPersonne(new Personne(200, 300));

        salle.addSortie(new Sortie(2,60,50));
        salle.addSortie(new Sortie(3, 60, 80));

        salle.addObstacle(new ObstacleRectangle(150, 200, 60, 70));
        salle.addObstacle(new ObstacleRectangle(300, 400, 50, 50));
        salle.addObstacle(new ObstacleRectangle(600, 100, 70, 60));

        Graphe graphe = new Graphe(salle);
        Point depart = salle.getListObstacles().get(0).getCoins().get(3);
        Point arrive = salle.getListSorties().get(0).getPoint1();
        graphe.creerPlusCourtChemin(depart, arrive);


        List<Point> listeChemin = graphe.getListePointsCheminPlusCourt(depart, arrive);
        salle.addGraphe(graphe.afficher(listeChemin));



        //graphe.creerTousLesChemins();
        //salle.addGraphe(graphe.afficher());


        salle.setOnMouseClicked(new EventHandler<MouseEvent>() {
//>>>>>>> main

            @Override
            public void handle(MouseEvent event) {
                if(!isRunning()){
                    createPersonne(event.getX(),event.getY());
                }
            }
        });
        this.getChildren().add(salleGraphic);
    }   

      // Permet d'ajouter une sortie à la salle et la place correctement
    // Modifie x1 y1 x2 y2 de la sortie correspondante pour lui donner uniquement les coordonnées utiles
    // (donc pas les coords exterrieurs à la salle)
    public void addSortie (ControllerSortie sortieController) {
        listSorties.add(sortieController);
        double distance = sortieController.getSortie().getDistance();
        double epaisseur = sortieController.getSortie().getEpaisseur();
        int mur = sortieController.getMur();
        if (mur == 1) {
            sortieController.setTranslateX(marge + distance);
            sortieController.setTranslateY(marge - epaisseur);
        }
        if (mur == 2) {
            sortieController.setTranslateX(marge + salle.getLargeur());
            sortieController.setTranslateY(marge + distance);
        }
        if (mur == 3) {
            sortieController.setTranslateX(marge + distance);
            sortieController.setTranslateY(marge + salle.getHauteur());
        }
        if (mur == 4) {
            sortieController.setTranslateX(marge - epaisseur);
            sortieController.setTranslateY(marge + distance);
        }
        this.getChildren().add(sortieController);
        salle.addSortie(sortieController.getSortie());
    }

    public void createPersonne(double x, double y){
        ControllerPersonne personneController = new ControllerPersonne(x, y);
        listPersonnes.add(personneController);
        this.getChildren().add(personneController);
        salle.addPersonne(personneController.getPersonne());
    }

    public void removePersonne(ControllerPersonne personneController) {
        listPersonnes.remove(personneController);
        getChildren().remove(personneController);
        if (listPersonnes.isEmpty())
            loop.pause();
        salle.removePersonne(personneController.getPersonne());
    }

    public void removeAllPersonne(){
        if(loop!=null){
            loop.pause();
        }
        while(!listPersonnes.isEmpty())
            removePersonne(listPersonnes.get(0));
        salle.removeAllPersonne();
    }

    public void pause(){
        if(loop != null && loop.getStatus() == Animation.Status.RUNNING){
            loop.pause();
        }
    }

    public boolean isRunning(){
        if(loop!=null && loop.getStatus()==Animation.Status.RUNNING) return true;
        else return false;
    }

    public void startLoop(){
        if(!listPersonnes.isEmpty()){
            salle.initPersonneDxDy();
            if(loop==null){
                loop = new Timeline(new KeyFrame(Duration.millis(10), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent arg) {
                        for (int i = 0; i < listPersonnes.size(); i ++) {
                            if (listPersonnes.get(i).estSorti(salle))
                                removePersonne(listPersonnes.get(i));
                            else
                                listPersonnes.get(i).avancer();
                        }
                    }
                }));
                loop.setCycleCount(Timeline.INDEFINITE);
                loop.play();
            } else if(loop.getStatus() == Animation.Status.PAUSED){
                loop.play();
            }
        }
    }

    public void addGraphe(ControllerGraphe controllerGraphe) {
        getChildren().add(controllerGraphe);
    }
    
    public void addObstacle(ControllerObstacleRectangle obstacleController){
        listObstacles.add(obstacleController);
        this.getChildren().add(obstacleController);
        salle.addObstacle(obstacleController.getObstacle());
    }
}
