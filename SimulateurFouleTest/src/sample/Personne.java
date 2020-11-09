package sample;

import java.util.List;

public class Personne {

    private Point coordCourant;
    private Point objectif;
    private Point objectifRayon;

    private double rayon;
    private double dx;
    private double dy;
    private double vitesse = 20;

    public Personne(double posX , double posY){
        coordCourant = new Point(posX, posY);
        objectif = null;
        rayon = 5;
        objectifRayon = new Point();
    }

    public ControllerPersonne afficher() {
        return new ControllerPersonne(this);
    }

    public Point findDxDy (Point arrivee) {
        System.out.println("findDxDy : courant : " + coordCourant + " objectif : " + arrivee);
        Point dxdy = new Point();

        double distX = Math.abs(coordCourant.getX() - arrivee.getX());
        double distY = Math.abs(coordCourant.getY() - arrivee.getY());

        if (distY == 0) {
            if (coordCourant.getX() < arrivee.getX())
                dxdy.setX(1);
            else
                dxdy.setX(-1);
        }
        else if (coordCourant.getX() < arrivee.getX())
            dxdy.setX(distX / distY);
        else
            dxdy.setX(- distX / distY);

        if (distY == 0)
            dxdy.setY(0);
        else if (coordCourant.getY() < arrivee.getY())
            dxdy.setY(1);
        else
            dxdy.setY(-1);

        System.out.println("dxdy : " + dxdy);

        return dxdy;
    }

    public void setDxDyNormalise (Point arrivee) {
        Point coordDxDy = findDxDy(arrivee);

        //argument = sqrt(x^2 + y^2)
        double argument = Math.sqrt( (coordDxDy.getX() * coordDxDy.getX()) + (coordDxDy.getY() * coordDxDy.getY()) );

        this.dx = (vitesse/argument) * coordDxDy.getX();
        this.dy = (vitesse/argument) * coordDxDy.getY();
        System.out.println("dx : " + dx);
        System.out.println("dy : " + dy);
    }

    // Permet de faire avancer Personne suivant ses dx, dy (donc normalement en direction de son Point objectif).
    // Si le perso dépasse en x ou y son objectif, cela signifie qu'il ateint son objectif et donc coordCourant prend les coord de l'objectif.
    // Sinon, il avance simplement de dx et dy
    public void avancer () {
        if (dx >= 0 && dy >= 0 && coordCourant.getX() + dx >= objectif.getX() && coordCourant.getY() + dy >= objectif.getY())
            coordCourant.setPoint(objectif.getX(), objectif.getY());
        else if (dx >= 0 && dy <= 0 && coordCourant.getX() + dx >= objectif.getX() && coordCourant.getY() + dy <= objectif.getY())
            coordCourant.setPoint(objectif.getX(), objectif.getY());
        else if (dx <= 0 && dy <= 0 && coordCourant.getX() + dx <= objectif.getX() && coordCourant.getY() + dy <= objectif.getY())
            coordCourant.setPoint(objectif.getX(), objectif.getY());
        else if (dx <= 0 && dy >= 0 && coordCourant.getX() + dx <= objectif.getX() && coordCourant.getY() + dy >= objectif.getY())
            coordCourant.setPoint(objectif.getX(), objectif.getY());
        else
            coordCourant.setPoint(coordCourant.getX() + dx, coordCourant.getY() + dy); // Cas normal.
    }

    public void avancerRayon () {/*
        if (dx > 0 && coordCourant.getX() + dx > objectifRayon.getX())   // Tous les if sont les cas où il ateint son objectif.
            coordCourant.setPoint(objectifRayon.getX(), objectifRayon.getY());
        else if (dx < 0 && coordCourant.getX() + dx < objectifRayon.getX())
            coordCourant.setPoint(objectifRayon.getX(), objectifRayon.getY());
        else if (dy > 0 && coordCourant.getY() + dy > objectifRayon.getY())
            coordCourant.setPoint(objectifRayon.getX(), objectifRayon.getY());
        else if (dy < 0 && coordCourant.getY() + dy < objectifRayon.getY())
            coordCourant.setPoint(objectifRayon.getX(), objectifRayon.getY());
        else*/
            coordCourant.setPoint(coordCourant.getX() + dx, coordCourant.getY() + dy); // Cas normal.
    }

    public void setObjectif (Salle salle) {
        if (objectif == null) {
            objectif = findBonChemin(salle);

        } else { // peut etre faire un cas pour la fin, car getSuiv() de l'arrive == null
            objectif = objectif.getSuivant();
        }
        //System.out.println("courant : " + coordCourant);
        //System.out.println("objectif : " + objectif);
        objectifRayon.setX(objectif.getX());
        objectifRayon.setY(objectif.getY());
    }

