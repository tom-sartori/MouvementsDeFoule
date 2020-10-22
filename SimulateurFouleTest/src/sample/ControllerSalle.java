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

public class ControllerSalle extends Parent{
    private Salle salle;
    private double marge = 20;
    private List<ControllerSortie> listSorties;
    private List<ControllerPersonne> listPersonnes;
    private Timeline loop;
    
    public Timeline getLoop(){
        return loop;
    }
    public Salle getSalle(){
        return salle;   
    }

    public List<ControllerSortie> getListSorties(){
        return listSorties;
    }
    public List<ControllerPersonne> getListPersonnes(){
        return listPersonnes;
    }
    public ControllerSalle(double width, double height, Salle salle){
        this.salle = salle;
        this.listSorties = new ArrayList<>();
        this.listPersonnes = new ArrayList<>();
        Rectangle salleGraphic = new Rectangle(width - (2 * marge), height - (2 * marge));
        salleGraphic.setTranslateX(marge);
        salleGraphic.setTranslateY(marge);
        salleGraphic.setFill(Color.LIGHTCYAN);
        this.setOnMouseClicked(new EventHandler<MouseEvent>() {

            @Override
            public void handle(MouseEvent event) {
                if(!isRunning()){
                    salle.addPersonne(event.getX(),event.getY());
                }
            }
        });
        
        this.getChildren().add(salleGraphic);
    }   

      // Permet d'ajouter une sortie à la salle et la place correctement
    // Modifie x1 y1 x2 y2 de la sortie correspondante pour lui donner uniquement les coordonnées utiles
    // (donc pas les coords exterrieurs à la salle)
    public void addSortie (ControllerSortie sortie, int mur) {
        listSorties.add(sortie);
        double distance = sortie.getSortie().getDistance();
        double epaisseur = sortie.getSortie().getEpaisseur();
        if (mur == 1) {
            sortie.setTranslateX(marge + distance);
            sortie.setTranslateY(marge - epaisseur);
        }
        if (mur == 2) {
            sortie.setTranslateX(marge + salle.getLargeur());
            sortie.setTranslateY(marge + distance);
        }
        if (mur == 3) {
            sortie.setTranslateX(marge + distance);
            sortie.setTranslateY(marge + salle.getHauteur());
        }
        if (mur == 4) {
            sortie.setTranslateX(marge - epaisseur);
            sortie.setTranslateY(marge + distance);
        }
        this.getChildren().add(sortie);
    }

    public void createPersonne(ControllerPersonne personne){
        listPersonnes.add(personne);
        this.getChildren().add(personne);
    }

    public void removePersonneGraphic (ControllerPersonne personne) {
        listPersonnes.remove(personne);
        getChildren().remove(personne);
        if (listPersonnes.isEmpty())
            loop.pause(); 
    }

    public void removeAllPersonneGraphic(){
        if(loop!=null){
            loop.pause();
        }
        while(!listPersonnes.isEmpty())
            removePersonneGraphic(listPersonnes.get(0));

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
                        salle.demarrer();
                    }
                }));
                loop.setCycleCount(Timeline.INDEFINITE);
                loop.play();
            } else if(loop.getStatus() == Animation.Status.PAUSED){
                loop.play();
            }
        }
    }

}
