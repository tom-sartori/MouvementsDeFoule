package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class Sortie extends Parent {
    private int mur;
    private double longueur;
    private double distance;
    private static double epaisseur = 15;

    private double x1;
    private double y1;
    private double x2;
    private double y2;

    private Point point1;  // temporaire avant de modifier toute la classe avec juste point1 et point2 sans x1, y1, x2, y2.
    private Point point2;


    public Sortie(int m, double l, double d) {
        this.mur = m;
        this.longueur = l;
        this.distance = d;
        Rectangle sortie = new Rectangle();

        if(mur==1){
            sortie.setWidth(longueur);
            sortie.setHeight(epaisseur);
        }
        else if(mur==2){
            sortie.setHeight(longueur);
            sortie.setWidth(epaisseur);
        }
        else if(mur==3){
            sortie.setWidth(longueur);
            sortie.setHeight(epaisseur);
        }
        else if(mur==4) {
            sortie.setWidth(epaisseur);
            sortie.setHeight(longueur);
        }
        else {
            System.out.println("Erreur dans constructeur Sortie");
            return;
        }
        sortie.setFill(Color.NAVY);
        this.getChildren().add(sortie);

    }

    public double getDistance() {
        return distance;
    }

    public static double getEpaisseur() {
        return epaisseur;
    }

    public double getLongueur() {
        return longueur;
    }

    public int getMur() {
        return mur;
    }

    public double getX1() {
        return x1;
    }

    public void setX1(double x1) {
        this.x1 = x1;
    }

    public double getY1() {
        return y1;
    }

    public void setY1(double y1) {
        this.y1 = y1;
    }

    public double getX2() {
        return x2;
    }

    public void setX2(double x2) {
        this.x2 = x2;
    }

    public double getY2() {
        return y2;
    }

    public void setY2(double y2) {
        this.y2 = y2;
    }

    public Point getPoint1() {
        return point1;
    }

    public Point getPoint2() {
        return point2;
    }

    public void setPoint1(Point point1) {
        this.point1 = point1;
    }

    public void setPoint2(Point point2) {
        this.point2 = point2;
    }
}
