package controller;

import javafx.scene.Parent;
import physique.Chemin;
import physique.Graphe;

public class ControllerGraphe extends Parent {

    public ControllerGraphe(Graphe graphe) {

        for (Chemin chemin : graphe.getListeChemins()) {
            controller.ControllerChemin controllerChemin = new controller.ControllerChemin(chemin);
            getChildren().add(controllerChemin);
        }
    }
}
