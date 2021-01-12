package sample;

import sample.controller.ControllerObstacle;
import sample.controller.ControllerObstaclePolygone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObstaclePolygone implements Obstacle {

    private List<Point> listePointsPhysique;
    private List<Point> listePointsGraphiques;
    private List<Point> listDiagonales;

    public ObstaclePolygone(List<Point> listePoints) {
        listePointsPhysique = new ArrayList<>();
        listePointsGraphiques = new ArrayList<>();
        listDiagonales = new ArrayList<>();

        for (Point point : listePoints) {
            listePointsPhysique.add(new Point(point));
        }
        //listePointsGraphiques = listePointsPhysique;


        double rayon = new Personne().getRayon();

        Point d1 = MathsCalcule.getDroiteParallele(listePoints.get(listePoints.size() - 1), listePoints.get(0), rayon);
        Point d2 = MathsCalcule.getDroiteParallele(listePoints.get(0), listePoints.get(1), rayon);
        listePointsGraphiques.add(MathsCalcule.getPointIntersection(d1, d2));

        for (int i = 1; i < listePoints.size() - 1; i++) {
            d1 = MathsCalcule.getDroiteParallele(listePoints.get(i - 1), listePoints.get(i), rayon);
            d2 = MathsCalcule.getDroiteParallele(listePoints.get(i), listePoints.get(i + 1), rayon);
            listePointsGraphiques.add(MathsCalcule.getPointIntersection(d1, d2));
        }

        d1 = MathsCalcule.getDroiteParallele(listePoints.get(listePoints.size() - 2), listePoints.get(listePoints.size() - 1), rayon);
        d2 = MathsCalcule.getDroiteParallele(listePoints.get(listePoints.size() - 1), listePoints.get(0), rayon);
        listePointsGraphiques.add(MathsCalcule.getPointIntersection(d1, d2));





        ControllerObstaclePolygone obstacleFX = new ControllerObstaclePolygone(this);
        Point milieu = new Point();

        for (int i = 0; i < listePointsPhysique.size(); i++) {
            for (int j = 0; j < listePointsPhysique.size(); j++) {
                if (i < j) {
                    milieu.setPoint(MathsCalcule.getMilieu(listePointsPhysique.get(i), listePointsPhysique.get(j)));
                    if (obstacleFX.contains(milieu.getX(), milieu.getY())) {
                        if (i == 0 && j != listePointsPhysique.size() - 1 && j != 1) {
                            listDiagonales.add(new Point(listePointsPhysique.get(i)));
                            listDiagonales.add(new Point(listePointsPhysique.get(j)));
                        } else if (i == listePointsPhysique.size() - 1 && j != listePointsPhysique.size() - 1) {
                            listDiagonales.add(new Point(listePointsPhysique.get(i)));
                            listDiagonales.add(new Point(listePointsPhysique.get(j)));
                        } else if (i != 0 && j != i + 1) {
                            listDiagonales.add(new Point(listePointsPhysique.get(i)));
                            listDiagonales.add(new Point(listePointsPhysique.get(j)));
                        }
                    }
                }
            }
        }
        //System.out.println("Nb diag : " + listDiagonales.size() / 2);
        //System.out.println("Nb diag attendues : " + (listePoints.size() * (listePoints.size() - 3)) / 2);
    }

    @Override
    public ControllerObstacle afficher() {
        return new ControllerObstaclePolygone(this);
    }

    @Override
    public List<Point> getListePointsPhysique() {
        return listePointsPhysique;
    }

    @Override
    public List<Point> getDiagonales() {
        return listDiagonales;
    }

    @Override
    public boolean estDansObstacle(Point point) {
        ControllerObstaclePolygone obstaclePolygone = new ControllerObstaclePolygone(listePointsPhysique);
        return obstaclePolygone.contains(point.getX(), point.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObstaclePolygone that = (ObstaclePolygone) o;
        return Objects.equals(listePointsPhysique, that.listePointsPhysique);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listePointsPhysique);
    }

    @Override
    public List<Point> getListePointsGraphiques() {
        return listePointsGraphiques;
    }
}
