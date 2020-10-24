package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class ControllerChemin extends Parent {

    private Chemin chemin;

    public ControllerChemin(Chemin chemin) {
        Line line = new Line();
        line.setStartX(chemin.getA().getX());
        line.setStartY(chemin.getA().getY());
        line.setEndX(chemin.getB().getX());
        line.setEndY(chemin.getB().getY());

        line.setStroke(Color.RED);
        getChildren().add(line);
        this.chemin = chemin;
    }
}
