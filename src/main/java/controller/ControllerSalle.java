package controller;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import physique.Obstacle;
import physique.Personne;
import physique.Salle;

import java.util.ArrayList;
import java.util.List;

public class ControllerSalle extends Parent{
    private Salle salle;
    private Rectangle salleGraphique;
    private List<ControllerPersonne> listeControllerPersonne;
    private List<ControllerObstacle> listeControllerObstacle;
    private ControllerGraphe controllerGraphe;
    private Controller controller;


    public ControllerSalle (Salle s) {
        this.salle = s;
        listeControllerPersonne = new ArrayList<>();
        listeControllerObstacle = new ArrayList<>();

        salleGraphique = new Rectangle(salle.getLargeur(), salle.getHauteur());
        salleGraphique.setFill(Color.LIGHTCYAN);
        //listeControllerPersonne = new ArrayList<>();

        this.getChildren().add(salleGraphique);
    }

    // Créer et affiche une personne en position x et y (Utilisé lorsque l'utilisateur ajoute des personnes manuellement)
    public ControllerPersonne createPersonne(double x, double y){
        Personne personne = new Personne(x, y);
        salle.addPersonne(personne);
        return personne.afficher();
    }

    public void afficherControllerObstacle(ControllerObstacle controllerObstacle) {
        listeControllerObstacle.add(controllerObstacle);
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

    public Rectangle getSalleGraphique(){
        return salleGraphique;
    }

    public boolean retirerObstacle(Obstacle obstacle){
        for (ControllerObstacle controllerObstacle : listeControllerObstacle) {
            System.out.println(listeControllerObstacle.size());
            if (controllerObstacle.getObstacle().equals(obstacle)) {
                getChildren().remove(controllerObstacle);
                return true;
            }
        }
        return false;
    }

	public List<ControllerObstacle> getListObstacles() {
		return listeControllerObstacle;
    }
    
    public void setController(Controller controller){
        this.controller = controller;
    }

    public Controller getController(){
        return controller;
    }
}
