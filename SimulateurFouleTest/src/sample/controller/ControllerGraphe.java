package sample.controller;

import javafx.scene.Parent;
import sample.Chemin;
import sample.Graphe;

public class ControllerGraphe extends Parent {

    public ControllerGraphe(Graphe graphe) {

        for (Chemin chemin : graphe.getListeChemins()) {
            ControllerChemin controllerChemin = new ControllerChemin(chemin);
            getChildren().add(controllerChemin);
        }
    }
}
