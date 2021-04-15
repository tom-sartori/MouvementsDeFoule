package physique;

import controller.ControllerObstacle;

import java.util.List;

public interface Obstacle {

    public ControllerObstacle afficher();
    public List<Point> getListePointsPhysique();
    public List<Point> getListePointsGraphiques();
    public List<Point> getDiagonales();
    public boolean estDansObstacle(Point point);
}
