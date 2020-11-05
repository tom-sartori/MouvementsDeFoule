package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class ControllerPersonne extends Parent {

    private Personne personne;
    private Circle personneGraphique;

    public ControllerPersonne (Personne p) {
        personne = p;
        final double rayon = 5;

        personneGraphique = new Circle(personne.getCoordCourant().getX(), personne.getCoordCourant().getY(), rayon);
        personneGraphique.setFill(Color.RED);
        getChildren().add(personneGraphique);
    }

    public void deplacer (double x, double y) {
        personneGraphique.setCenterX(x);
        personneGraphique.setCenterY(y);
    }

    public Personne getPersonne() {
        return personne;
    }
}
