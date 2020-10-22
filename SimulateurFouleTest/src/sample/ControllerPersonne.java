package sample;

import javafx.scene.Parent;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class ControllerPersonne extends Parent{
    private final double r = 15;
    private Personne personne;

    public ControllerPersonne(double x, double y, Personne personne){
        this.personne = personne;
        Circle personneGraphic = new Circle(x, y, r);
        personneGraphic.setFill(Color.RED);
        this.getChildren().add(personneGraphic);
    }

    public void run(double dx, double dy){
        setTranslateX(getTranslateX() + dx);
        setTranslateY(getTranslateY() + dy);
    }

        // Permet de savoir si le perso est sorti de la salle
        public boolean estSorti(Salle salle) {
            double dx = personne.getDx();
            double dy = personne.getDy();
            double xDepart = personne.getxDepart();
            double yDepart = personne.getyDepart();
            if (dx > 0) {
                if (xDepart + getTranslateX() > salle.getLargeur() + salle.getMarge())
                    return true;
            }
            if (dx < 0) {
                System.out.println(xDepart + getTranslateX());
                if (xDepart + getTranslateX() < 0 + salle.getMarge())
                    return true;
            }
            if (dy > 0) {
                if (yDepart + getTranslateY() > salle.getHauteur() + salle.getMarge())
                    return true;
            }
            if (dy < 0) {
                if (yDepart + getTranslateY() < salle.getMarge())
                    return true;
            }
            return false;
        }
}