    // Prend aussi en compte le rayon de la personne.
    public void setObjectifAvecRayon (Salle salle) {
        setObjectif(salle);
        if (objectif.getSuivant() != null) {

            System.out.println("objectif.getSuivant() != null");
            setObjectifRayonObstacle(salle);

        } else {
            System.out.println("objectif.getSuivant() == null");
            setObjectifRayonSortie(salle);
        }
    }




    public void setObjectifRayonObstacle (Salle salle) {
        for (Obstacle o : salle.getListObstacles()) {
            for (Point p : o.getListePoints()) {
                if (p.environEgale(objectif, 1)) {

                    if (objectifRayon.environEgaleX(coordCourant.getX() - rayon)) {
                        System.out.println("objectifRayon.environEgaleX(coordCourant.getX() - r) ligne 122");

                        if (objectifRayon.environEgaleY(coordCourant.getY() + rayon)) {
                            System.out.println("objectifRayon.environEgaleY(coordCourant.getY() + r) ligne 225");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
                            }

                        } else if (objectifRayon.environEgaleY(coordCourant.getY() - rayon)) {
                            System.out.println("objectifRayon.environEgaleY(coordCourant.getY() - r) ligne 135");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
                            }

                        } else if (objectifRayon.getY() + rayon > coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() + r > coordCourant.getY() ligne 145");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
                            }

                        } else if (objectifRayon.getY() - rayon > coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() - r > coordCourant.getY() ligne 154");
                            objectifRayon.setX(objectif.getX() + rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() - rayon);
                            }

                        } else if (objectifRayon.getY() + rayon < coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() + r <coordCourant.getY() ligne 103");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                            }

                        } else if (objectifRayon.getY() - rayon < coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() - r <coordCourant.getY() ligne 172");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                            }

                        }
                    } else if (objectifRayon.environEgaleX(coordCourant.getX() + rayon)) {
                        System.out.println("objectifRayon.environEqualsX(coordCourant.getX()+r) ligne 182");

                        if (objectifRayon.environEgaleY(coordCourant.getY() + rayon)) {
                            System.out.println("objectifRayon.environEgaleY(coordCourant.getY() + r) ligne 185");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
                            }

                        } else if (objectifRayon.environEgaleY(coordCourant.getY() - rayon)) {
                            System.out.println("objectifRayon.environEgaleY(coordCourant.getY() - r) ligne 195");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
                            }

                        } else if (objectifRayon.getY() + rayon > coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() + r > coordCourant.getY() ligne 205");
                            objectifRayon.setX(objectif.getX() + rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() - rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
                            }

                        } else if (objectifRayon.getY() - rayon > coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() - r > coordCourant.getY() ligne 214");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                            }

                        } else if (objectifRayon.getY() + rayon < coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() + R<coordCourant.getY() ligne 223");
                            objectifRayon.setX(objectif.getX() + rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() - rayon);
                            }

                        } else if (objectifRayon.getY() - rayon < coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() - r<coordCourant.getY() ligne 232");
                            objectifRayon.setX(objectif.getX() + rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() - rayon);
                            }

                        }
                    } else if (objectifRayon.getX() + rayon > coordCourant.getX()) {
                        System.out.println("objectif.getX()+r > coordCourant.getX() ligne 242");

                        if (objectifRayon.environEgaleY(coordCourant.getY() + rayon)) {
                            System.out.println("objectifRayon.environEgaleY(coordCourant.getY() + r) ligne 245");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() - rayon);
                            }

                        } else if (objectifRayon.environEgaleY(coordCourant.getY() - rayon)) {
                            System.out.println("objectifRayon.environEgaleY(coordCourant.getY() - r) ligne 255");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
                            }

                        } else if (objectifRayon.getY() + rayon > coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() + r > coordCourant.getY() ligne 265");
                            objectifRayon.setX(objectif.getX() + rayon);
                            objectifRayon.setY(objectif.getY() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() - rayon);
                                objectifRayon.setY(objectif.getY() + rayon);

                            }

                        } else if (objectifRayon.getY() - rayon > coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() - r > coordCourant.getY() ligne 277");
                            objectifRayon.setX(objectif.getX() + rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() - rayon);
                            }

                        } else if (objectifRayon.getY() + rayon < coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() + r < coordCourant.getY() ligne 286");
                            objectifRayon.setX(objectif.getX() + rayon);
                            objectifRayon.setY(objectif.getY() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() - rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
                            }

                        } else if (objectifRayon.getY() - rayon < coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() - r < coordCourant.getY() ligne 297");
                            objectifRayon.setX(objectif.getX() + rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() - rayon);
                            }

                        }
                    } else if (objectifRayon.getX() - rayon < coordCourant.getX()) {
                        System.out.println("objectif.getX()-r < coordCourant.getX() ligne 307");

                        if (objectifRayon.environEgaleY(coordCourant.getY() + rayon)) {
                            System.out.println("objectifRayon.environEgaleY(coordCourant.getY() + r) ligne 310");
                            objectifRayon.setX(objectif.getX() - rayon);
                            objectifRayon.setY(objectif.getY() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() - rayon);
                            }

                        } else if (objectifRayon.environEgaleY(coordCourant.getY() - rayon)) {
                            System.out.println("objectifRayon.environEgaleY(coordCourant.getY() - r) ligne 320");
                            objectifRayon.setX(objectif.getX() + rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() - rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
                            }

                        } else if (objectifRayon.getY() + rayon > coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() + r > coordCourant.getY() ligne 330");
                            objectifRayon.setX(objectif.getX() - rayon);
                            objectifRayon.setY(objectif.getY() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
                            }

                        } else if (objectifRayon.getY() - rayon > coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() - r > coordCourant.getY() ligne 341");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
                            }

                        } else if (objectifRayon.getY() + rayon < coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() + r <coordCourant.getY() ligne 351");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce ");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() - rayon);
                            }

                        } else if (objectifRayon.getY() - rayon < coordCourant.getY()) {
                            System.out.println("objectifRayon.getY() - r < coordCourant.getY() ligne 360");
                            objectifRayon.setX(objectif.getX() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce ");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() - rayon);
                            }

                        }

                    }

                }
            }
        }
    }

    public void setObjectifRayonSortie(Salle salle){
        for (Sortie sortie : salle.getListSorties()) {
            for (Point pointSortie : sortie.getListePointsSortie()) {
                if (pointSortie.environEgale(objectif, 1)) {
                    System.out.println("pointSortie.environEgale(objectif)");

                    if (objectifRayon.environEgaleX(sortie.getListePointsSortie().get(0).getX() + sortie.getLargeurPorte()) && (sortie.estMur1ou3())) {
                        System.out.println("objectifRayon.environEgaleX(sortie.getCoins().get(0).getX() + sortie.getLongueur()) && (sortie.getMur() == 1 || sortie.getMur()==3)");
                        System.out.println("X2");
                        objectifRayon.setX(objectif.getX() - rayon);

                    } else if(objectifRayon.environEgaleX(pointSortie.getX()) && (sortie.estMur1ou3())) {
                        System.out.println("X1");
                        objectifRayon.setX(objectif.getX() + rayon);

                    }else if(objectifRayon.environEgaleY(sortie.getListePointsSortie().get(0).getY() + sortie.getLargeurPorte()) && (!sortie.estMur1ou3())){
                        System.out.println("objectifRayon.environEgaleX(sortie.getCoins().get(0).getX() + sortie.getLongueur()) && (sortie.getMur()==2 || sortie.getMur()==4)");
                        System.out.println("X2");
                        objectifRayon.setY(objectif.getY() - rayon);

                    } else {
                        System.out.println("X1");
                        objectifRayon.setY(objectif.getY() + rayon);
                    }

                }
            }
        }
    }



    // Obligé de faire environ égale avec une petite precision car les doubles ne sont pas égaux.
    public boolean objectifAteint () {
        if (coordCourant.environEgale(objectifRayon,1)) {
            System.out.println("objectif ateint. ");
            return true;
        }
        else
            return false;
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


    // Permet de savoir si le perso est sorti de la salle avec plus ou moins de précision
    // La précision est importante car sinon on detecte en premier qu'il est arrivé a son dernier objectif et donc,
    // son prochain objectif est null.
    // Du coup, on detecte un peu avant qu'il soit sorti, qu'il est sorti.
    public boolean estSorti(Salle salle) {
        double precision = 3;

        if (dx > 0) {
            if (coordCourant.getX() + precision >= salle.getLargeur())
                return true;
        }
        if (dx < 0) {
            if (coordCourant.getX() - precision <= 0)
                return true;
        }
        if (dy > 0) {
            if (coordCourant.getY() + precision >= salle.getHauteur())
                return true;
        }
        if (dy < 0) {
            if (coordCourant.getY() - precision <= 0)
                return true;
        }
        return false;
    }

    public boolean estTouche(Point coordSortie,Point coordC,Point coordD){
        Point coordP = new Point(coordCourant.getX(), coordCourant.getY());
        return MathsCalcule.estCoupe(coordP,coordSortie,coordC,coordD);
    }

    public boolean estSuperpose(Point coordSortie,Point coordC,Point coordD){
        Point coordP = new Point(coordCourant.getX(), coordCourant.getY());
        return MathsCalcule.estSuperpose(coordP,coordSortie,coordC,coordD);
    }

    public List<Point> segmentObstacle(Point coordSortie,Obstacle o){
        Point coordP = new Point(coordCourant.getX(), coordCourant.getY());
        return MathsCalcule.coordSegments(coordP,coordSortie,o);
    }

    public Point getObjectif() {
        return objectif;
    }

    public Point getObjectifRayon() {
        return objectifRayon;
    }

    public Point getCoordCourant() {
        return coordCourant;
    }

    public void setVitesse(double v){
        vitesse = v;
    }

    public double getRayon() {
        return rayon;
    }
}

