package sample;

import javafx.scene.Parent;

import java.util.List;

public abstract class Obstacle extends Parent {
    private double x;
    private double y;
    private List<Point> listCoins;

    public List<Point> getCoins() {
        return listCoins;
    }
}
