package sample;

import sample.controller.ControllerObstacle;
import sample.controller.ControllerObstacleTriangle;

import java.util.ArrayList;
import java.util.List;

public class ObstacleTriangle implements Obstacle {

    private List<Point> listePoints;
    private List<Point> listDiagonales;

    public ObstacleTriangle (Point A, Point B, Point C) {
        listePoints = new ArrayList<>();
        listePoints.add(A);
        listePoints.add(B);
        listePoints.add(C);

        listDiagonales = new ArrayList<>();
    }


    @Override
    public ControllerObstacle afficher() {
        return new ControllerObstacleTriangle(this);
    }

    @Override
    public List<Point> getListePointsPhysique() {
        return listePoints;
    }

    @Override
    public List<Point> getDiagonales() {
        return listDiagonales;
    }

    @Override
    public boolean estDansObstacle(Point point) {
        return false;
    }
}
