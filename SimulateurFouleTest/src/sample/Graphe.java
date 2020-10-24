package sample;

import java.util.ArrayList;
import java.util.List;

public class Graphe {

    private List<Chemin> listeChemins;
    private Salle salle;

    public Graphe (Salle salle) {
        listeChemins = new ArrayList<>();
        this.salle = salle;
    }

    public void addChemin (Chemin chemin) {
        listeChemins.add(chemin);
    }

    public void creerChemins () {
        for (Obstacle obstacle : salle.getListObstacles()) {
            for (Point point : obstacle.getCoins()) {
                for (Obstacle obstacle1 : salle.getListObstacles()) {
                    for (Point point1 : obstacle1.getCoins()) {
                        //if ()
                        addChemin(new Chemin(point, point1));
                    }
                }
                addChemin(new Chemin(point, salle.findSortiePlusProche(point)));
            }
        }
    }

    public ControllerGraphe afficher () {
        return new ControllerGraphe(this);
    }

    public List<Chemin> getListeChemins() {
        return listeChemins;
    }
}
