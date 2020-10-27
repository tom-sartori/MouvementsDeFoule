package sample;

import javafx.scene.Parent;

import java.util.List;

public abstract class Obstacle extends Parent {
    private double x;
    private double y;
    private List<Point> listCoins;
    private List<Point> listDiagonales;

    public List<Point> getCoins() {
        return listCoins;
    }
    public List<Point> getDiagonales(){return listDiagonales;}
}
