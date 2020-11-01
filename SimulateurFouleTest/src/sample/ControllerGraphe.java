package sample;

import javafx.scene.Parent;

public class ControllerGraphe extends Parent {

    private Graphe graphe;

    public ControllerGraphe(Graphe graphe) {
        this.graphe = graphe;

        for (Chemin chemin : graphe.getListeChemins()) {
            ControllerChemin controllerChemin = new ControllerChemin(chemin);
            getChildren().add(controllerChemin);
        }
    }
}
