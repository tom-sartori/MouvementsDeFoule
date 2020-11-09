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
}
