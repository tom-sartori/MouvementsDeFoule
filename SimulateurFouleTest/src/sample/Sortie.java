package sample;


public class Sortie {
    private Point point1;
    private Point point2;


    public Sortie (Point p1, Point p2) {
        this.point1 = p1;
        this.point2 = p2;
    }

    public ControllerSortie afficher() {
        return new ControllerSortie(this);
    }


    public double getLargeurPorte() {
        if (point1.getY() == point2.getY()) {   // mur==1 || mur==3)
            return Math.abs(point1.getX() - point2.getX());
        }
        else if(point1.getX() == point2.getX()) {  //mur==2 || mur==4
            return Math.abs(point1.getY() - point2.getY());
        }
        else {
            System.out.println("Sortie, getLongueur, erreur. ");
            return 0;
        }
    }

    public boolean estMur1ou3 () {
        // mur==1 || mur==3
        return point1.getY() == point2.getY();
    }

    // Pour un point de départ en paramètre, retourne un point correspondant à l'endroit de la sortie (this)
    // le plus proche et direct par rapport au départ.
    // S'il n'y a pas de point direct, alors return null.
    public Point findPointSortieDirect (Salle salle,  Point depart, double rayon) {
        rayon = 0;
        Point courant = new Point(point1);
        Point pointSortie = null;
        double plusPetiteDistance = 100000000;    // Infinie;

        for (double i = rayon; i <= getLargeurPorte() - rayon; i++ ) {
            if (estMur1ou3()) {
                courant.setX(point1.getX() + i);
                if (!salle.intersecObstacle(depart, courant)) {
                    if (MathsCalcule.distance(depart, courant) <= plusPetiteDistance) {
                        plusPetiteDistance = MathsCalcule.distance(depart, courant);
                        pointSortie = new Point(courant);
                    }
                }
            }
            else {
                courant.setY(point1.getY() + i);
                if (!salle.intersecObstacle(depart, courant)) {
                    if (MathsCalcule.distance(depart, courant) < plusPetiteDistance) {
                        plusPetiteDistance = MathsCalcule.distance(depart, courant);
                        pointSortie = new Point(courant);
                    }
                }
            }
        }
        System.out.println("depart + " + depart + " sortie + " + pointSortie);
        return pointSortie; // Retourne null si pas de point direct.
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }
}
