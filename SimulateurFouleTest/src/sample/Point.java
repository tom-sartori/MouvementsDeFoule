package sample;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Point {
    private double x;
    private double y;

    private double distance;
    private Point precedent;


    public Point(){
        x=0;
        y=0;

        precedent = null;
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    
        precedent = null;
    }

    public Point(Point p) {
        x = p.getX();
        y = p.getY();
        precedent = p.getPrecedent();
    }


    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Point point = (Point) o;
        return Double.compare(point.x, x) == 0 &&
                Double.compare(point.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public double getDistance() {
        return distance;
    }


    public Point getPrecedent() {
        return precedent;
    }

    public void setPrecedent(Point precedent) {
        this.precedent = precedent;
    }

}