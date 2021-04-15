package physique;


import controller.ControllerSortie;

public class Sortie {
    private Point extremum1;
    private Point extremum2;


    public Sortie (Point p1, Point p2) {
        this.extremum1 = p1;
        this.extremum2 = p2;
    }

    public ControllerSortie afficher() {
        return new ControllerSortie(this);
    }


    public double getLargeurPorte() {
        if (extremum1.getY() == extremum2.getY()) {   // mur==1 || mur==3)
            return Math.abs(extremum1.getX() - extremum2.getX());
        }
        else if(extremum1.getX() == extremum2.getX()) {  //mur==2 || mur==4
            return Math.abs(extremum1.getY() - extremum2.getY());
        }
        else {
            System.out.println("Sortie, getLongueur, erreur. ");
            return 0;
        }
    }

    public boolean estMur1ou3 () {
        // mur==1 || mur==3
        return extremum1.getY() == extremum2.getY();
    }

    // Pour un point de départ en paramètre, retourne un point correspondant à l'endroit de la sortie (this)
    // le plus proche et direct par rapport au départ.
    // S'il n'y a pas de point direct, alors return null.
    public Point findPointSortieDirect (Salle salle, Point depart, double rayon) {
        Point courant = new Point(extremum1);  // Extrémité du segment de la sortie avec le plus petit x ou y suivant orientation.
        Point pointSortie = null;
        double plusPetiteDistance = Double.POSITIVE_INFINITY;

        for (double i = rayon; i <= getLargeurPorte() - rayon; i++ ) {  // Pour tous les points du segment de la sortie.
            if (estMur1ou3()) { // Si la sortie est sur le mur du haut ou du bas.
                courant.setX(extremum1.getX() + i); // courant prend tous les points du segment de la sortie.
                // Si le segment [depart ; courant] n'intersecte aucun obstacle de la salle.
                if (!salle.intersecObstacle(depart, courant)) {
                    if (MathsCalcule.distance(depart, courant) <= plusPetiteDistance) {
                        // On cherche la distance minimale de depart à courant.
                        plusPetiteDistance = MathsCalcule.distance(depart, courant);
                        pointSortie = new Point(courant);
                    }
                }
            }
            else {  // Est donc sur le mur de droit ou de gauche.
                courant.setY(extremum1.getY() + i);
                // Si le segment [depart ; courant] n'intersecte aucun obstacle de la salle.
                if (!salle.intersecObstacle(depart, courant)) {
                    if (MathsCalcule.distance(depart, courant) < plusPetiteDistance) {
                        plusPetiteDistance = MathsCalcule.distance(depart, courant);
                        pointSortie = new Point(courant);
                    }
                }
            }
        }
        return pointSortie; // Retourne null si pas de point direct.
    }

    public Point getExtremum1() {
        return extremum1;
    }

    public Point getExtremum2() {
        return extremum2;
    }
}
