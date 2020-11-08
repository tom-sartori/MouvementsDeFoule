package sample;

import java.util.ArrayList;
import java.util.List;

public class ObstacleRectangle extends Obstacle {

    private List<Point> listcoins;
    private List<Point> listDiagonales;

    public ObstacleRectangle(double x, double y, double largeur, double hauteur) {
        listcoins= new ArrayList<>();
        listcoins.add(new Point(x, y));     // haut gauche
        listcoins.add(new Point(x + largeur, y));   // haut droit
        listcoins.add(new Point(x + largeur, y + hauteur));     // bas droit
        listcoins.add(new Point(x, y + hauteur));   // bas gauche

        listDiagonales= new ArrayList<>();
        listDiagonales.add(listcoins.get(0));
        listDiagonales.add(listcoins.get(2));
        listDiagonales.add(listcoins.get(1));
        listDiagonales.add(listcoins.get(3));
    }

    public boolean estDansObstacle(Point point) {
        if (listcoins.get(0).getX() <= point.getX() && point.getX() <= listcoins.get(1).getX() && listcoins.get(0).getY() <= point.getY() && point.getY() <= listcoins.get(2).getY())
            return true;
        else
            return false;
    }

    public ControllerObstacleRectangle afficher () {
        return new ControllerObstacleRectangle(this);
    }

    public double getLargeur() {
        return listcoins.get(1).getX() - listcoins.get(0).getX();
    }

    public double getHauteur() {
        return listcoins.get(2).getY() - listcoins.get(0).getY();
    }

    public List<Point> getCoins() {
        return listcoins;
    }

    public List<Point> getListcoins() {
        return listcoins;
    }

    public List<Point> getDiagonales() {
        return listDiagonales;
    }
}
