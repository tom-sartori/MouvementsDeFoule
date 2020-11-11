package sample;

import java.util.Objects;

public class Point {
    private double x;
    private double y;

    private boolean estSortie;

    private Point suivant;
    private double distance;

    private Point vraiSuivant;
    private double vraieDistance;


    private Point suivanttttttttttttt;
    private double distanceSortieeeeeeeeeeeeeeeee;


    public Point(){
        x=0;
        y=0;
        estSortie = false;

        suivant = null;
        vraiSuivant = null;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
        estSortie = false;

        suivant = null;
        vraiSuivant = null;
    }

    public Point(Point p) {
        x = p.getX();
        y = p.getY();
        estSortie = p.estSortie();
        suivant = p.getSuivant();
        distance = p.getDistance();
        suivanttttttttttttt = p.getSuivanttttttttttttt();
        distanceSortieeeeeeeeeeeeeeeee = p.getDistanceSortieeeeeeeeeeeeeeeee();
        vraiSuivant = p.getVraiSuivant();
        vraieDistance = p.getVraieDistance();
    }


    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    public String toStringV2() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", distanceSortie=" + distanceSortieeeeeeeeeeeeeeeee +
                ", suivant=" + suivanttttttttttttt +
                '}';
    }

    public String toStringV3() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                ", distance=" + distance +
                ", precedent=" + suivant +
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
        if ( (this.getY() - precision <= d) && (d <= this.getY() + precision) ) {
            return true;
        }
        return false;
    }

    public boolean environEgaleX (double d) {
        double precision = 1;
        if ( (this.getX() - precision <= d) && (d <= this.getX() + precision) ) {
            return true;
        }
        return false;
    }

    // Utilisé pour la detection, lorsqu'un perso est arrivé à son objectif (coin d'un obstacle).
    // Obligé de faire un environ égale car comme les coordonnés sont des doubles, c'était jamais égale.
    public boolean environEgale (Point p, double precision) {
        if ( (this.getX() - precision <= p.getX()) && (p.getX() <= this.getX() + precision) ) {
            if ( (this.getY() - precision <= p.getY()) && (p.getY() <= this.getY() + precision) ) {
                //System.out.println("Points environ egaux. ");
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

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }


    public Point getSuivant() {
        return suivant;
    }

    public void setSuivant(Point suivant) {
        this.suivant = suivant;
    }

    public Point getSuivanttttttttttttt() {
        return suivanttttttttttttt;
    }

    public void setSuivanttttttttttttt(Point suivanttttttttttttt) {
        this.suivanttttttttttttt = suivanttttttttttttt;
    }

    public double getDistanceSortieeeeeeeeeeeeeeeee() {
        return distanceSortieeeeeeeeeeeeeeeee;
    }

    public void setDistanceSortieeeeeeeeeeeeeeeee(double distanceSortieeeeeeeeeeeeeeeee) {
        this.distanceSortieeeeeeeeeeeeeeeee = distanceSortieeeeeeeeeeeeeeeee;
    }

    public void setPoint (double x, double y) {
        this.x = x;
        this.y = y;
    }

    public boolean estSortie() {
        return estSortie;
    }

    public void setEstSortie(boolean estSortie) {
        this.estSortie = estSortie;
    }

    public Point getVraiSuivant() {
        return vraiSuivant;
    }

    public double getVraieDistance() {
        return vraieDistance;
    }

    public void setVraiSuivant(Point vraiSuivant) {
        this.vraiSuivant = vraiSuivant;
    }

    public void setVraieDistance(double vraieDistance) {
        this.vraieDistance = vraieDistance;
    }
}
