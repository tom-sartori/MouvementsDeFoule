package sample;

import java.util.ArrayList;
import java.util.List;

public class Graphe {

    private Salle salle;
    private List<Chemin> listeChemins;
    private List<Point> listePoints;
    private List<Point> listePointsObstacles;


    public Graphe (Salle salle) {
        listeChemins = new ArrayList<>();
        this.salle = salle;

        listePoints = new ArrayList<>();    // Pas forcement Array
        listePointsObstacles = new ArrayList<>();
        for (Obstacle obstacle : salle.getListObstacles()) {
            for (Point point : obstacle.getCoins()) {
                listePoints.add(point);
                listePointsObstacles.add(point);
            }
        }
        for (Sortie sortie : salle.getListSorties()) {
            for (Point point : sortie.getListePointsSortie())
                listePoints.add(point);
        }

    }

    public void addChemin (Chemin chemin) {
        listeChemins.add(chemin);
    }

    public void creerTousLesChemins() {
        for (Obstacle obstacle : salle.getListObstacles()) {
            for (Point point : obstacle.getCoins()) {
                for (Obstacle obstacle1 : salle.getListObstacles()) {
                    for (Point point1 : obstacle1.getCoins()) {
                        if (!salle.intersecObstacle(point, point1))
                            addChemin(new Chemin(point, point1));
                    }
                }
                Point pointSortie = salle.findSortiePlusProcheDirecte(point);
                if (pointSortie != null) {
                    addChemin(new Chemin(point, pointSortie));
                }
            }
        }
    }

    public void creerPlusCourtChemin(Point depart) {
        List<Point> listeCourante = new ArrayList<>();

        for (Point point : listePoints) {
            point.setDistance(100000);  // distance infinie
            listeCourante.add(point);
        }
        depart.setDistance(0);

        while (!listeCourante.isEmpty()) {
            double distance = 1000000;

            Point u = new Point();

            for (Point point : listeCourante) {
                if (point.getDistance() < distance) {
                    distance = point.getDistance();
                    u = point;
                }
            }

            listeCourante.remove(u);
            List<Point> N = getListePointsDirectes(u);

            for (Point n : N) {
                double nouveau = u.getDistance() + MathsCalcule.distance(u, n);
                if (nouveau < n.getDistance()) {
                    //System.out.println(u);
                    //addChemin(new Chemin(n, u));

                    n.setDistance(nouveau);
                    n.setPrecedent(new Point(u));
                }
            }
        }
    }


    public void creerCheminPlusCourtAvecSortie () {

        for (Point pointObstacle : listePointsObstacles) {
            creerPlusCourtChemin(pointObstacle);

            affecteSuivants(getListePointsCheminPlusCourt(pointObstacle, salle.findSortiePlusProcheIndirecte(pointObstacle)));
            addChemin(getListePointsCheminPlusCourt(pointObstacle, salle.findSortiePlusProcheIndirecte(pointObstacle)));
            //affecteLesSuivants(pointObstacle, salle.findSortiePlusProcheIndirecte(pointObstacle));
        }
    }



    // Apres que tous les plus courts chemins ont été calculés pour un point,
    // cette fonction permet de retourner une liste, composée des points du plus court chemin, du départ à l'arrivé en paramètre.
    // On utilise le point précédent, de chaque point, de l'arrivé au départ.
    // La liste retournée est dans l'ordre : [depart, ..., arrive]
    public List<Point> getListePointsCheminPlusCourt(Point depart, Point arrive) {
        List<Point> cheminPlusCourt = new ArrayList<>();

        Point courant = arrive;

        while (!courant.equals(depart)) {
            cheminPlusCourt.add(0, courant);
            courant = courant.getPrecedent();
        }

        cheminPlusCourt.add(0, depart);

        return cheminPlusCourt;
    }

    public void affecteSuivants (List<Point> listePointsChemin) {
        listePointsChemin.get(listePointsChemin.size() -1).setDistanceSortie(0);

        for (int i = listePointsChemin.size() -2; i >= 0; i--) {
            listePointsChemin.get(i).setSuivant(listePointsChemin.get(i +1));
            listePointsChemin.get(i).setDistanceSortie(listePointsChemin.get(i + 1).getDistanceSortie() + MathsCalcule.distance(listePointsChemin.get(i), listePointsChemin.get(i +1)));
        }
    }



    public ControllerGraphe afficher () {
        return new ControllerGraphe(this);
    }

    public ControllerGraphe addChemin (List<Point> parcourPoints) {
        for (int i = 0; i < parcourPoints.size() -1; i++) {
            addChemin(new Chemin(parcourPoints.get(i), parcourPoints.get(i +1)));
        }
        return new ControllerGraphe(this);
    }

    public List<Chemin> getListeChemins() {
        return listeChemins;
    }

    public List<Point> getListePoints() {
        return listePoints;
    }

    public List<Point> getListePointsDirectes (Point A) {
        List<Point> listePointsDirectes = new ArrayList<>();

        for (Point point : listePoints) {
            if (!salle.intersecObstacle(A, point))
                listePointsDirectes.add(point);
        }
        return listePointsDirectes;
    }

    public List<Point> getListePointsDirectes2 (Point A) {
        List<Point> listePointsDirectes = new ArrayList<>();

        listePoints.add(A);
        for (Point point : listePoints) {
            if (!salle.intersecObstacle(A, point))
                listePointsDirectes.add(point);
        }
        listePoints.remove(A);
        return listePointsDirectes;
    }

}