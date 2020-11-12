package sample;

public class Chemin {

    private Point A;
    private Point B;
    private double distance;

    public Chemin(Point a, Point b) {
        A = a;
        B = b;
        distance = MathsCalcule.distance(A, B);
    }

    public void afficher () {
        ControllerChemin controllerChemin = new ControllerChemin(this);
    }

    public Point getA() {
        return A;
    }

    public Point getB() {
        return B;
    }

}
