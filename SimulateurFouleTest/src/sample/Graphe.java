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

    public void creerTousLesChemins() {
        for (Obstacle obstacle : salle.getListObstacles()) {
            for (Point point : obstacle.getCoins()) {
                for (Obstacle obstacle1 : salle.getListObstacles()) {
                    for (Point point1 : obstacle1.getCoins()) {
                        if (!salle.intersecObstacle(point, point1))
                            addChemin(new Chemin(point, point1));
                    }
                }
                Point pointSortie = salle.findSortiePlusProcheDirecte(point);
                if (pointSortie != null) {
                    addChemin(new Chemin(point, pointSortie));
                }
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
