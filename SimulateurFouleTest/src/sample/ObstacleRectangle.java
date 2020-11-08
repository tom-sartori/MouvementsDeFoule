package sample;

import java.util.ArrayList;
import java.util.List;

public class ObstacleRectangle implements Obstacle {

    private List<Point> listePoints;
    private List<Point> listDiagonales;

    public ObstacleRectangle(double x, double y, double largeur, double hauteur) {
        listePoints = new ArrayList<>();
        listePoints.add(new Point(x, y));     // haut gauche
        listePoints.add(new Point(x + largeur, y));   // haut droit
        listePoints.add(new Point(x + largeur, y + hauteur));     // bas droit
        listePoints.add(new Point(x, y + hauteur));   // bas gauche

        listDiagonales= new ArrayList<>();
        listDiagonales.add(listePoints.get(0));
        listDiagonales.add(listePoints.get(2));
        listDiagonales.add(listePoints.get(1));
        listDiagonales.add(listePoints.get(3));
    }

    @Override
    public boolean estDansObstacle(Point point) {
        if (listePoints.get(0).getX() <= point.getX() && point.getX() <= listePoints.get(1).getX() && listePoints.get(0).getY() <= point.getY() && point.getY() <= listePoints.get(2).getY())
            return true;
        else
            return false;
    }

    public ControllerObstacleRectangle afficher () {
        return new ControllerObstacleRectangle(this);
    }

    public double getLargeur() {
        return listePoints.get(1).getX() - listePoints.get(0).getX();
    }

    public double getHauteur() {
        return listePoints.get(2).getY() - listePoints.get(0).getY();
    }

    @Override
    public List<Point> getListePoints() {
        return listePoints;
    }

    @Override
    public List<Point> getDiagonales() {
        return listDiagonales;
    }
}
