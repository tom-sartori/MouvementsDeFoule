package sample;

import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Personne extends Parent {
    private double x;
    private double y;
    private double r = 15;

    private double dx;
    private double dy;

    public Personne(double posX , double posY){
        x=posX;
        y=posY;
        Circle cercle = new Circle(x,y,r);
        cercle.setFill(Color.RED);
        this.getChildren().add(cercle);
    }

    public double[] findCoordSortie (Salle salle) {
        double [] tab = new double[3];
        double [] tabCoord = new double[2];

        double minDistance = 1000000;
        int x1oux2 = -1;
        int i = -1;
        int index = i;

        for (Sortie sortie : salle.getListSorties()) {
            tabCoord = findNumCoord(sortie);
            i++;
            if (tabCoord[1] < minDistance) {
                minDistance = tabCoord[1];
                x1oux2 = (int)tabCoord[0];
                index = i;
            }

        }
        if (x1oux2 == 1) {
            tab[0] = salle.getListSorties().get(index).getX1();
            tab[1] = salle.getListSorties().get(index).getY1();
        }
        else if (x1oux2 == 2) {
            tab[0] = salle.getListSorties().get(index).getX2();
            tab[1] = salle.getListSorties().get(index).getY2();
        }
        else
            System.out.println("Erreur findCoordSortie. ");

        tab[2] = salle.getListSorties().get(index).getMur();
        return tab;
    }

    public double[] findNumCoord (Sortie sortie) {
        double [] tab = new double[2];
        double dist1 = 0;
        double dist2 = 0;


        dist1 = Math.sqrt( Math.pow(Math.abs(x - sortie.getX1()), 2) + Math.pow(Math.abs(y - sortie.getY1()), 2));
        dist2 = Math.sqrt( Math.pow(Math.abs(x - sortie.getX2()), 2) + Math.pow(Math.abs(y - sortie.getY2()), 2));

        System.out.println("Distance 1 : " + dist1);
        System.out.println("Distance 2 : " + dist2);

        if (dist1 <= dist2) {
            tab[0] = 1;
            tab[1] = dist1;
            return tab;
        }
        else {
            tab[0] = 2;
            tab[1] = dist2;
            return tab;
        }
    }


    public double[] findDxDy (double xArrive, double yArrive, int mur) {
        double[] tab = new double[2];

        if (mur == 1) {
            double distX = Math.abs(x - xArrive);
            double distY = Math.abs(y - yArrive);
            if (x - xArrive > 0) {
                tab[0] = - (distX / distY);
            }
            else
                tab[0] = distX / distY;
            tab[1] = -1;
        }
        else if (mur == 2) {
            double distX = Math.abs(x - xArrive);
            double distY = Math.abs(y - yArrive);
            if (y - yArrive > 0) {
                tab[1] = - (distY / distX);
            }
            else
                tab[1] = distY / distX;
            tab[0] = 1;
        }
        else if (mur == 3) {
            double distX = Math.abs(x - xArrive);
            double distY = Math.abs(y - yArrive);
            if (x - xArrive > 0) {
                tab[0] = - (distX / distY);
            }
            else
                tab[0] = distX / distY;
            tab[1] = 1;
        }
        else if (mur == 4) {
            double distX = Math.abs(x - xArrive);
            double distY = Math.abs(y - yArrive);
            if (y - yArrive > 0) {
                tab[1] = - (distY / distX);
            }
            else
                tab[1] = distY / distX;
            tab[0] = -1;
        }

        System.out.println("dx : " + tab[0]);
        System.out.println("dy : " + tab[1]);

        return tab;
    }

    public double [] getDxDy (Salle salle) {
        double [] coordSortie = findCoordSortie(salle);
        double [] coordDxDy = findDxDy(coordSortie[0], coordSortie[1], (int)coordSortie[2]);

        return coordDxDy;
    }

}
