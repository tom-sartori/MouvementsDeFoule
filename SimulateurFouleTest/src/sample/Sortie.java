package sample;

import java.util.ArrayList;
import java.util.List;

public class Sortie {
    private int mur;
    private double longueur;
    private double distance;
    private static double epaisseur = 15;

    private double x1;  // Ces points vont disparaitre pour garder uniquement point1 et point2
    private double y1;
    private double x2;
    private double y2;

    private Point point1;  // temporaire avant de modifier toute la classe avec juste point1 et point2 sans x1, y1, x2, y2.
    private Point point2;
    private List<Point> listePointsSortie;


    public Sortie(int m, double l, double d) {
        this.mur = m;
        this.longueur = l;
        this.distance = d;

        point1 = new Point();   // Pour le moment, ils sont initialisés addSortie de Salle
        point2 = new Point();

        listePointsSortie = new ArrayList<>();
        listePointsSortie.add(point1);
        listePointsSortie.add(point2);
    }

    public double getDistance() {
        return distance;
    }

    public static double getEpaisseur() {
        return epaisseur;
    }

    public double getLongueur() {
        return longueur;
    }

    public int getMur() {
        return mur;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }

    public void setPoint1 (double x, double y) {
        point1.setX(x);
        point1.setY(y);
    }

    public void setPoint2 (double x, double y) {
        point2.setX(x);
        point2.setY(y);
    }

    public List<Point> getListePointsSortie() {
        return listePointsSortie;
    }
}
