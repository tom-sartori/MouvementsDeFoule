package physique;

import java.util.Objects;

public class Point {
    private double x;
    private double y;

    private boolean estSortie;

    private Point suivant;  // Représente le point suivant pour aller vers la sortie le plus rapidement.
    private double distanceASortie; // Représente la distance jusqu'à la sortie la plus proche en passant par des obstacles (ou pas).

    private Point suivantCourant;   // Modifiés à chaque tour de boucle suivant les sorties.
    private double distanceCourante;// Utiliser les variables "suivant" et "distance" pour avoir les vrais suivants.


    public Point(){
        x=0;
        y=0;
        estSortie = false;

        suivantCourant = null;
        suivant = null;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        estSortie = false;

        suivantCourant = null;
        suivant = null;
    }

    public Point(Point p) {
        x = p.getX();
        y = p.getY();
        estSortie = p.estSortie();
        suivantCourant = p.getSuivantCourant();
        distanceCourante = p.getDistanceCourante();
        suivant = p.getSuivant();
        distanceASortie = p.getDistanceASortie();
    }


    @Override
    public String toString() {
        if (estSortie)
            return "PointSortie(" + x + " ; " + y + ')';
        return "Point(" + x + " ; " + y + ')';
    }

    public String toStringV2() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", distance=" + distanceCourante +
                ", suivant=" + suivantCourant +
                ", vrais suiv=" + suivant +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }


    public boolean environEgaleY (double d) {
        double precision = 1;
        return (this.getY() - precision <= d) && (d <= this.getY() + precision);
    }

    public boolean environEgaleX (double d) {
        double precision = 1;
        return (this.getX() - precision <= d) && (d <= this.getX() + precision);
    }

    // Utilisé pour la detection, lorsqu'un perso est arrivé à son objectif (coin d'un obstacle).
    // Obligé de faire un environ égale car comme les coordonnés sont des doubles, c'était jamais égale.
    public boolean environEgale (Point p, double precision) {
        if ( (this.getX() - precision <= p.getX()) && (p.getX() <= this.getX() + precision) ) {
            if ( (this.getY() - precision <= p.getY()) && (p.getY() <= this.getY() + precision) ) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDistanceCourante(double distanceCourante) {
        this.distanceCourante = distanceCourante;
    }

    public double getDistanceCourante() {
        return distanceCourante;
    }


    public Point getSuivantCourant() {
        return suivantCourant;
    }

    public void setSuivantCourant(Point suivantCourant) {
        this.suivantCourant = suivantCourant;
    }

    public void setPoint (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public void setPoint(Point p) {
        this.x = p.getX();
        this.y = p.getY();
    }

    public boolean estSortie() {
        return estSortie;
    }

    public void setEstSortie(boolean estSortie) {
        this.estSortie = estSortie;
    }

    public Point getSuivant() {
        return suivant;
    }

    public double getDistanceASortie() {
        return distanceASortie;
    }

    public void setSuivant(Point suivant) {
        this.suivant = suivant;
    }

    public void setDistanceASortie(double distanceASortie) {
        this.distanceASortie = distanceASortie;
    }
}
