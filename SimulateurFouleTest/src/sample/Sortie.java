package sample;

import java.util.ArrayList;
import java.util.List;

public class Sortie {
    private int mur;
    private double longueur;
    private double distance;

    private Point point1;  // temporaire avant de modifier toute la classe avec juste point1 et point2 sans x1, y1, x2, y2.
    private Point point2;
    private List<Point> listePointsSortie;


    public Sortie(int m, double l, double d) {
        this.mur = m;
        this.longueur = l;
        this.distance = d;

        point1 = new Point();   // Initialisés lorsqu'on ajoute la sortie à la salle.
        point2 = new Point();

        listePointsSortie = new ArrayList<>();
        listePointsSortie.add(point1);
        listePointsSortie.add(point2);
    }

    public ControllerSortie afficher() {
        return new ControllerSortie(this);
    }

    public double getDistance() {
        return distance;
    }

    public double getLongueur() {
        return longueur;
    }

    public int getMur() {
        return mur;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public ArrayList<Point> getCoins(){
        ArrayList<Point> listCoins = new ArrayList<>();
        listCoins.add(getPoint1());
        listCoins.add(getPoint2());
        return listCoins;
    }

    public List<Point> getListePointsSortie() {
        return listePointsSortie;
    }
}
