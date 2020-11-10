package sample;

import java.util.List;

public class Sortie {
    private List<Point> listePointsSortie;


    public Sortie (List<Point> listePoints) {
        listePointsSortie = listePoints;
    }

    public ControllerSortie afficher() {
        return new ControllerSortie(this);
    }

    public List<Point> getListePointsSortie() {
        return listePointsSortie;
    }

    public double getLargeurPorte() {
        Point point1 = listePointsSortie.get(0);
        Point point2 = listePointsSortie.get(1);

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
        if (listePointsSortie.get(0).getY() == listePointsSortie.get(1).getY()) {   // mur==1 || mur==3
            return true;
        } else return false;
    }

    public Point findPointSortie (Point depart) {
        Point point1 = listePointsSortie.get(0);
        Point point2 = listePointsSortie.get(1);
        System.out.println("point 1 : " + point1 + " point 2 : " + point2);

        Point courant = new Point(point1);
        Point pointSortie = new Point(point1);
        double plusPetiteDistance = 1000000;    // Infinie;

        for (int i = 0; i <= getLargeurPorte(); i++ ) {
            if (estMur1ou3()) {
                courant.setX(point1.getX() + i);
                if (MathsCalcule.distance(depart, courant) < plusPetiteDistance) {
                    plusPetiteDistance = MathsCalcule.distance(depart, courant);
                    pointSortie.setX(courant.getX());
                }
            }
            else {
                courant.setY(point1.getY() + i);
                if (MathsCalcule.distance(depart, courant) < plusPetiteDistance) {
                    plusPetiteDistance = MathsCalcule.distance(depart, courant);
                    pointSortie.setY(courant.getY());
                }
            }
        }
        System.out.println("PointSortie : " + pointSortie);
        return pointSortie;
    }


    public Point findPointSortieDirect (Salle salle,  Point depart) {
        Point point1 = listePointsSortie.get(0);
        Point point2 = listePointsSortie.get(1);

        Point courant = new Point(point1);
        Point pointSortie = null;
        double plusPetiteDistance = 1000000;    // Infinie;

        for (int i = 0; i <= getLargeurPorte(); i++ ) {
            if (estMur1ou3()) {
                courant.setX(point1.getX() + i);
                if (!salle.intersecObstacle(depart, courant)) {
                    if (MathsCalcule.distance(depart, courant) < plusPetiteDistance) {
                        plusPetiteDistance = MathsCalcule.distance(depart, courant);
                        //pointSortie.setPoint(courant.getX(), courant.getY());
                        pointSortie = new Point(courant);
                        //pointSortie.setPrecedent(depart);
                    }
                }
            }
            else {
                courant.setY(point1.getY() + i);
                if (!salle.intersecObstacle(depart, courant)) {
                    if (MathsCalcule.distance(depart, courant) < plusPetiteDistance) {
                        plusPetiteDistance = MathsCalcule.distance(depart, courant);
                        //pointSortie.setPoint(courant.getX(), courant.getY());
                        pointSortie = new Point(courant);
                    }
                }
            }
        }
        System.out.println("pointSortie : " + pointSortie);
        return pointSortie; // Si null, devrait rien retourner
    }
}
