package sample;

import java.util.List;

public class Personne {

    private Point coordCourant;
    private Point objectif; // Prochain point / direction du perso
    private Point objectifRayon;


    private double rayon;
    private double dx;
    private double dy;
    private double vitesse = 20; // Norme du vecteur de déplacement par frame, en unité par frame.

    public Personne(double posX , double posY){
        coordCourant = new Point(posX, posY);
        objectif = null;
        rayon = 8;
        objectifRayon = new Point();
    }

    public ControllerPersonne afficher() {
        return new ControllerPersonne(this);
    }

    // Retourne le vecteur de coordCourant à arrive
    public Point findDxDy (Point pointObjectif) {
      return new Point(pointObjectif.getX() - coordCourant.getX(), pointObjectif.getY() - coordCourant.getY()); 
    }

    // Normalise dx dy par rapport à la vitesse de la personne.
    public void setDxDyNormalise (Point pointObjectif) {
        Point coordDxDy = findDxDy(pointObjectif);

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
    public Point getProchainMouvement () {
        if (dx >= 0 && dy >= 0 && coordCourant.getX() + dx >= objectif.getX() && coordCourant.getY() + dy >= objectif.getY())
            return new Point(objectif.getX(), objectif.getY());
        else if (dx >= 0 && dy <= 0 && coordCourant.getX() + dx >= objectif.getX() && coordCourant.getY() + dy <= objectif.getY())
            return new Point(objectif.getX(), objectif.getY());
        else if (dx <= 0 && dy <= 0 && coordCourant.getX() + dx <= objectif.getX() && coordCourant.getY() + dy <= objectif.getY())
            return new Point(objectif.getX(), objectif.getY());
        else if (dx <= 0 && dy >= 0 && coordCourant.getX() + dx <= objectif.getX() && coordCourant.getY() + dy >= objectif.getY())
            return new Point(objectif.getX(), objectif.getY());
        else
            return new Point(coordCourant.getX() + dx, coordCourant.getY() + dy); // Cas normal.
    }

    public void avancer(){
        Point p = getProchainMouvement();
        coordCourant.setPoint(p.getX(), p.getY());
    }

    public void avancerRayon () { if (dx >= 0 && dy >= 0 && coordCourant.getX() + dx >= objectifRayon.getX() && coordCourant.getY() + dy >= objectifRayon.getY())
        coordCourant.setPoint(objectifRayon.getX(), objectifRayon.getY());
    else if (dx >= 0 && dy <= 0 && coordCourant.getX() + dx >= objectifRayon.getX() && coordCourant.getY() + dy <= objectifRayon.getY())
        coordCourant.setPoint(objectifRayon.getX(), objectifRayon.getY());
    else if (dx <= 0 && dy <= 0 && coordCourant.getX() + dx <= objectifRayon.getX() && coordCourant.getY() + dy <= objectifRayon.getY())
        coordCourant.setPoint(objectifRayon.getX(), objectifRayon.getY());
    else if (dx <= 0 && dy >= 0 && coordCourant.getX() + dx <= objectifRayon.getX() && coordCourant.getY() + dy >= objectifRayon.getY())
        coordCourant.setPoint(objectifRayon.getX(), objectifRayon.getY());
    else
            coordCourant.setPoint(coordCourant.getX() + dx, coordCourant.getY() + dy); // Cas normal.
    }

    // Est appelé quand un play la salle eu début, mais aussi à chaque fois qu'il vient d'atteindre son objectif.
    // Jamais appelé quand getVraisSuivant() == null
    public void setObjectif (Salle salle) {
        if (objectif == null) {
            objectif = findBonChemin(salle);

        } else { // peut etre faire un cas pour la fin, car getSuiv() de l'arrive == null
            objectif = objectif.getSuivant();
        }
        System.out.println("courant : " + coordCourant);
        System.out.println("objectif : " + objectif);
        objectifRayon.setX(objectif.getX());
        objectifRayon.setY(objectif.getY());
    }

    // Prend aussi en compte le rayon de la personne.
    public void setObjectifRayon (Salle salle) {
        setObjectif(salle);

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
                            objectifRayon.setX(objectif.getX() + rayon);
                            objectifRayon.setY(objectif.getY() - rayon);
                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() - rayon);
                                objectifRayon.setY(objectif.getY() - rayon);
                            }

                        } else if (objectifRayon.environEgaleY(coordCourant.getY() - rayon)) {
                            System.out.println("objectifRayon.environEgaleY(coordCourant.getY() - r) ligne 255");
                            objectifRayon.setX(objectif.getX() - rayon);
                            objectifRayon.setY(objectif.getY() - rayon);
                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce");
                                objectifRayon.setX(objectif.getX() + rayon);
                                objectifRayon.setY(objectif.getY() - rayon);
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
                            objectifRayon.setY(objectif.getY() + rayon);

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
                            objectifRayon.setY(objectif.getY() + rayon);

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
                            objectifRayon.setX(objectif.getX() + rayon);
                            objectifRayon.setY(objectif.getY() - rayon);

                            if (!segmentObstacle(objectifRayon, o).isEmpty()) {
                                System.out.println("objectifRayon était obstalce ");
                                objectifRayon.setX(objectif.getX() - rayon);
                                objectifRayon.setY(objectif.getY() + rayon);
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

    // Obligé de faire environ égale avec une petite precision car les doubles ne sont pas égaux.
    public boolean objectifAteint () {
        return coordCourant.equals(objectifRayon);
    }

    // Appelé par set objectif pour initialiser le premier objectif de la personne
    public Point findBonChemin (Salle salle) {
        Point premierObjectif = null;
        double distance, distanceCourante;
        distance = 100000; // distance infinie

        for (Point pointDirect : salle.getListePointsDirectes(coordCourant)) { // Fonctionne pas car personne pas dans le graphe
            if (MathsCalcule.distance(coordCourant, pointDirect) != 0) { // Car si 0,c'est lui meme.
                distanceCourante = MathsCalcule.distance(coordCourant, pointDirect) + pointDirect.getDistanceASortie();
                if (distanceCourante < distance) {
                    distance = distanceCourante;
                    premierObjectif = pointDirect;
                }
            }
        }
        //System.out.println("Premier objectif : " + premierObjectif);
        return premierObjectif;
    }

// A mdoif
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

    // Retourne vrais si la personne et son point de sortie intersectent le segment CD
    public boolean estTouche(Point nextPoint,Point coordC,Point coordD){
        Point coordP = new Point(coordCourant.getX(), coordCourant.getY());
        return MathsCalcule.estCoupe(coordP,nextPoint,coordC,coordD);
    }

    // Retourne vrais si le segment coordCourant coordSortie est superposé à CD
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

    public Point getNextPos(){
        return new Point(this.coordCourant.getX() + this.dx, this.coordCourant.getY() + this.dy);
    }

}

