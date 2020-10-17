package sample;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Personne extends Parent {
    private double xDepart;
    private double yDepart;
    private double r = 15;

    private double dx;
    private double dy;

    public Personne(double posX , double posY){
        xDepart = posX;
        yDepart = posY;
        Circle cercle = new Circle(xDepart, yDepart,r);
        cercle.setFill(Color.RED);
        this.getChildren().add(cercle);
    }

    // Permet de savoir les coordonnés du coin de sortie le plus proche du perso
    // retourne un tableau du type [xSortiePlusProche, ySortiePlusProche, murSortiePlusProche]
    public double[] findCoordSortie (Salle salle) {
        double [] tab = new double[3];
        double [] tabCoord = new double[2];

        double minDistance = 1000000;
        int x1oux2 = -1;
        int i = -1;
        int index = i;

        for (Sortie sortie : salle.getListSorties()) {  // Pour toutes les sorties de la salle
            tabCoord = findCoinSortieProche(sortie);    // On recupere le coin le plus proche avec sa distance
            i++;
            if (tabCoord[1] < minDistance) {    // Si nouveau coin plus proche alors on ne garde pas l'ancien coin
                minDistance = tabCoord[1];
                x1oux2 = (int)tabCoord[0];
                index = i;  // On garde l'index dans listSorties de la sortie la plus proche
            }

        }
        if (x1oux2 == 1) {  // Si c'est le (x1 y1) d'une des sorties qui est le plus proche, alors :
            tab[0] = salle.getListSorties().get(index).getX1();     // tab[0] prend le x1 de la sortie correspondante
            tab[1] = salle.getListSorties().get(index).getY1();     // et tab[1] le y1
        }
        else if (x1oux2 == 2) {     // Sinon, c'est le (x2, y2) de la sortie qui est plus proche, alors :
            tab[0] = salle.getListSorties().get(index).getX2();     // tab[0] prend x2
            tab[1] = salle.getListSorties().get(index).getY2();     // tab[1] prend y2
        }
        else
            System.out.println("Erreur findCoordSortie. ");

        tab[2] = salle.getListSorties().get(index).getMur();    // tab[2] prend juste le mur correspondant à la sortie
        return tab;
    }



    // Retourne le coté de sortie le plus proche, pour la sortie en argument.
    // indice 0 retourné est le coté 1 ou 2 (haut bas ou gauche droite de la sortie)
    // indice 1 retourné est la distance entre le perso le coin de sortie le plus proche
    // Retourne donc un tableau du type [1, distance] ou [2, distance]
    public double[] findCoinSortieProche(Sortie sortie) {
        double [] tab = new double[2];
        double dist1 = 0;
        double dist2 = 0;

        // calcul avec Pythagore
        dist1 = Math.sqrt( Math.pow(Math.abs(xDepart - sortie.getX1()), 2) + Math.pow(Math.abs(yDepart - sortie.getY1()), 2));
        dist2 = Math.sqrt( Math.pow(Math.abs(xDepart - sortie.getX2()), 2) + Math.pow(Math.abs(yDepart - sortie.getY2()), 2));

        //System.out.println("Distance 1 : " + dist1);
        //System.out.println("Distance 2 : " + dist2);

        if (dist1 <= dist2) {
            tab[0] = 1;     // Donc coté 1 de la sortie (correspond à x1 y1 de Sortie)
            tab[1] = dist1;
            return tab;
        }
        else {
            tab[0] = 2;     // Ou coté 2 de la sortie (correspond à x2 y2 de Sortie)
            tab[1] = dist2;
            return tab;
        }
    }


    // Prend des coordonnés (de sortie) en paramètre avec le mur correspondant
    // Retourne dx dy tels que par rapport à la position du perso, il arrivera à l'arrivée en paramètre en une succession de x + dx et y + dy
    // Retourne donc un tableau du type [dx, dy]
    // Cependant, dx et dy ne sont pas normalisés. Un perso peut donc aller plus vite qu'un autre.
    // Une nouvelle version de cette fonction sera surement necessaire sans l'argument "mur" afin de convenir aux coordonnés des obstacles.
    public double[] findDxDy (double xArrive, double yArrive, int mur) {
        double[] tab = new double[2];

        if (mur == 1) {
            double distX = Math.abs(xDepart - xArrive);
            double distY = Math.abs(yDepart - yArrive);
            if (xDepart - xArrive > 0) {
                tab[0] = - (distX / distY);
            }
            else
                tab[0] = distX / distY;
            tab[1] = -1;
        }
        else if (mur == 2) {
            double distX = Math.abs(xDepart - xArrive);
            double distY = Math.abs(yDepart - yArrive);
            if (yDepart - yArrive > 0) {
                tab[1] = - (distY / distX);
            }
            else
                tab[1] = distY / distX;
            tab[0] = 1;
        }
        else if (mur == 3) {
            double distX = Math.abs(xDepart - xArrive);
            double distY = Math.abs(yDepart - yArrive);
            if (xDepart - xArrive > 0) {
                tab[0] = - (distX / distY);
            }
            else
                tab[0] = distX / distY;
            tab[1] = 1;
        }
        else if (mur == 4) {
            double distX = Math.abs(xDepart - xArrive);
            double distY = Math.abs(yDepart - yArrive);
            if (yDepart - yArrive > 0) {
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


    // Cette fonction utilise les fonctions précédentes afin de retourner directement dx et dy suivant la Salle en argument
    // Retourne donc un tavleau du type [dx, dy]
    public double [] getDxDy (Salle salle) {
        double [] coordSortie = findCoordSortie(salle);
        double [] coordDxDy = findDxDy(coordSortie[0], coordSortie[1], (int)coordSortie[2]);

        this.dx = coordDxDy[0];
        this.dy = coordDxDy[1];
        return coordDxDy;
    }



    public void avancer () {
        setTranslateX(getTranslateX() + dx);
        setTranslateY(getTranslateY() + dy);
    }


    public double getxDepart() {
        return xDepart;
    }

    public double getyDepart() {
        return yDepart;
    }


    // Permet de savoir si le perso est sorti de la salle
    public boolean estSorti(Salle salle) { // Manque le cas avec y
        if (dx > 0) {
            if (xDepart + getTranslateX() > salle.getLargeur() - 2 * salle.getMarge())
                return true;
        }
        if (dx < 0) {
            if (xDepart + getTranslateX() < 0 + salle.getMarge())
                return true;
        }
        // cas avec y
        return false;
    }
}
