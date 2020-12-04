package sample.controller;

import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sample.Personne;
import sample.Salle;

import java.util.ArrayList;
import java.util.List;

public class ControllerSalle extends Parent{
    private Salle salle;
    private Rectangle salleGraphique;
    private List<ControllerPersonne> listeControllerPersonne;
    private ControllerGraphe controllerGraphe;


    public ControllerSalle (Salle s) {
        this.salle = s;
        listeControllerPersonne = new ArrayList<>();

        salleGraphique = new Rectangle(salle.getLargeur(), salle.getHauteur());
        salleGraphique.setFill(Color.LIGHTCYAN);
        //listeControllerPersonne = new ArrayList<>();


        // Event utilisé pour ajouter des Personne en cliquant.
        salleGraphique.setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent event) {
                if(!salle.isRunning()){
                    afficherPersonne(createPersonne(event.getX(),event.getY()));
                }
            }
        });

        this.getChildren().add(salleGraphique);
    }

    public ControllerPersonne createPersonne(double x, double y){
        Personne personne = new Personne(x, y);
        salle.addPersonne(personne);
        return personne.afficher();
    }

    public void afficherControllerObstacle(ControllerObstacle controllerObstacle) {
        getChildren().add(controllerObstacle);
    }

    public void afficherSortie(ControllerSortie controllerSortie) {
        getChildren().add(controllerSortie);
    }

    public void afficherPersonne(ControllerPersonne controllerPersonne) {
        listeControllerPersonne.add(controllerPersonne);
        getChildren().add(controllerPersonne);
    }

    // Retourne boolean uniquement utilisé pour faire un parcours partiel
    public boolean deplacerPersonne(Personne personne) {
        for (ControllerPersonne controllerPersonne : listeControllerPersonne) {
            if (controllerPersonne.getPersonne().equals(personne)) {
                controllerPersonne.deplacer(personne.getCoordCourant().getX(), personne.getCoordCourant().getY());
                return true;
            }
        }
        return false;
    }

    // Retourne boolean uniquement utilisé pour faire un parcours partiel
    public boolean retirerPersonne (Personne personne) {
        for (ControllerPersonne controllerPersonne : listeControllerPersonne) {
            if (controllerPersonne.getPersonne().equals(personne)) {
                getChildren().remove(controllerPersonne);
                return true;
            }
        }
        return false;
    }

    public void afficherGraphe (ControllerGraphe controllerGraphe) {
        this.controllerGraphe = controllerGraphe;
        getChildren().add(controllerGraphe);
    }

    public void cacherGraphe(){
        if(controllerGraphe != null)
            getChildren().remove(controllerGraphe);
    }
}
