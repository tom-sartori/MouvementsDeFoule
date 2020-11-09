package sample;

import java.util.List;

public interface Obstacle {

    public ControllerObstacle afficher();
    public List<Point> getListePoints();
    public List<Point> getDiagonales();
    public boolean estDansObstacle(Point point);
}
