package sample;

import sample.controller.ControllerChemin;

public class Chemin {

    private Point A;
    private Point B;

    public Chemin(Point a, Point b) {
        A = a;
        B = b;
    }

    public void afficher () {
        new ControllerChemin(this);
    }

    public Point getA() {
        return A;
    }

    public Point getB() {
        return B;
    }

}