package sample;

import javafx.scene.Parent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.List;

public class Personne extends Parent {
    private double xDepart;
    private double yDepart;
    private final double r = 2;

    private Point coordCourant;
    private Point objectif;

    private double dx;
    private double dy;
    private double vitesse = 1;

    public Personne(double posX , double posY){
        xDepart = posX;
        yDepart = posY;
        Circle cercle = new Circle(xDepart, yDepart,r);
        cercle.setFill(Color.RED);
        this.getChildren().add(cercle);
        coordCourant = new Point(xDepart, yDepart);
        objectif = null;
    }

    // Permet de savoir les coordonnés du coin de sortie le plus proche du perso
    // retourne un tableau du type [xSortiePlusProche, ySortiePlusProche, murSortiePlusProche]
    public double[] findCoordSortie (Salle salle) {
        double [] tab = new double[3];
        double [] tabCoord = new double[2];
        Point coordPoint =new Point();

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
            coordPoint.setX(salle.getListSorties().get(index).getX1());     // tab[0] prend le x1 de la sortie correspondante
            coordPoint.setY(salle.getListSorties().get(index).getY1());     // et tab[1] le y1
        }
        else if (x1oux2 == 2) {     // Sinon, c'est le (x2, y2) de la sortie qui est plus proche, alors :
            coordPoint.setX(salle.getListSorties().get(index).getX2());     // tab[0] prend x2
            coordPoint.setY(salle.getListSorties().get(index).getY2());     // tab[1] prend y2
        }
        else
            System.out.println("Erreur findCoordSortie. ");
        tab[0]=coordPoint.getX();
        tab[1]=coordPoint.getY();
        tab[2]=salle.getListSorties().get(index).getMur();    // tab[2] prend juste le mur correspondant à la sortie
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
    public Point findDxDy (double xArrive, double yArrive, int mur) {
        Point tab = new Point();

        if (mur == 1) {
            double distX = Math.abs(xDepart - xArrive);
            double distY = Math.abs(yDepart - yArrive);
            if (xDepart - xArrive > 0) {
                tab.setX(- (distX / distY));
            }
            else
                tab.setX(distX / distY);
            tab.setY(-1);
        }
        else if (mur == 2) {
            double distX = Math.abs(xDepart - xArrive);
            double distY = Math.abs(yDepart - yArrive);
            if (yDepart - yArrive > 0) {
                tab.setY(- (distY / distX));
            }
            else
                tab.setY(distY / distX);
            tab.setX(1);
        }
        else if (mur == 3) {
            double distX = Math.abs(xDepart - xArrive);
            double distY = Math.abs(yDepart - yArrive);
            if (xDepart - xArrive > 0) {
                tab.setX(- (distX / distY));
            }
            else
                tab.setX(distX / distY);
            tab.setY(1);
        }
        else if (mur == 4) {
            double distX = Math.abs(xDepart - xArrive);
            double distY = Math.abs(yDepart - yArrive);
            if (yDepart - yArrive > 0) {
                tab.setY(-(distY / distX));
            }
            else
                tab.setY(distY / distX);
            tab.setX(-1);
        }

        System.out.println("dx : " + tab.getX());
        System.out.println("dy : " + tab.getY());

        return tab;
    }


    public Point findDxDy (Point point) {
        System.out.println("findDxDy : courant : " + coordCourant + " objectif : " + point);
        Point dxdy = new Point();

        double distX = Math.abs(coordCourant.getX() - point.getX());
        double distY = Math.abs(coordCourant.getY() - point.getY());

        if (coordCourant.getX() < point.getX())
            dxdy.setX(distX / distY);
        else
            dxdy.setX(- distX / distY);

        if (coordCourant.getY() < point.getY())
            dxdy.setY(1);
        else
            dxdy.setY(-1);

        System.out.println("dxdy : " + dxdy);

        return dxdy;
    }

    public void setDxDyNormalise (Point point) {
        Point coordDxDy = findDxDy(point);

        //argument = sqrt(x^2 + y^2)
        double argument = Math.sqrt( (coordDxDy.getX() * coordDxDy.getX()) + (coordDxDy.getY() * coordDxDy.getY()) );

        this.dx = (vitesse/argument) * coordDxDy.getX();
        this.dy = (vitesse/argument) * coordDxDy.getY();
    }


    // Cette fonction utilise les fonctions précédentes afin de retourner directement dx et dy suivant la Salle en argument
    public void setDxDy(Salle salle) {
        double [] coordSortie = findCoordSortie(salle);
        Point coordDxDy = findDxDy(coordSortie[0], coordSortie[1], (int)coordSortie[2]);

        this.dx = coordDxDy.getX();
        this.dy = coordDxDy.getY();
    }

