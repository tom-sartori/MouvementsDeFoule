package sample;

import javafx.scene.Parent;

public abstract class Obstacle extends Parent {
    private double x;
    private double y;
    private double[][]coins;

    public double[][] getCoins() {
        return coins;
    }
}
