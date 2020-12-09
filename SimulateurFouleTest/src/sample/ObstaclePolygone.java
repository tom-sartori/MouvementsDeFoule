package sample;

import sample.controller.ControllerObstacle;
import sample.controller.ControllerObstaclePolygone;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class ObstaclePolygone implements Obstacle {

    private List<Point> listePoints;
    private List<Point> listDiagonales;

    public ObstaclePolygone(List<Point> points) {
        listePoints = new ArrayList<>();
        listDiagonales = new ArrayList<>();

        for (Point point : points) {
            listePoints.add(new Point(point));
        }

        ControllerObstaclePolygone obstacleFX = new ControllerObstaclePolygone(this);
        Point milieu = new Point();

        for (int i = 0; i < listePoints.size(); i++) {
            for (int j = 0; j < listePoints.size(); j++) {
                if (i < j) {
                    milieu.setPoint(MathsCalcule.getMilieu(listePoints.get(i), listePoints.get(j)));
                    if (obstacleFX.contains(milieu.getX(), milieu.getY())) {
                        if (i == 0 && j != listePoints.size() - 1 && j != 1) {
                            listDiagonales.add(new Point(listePoints.get(i)));
                            listDiagonales.add(new Point(listePoints.get(j)));
                        } else if (i == listePoints.size() - 1 && j != listePoints.size() - 1) {
                            listDiagonales.add(new Point(listePoints.get(i)));
                            listDiagonales.add(new Point(listePoints.get(j)));
                        } else if (i != 0 && j != i + 1) {
                            listDiagonales.add(new Point(listePoints.get(i)));
                            listDiagonales.add(new Point(listePoints.get(j)));
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
    public List<Point> getListePoints() {
        return listePoints;
    }

    @Override
    public List<Point> getDiagonales() {
        return listDiagonales;
    }

    @Override
    public boolean estDansObstacle(Point point) {
        ControllerObstaclePolygone obstaclePolygone = new ControllerObstaclePolygone(this);
        return obstaclePolygone.contains(point.getX(), point.getY());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ObstaclePolygone that = (ObstaclePolygone) o;
        return Objects.equals(listePoints, that.listePoints);
    }

    @Override
    public int hashCode() {
        return Objects.hash(listePoints);
    }

    
}
