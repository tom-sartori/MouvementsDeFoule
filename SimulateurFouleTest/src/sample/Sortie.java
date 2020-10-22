package sample;

public class Sortie {
    private int mur;
    private double longueur;
    private double distance;
    private double epaisseur = 15;
    private double x1;
    private double y1;
    private double x2;
    private double y2;

    private ControllerSortie controllerSortie;

    public double getEpaisseur(){
        return epaisseur;
    }

    public ControllerSortie getControllerSortie(){
        return controllerSortie;
    }

    public Sortie(int m, double l, double d) {
        this.mur = m;
        this.longueur = l;
        this.distance = d;
        controllerSortie = new ControllerSortie(m, l, d, epaisseur, this);
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
}