    // Cette fonction utilise les fonctions précédentes afin de retourner directement dx et dy suivant la Salle en argument
    // En plus, dx et dy sont normalisés suivant la vitesse de la personne (vitesse est un attribut de Personne).
    public void setDxDyNormalise (Salle salle) {
        double [] coordSortie = findCoordSortie(salle);
        //Point coordDxDy = findDxDy(coordSortie[0], coordSortie[1], (int)coordSortie[2]);
        Point coordDxDy = findDxDy(new Point(coordSortie[0], coordSortie[1]));

        //argument = sqrt(x^2 + y^2)
        double argument = Math.sqrt( (coordDxDy.getX() * coordDxDy.getX()) + (coordDxDy.getY() * coordDxDy.getY()) );

        this.dx = (vitesse/argument) * coordDxDy.getX();
        this.dy = (vitesse/argument) * coordDxDy.getY();
    }


    public void avancer () {
        setTranslateX(getTranslateX() + dx);
        setTranslateY(getTranslateY() + dy);
        coordCourant.setX(xDepart + getTranslateX());
        coordCourant.setY(yDepart + getTranslateY());
    }


    // Obligé de faire environ égale avec une petite precision car les doubles ne sont pas égaux.
    public boolean objectifAteint () {
        if (coordCourant.environEgale(objectif)) {
            System.out.println("objectif ateint. ");
            return true;
        }
        else
            return false;
    }


    public void setObjectif (Salle salle) {
        if (objectif == null) {
            objectif = findBonChemin(salle);
        }
        else { // peut etre faire un cas pour la fin, car getSuiv() de l'arrive == null
            objectif = objectif.getSuivant();
        }
        //System.out.println("courant : " + coordCourant);
        //System.out.println("objectif : " + objectif);
    }

    public Point findBonChemin (Salle salle) {
        Point premierObjectif;
        double distance, distanceCourante;

        List<Point> listePointsDirectes = salle.getGraphe().getListePointsDirectesPerso(salle, coordCourant);
        premierObjectif = listePointsDirectes.get(0);
        distance = 1000000;

        for (Point point : salle.getGraphe().getListePointsDirectesPerso(salle, coordCourant)) { // Fonctionne pas car personne pas dans le graphe
            if (MathsCalcule.distance(coordCourant, point) != 0) { // Car si 0,c'est lui meme.
                distanceCourante = MathsCalcule.distance(coordCourant, point) + point.getDistanceSortie();
                if (distanceCourante < distance) {
                    distance = distanceCourante;
                    premierObjectif = point;
                }
            }
        }
        //System.out.println("Premier objectif : " + premierObjectif);
        return premierObjectif;
    }


    public double getxDepart() {
        return xDepart;
    }

    public double getyDepart() {
        return yDepart;
    }


    // Permet de savoir si le perso est sorti de la salle avec plus ou moins de précision
    // La précision est importante car sinon on detecte en premier qu'il est arrivé a son dernier objectif et donc,
    // son prochain objectif est null.
    // Du coup, on detecte un peu acant qu'il soit sorti, qu'il est sorti.
    public boolean estSorti2(Salle salle) {
        double precision = 3;

        if (dx > 0) {
            if (coordCourant.getX() + precision >= salle.getLargeur() + salle.getMarge())
                return true;
        }
        if (dx < 0) {
            if (coordCourant.getX() - precision <= 0 + salle.getMarge())
                return true;
        }
        if (dy > 0) {
            if (coordCourant.getY() + precision >= salle.getHauteur() + salle.getMarge())
                return true;
        }
        if (dy < 0) {
            if (coordCourant.getY() - precision <= salle.getMarge())
                return true;
        }
        return false;
    }

    //returne True si un segment fait obstacle au déplacement d'une personne
    public boolean estTouche(Point coordSortie,Point coordC,Point coordD){
        Point coordP = new Point(xDepart,yDepart);
        return MathsCalcule.estCouper(coordP,coordSortie,coordC,coordD);
    }

    //retourne True si un segment est superposé sur le chemin d'une personne
    public boolean estSuperpose(Point coordSortie,Point coordC,Point coordD){
        Point coordP = new Point(xDepart,yDepart);
        return MathsCalcule.estSuperpose(coordP,coordSortie,coordC,coordD);
    }

    //retourne une liste de point pour connaître les segments qui font obstacle au déplacement d'une personne
    public List<Point> segmentObstacle(Point coordSortie,Obstacle o){
        Point coordP = new Point(xDepart,yDepart);
        return MathsCalcule.coordSegments(coordP,coordSortie,o);
    }

    public Point getObjectif() {
        return objectif;
    }

    public void setDx(double dx) {
        this.dx = dx;
    }

    public void setDy(double dy) {
        this.dy = dy;
    }
}

