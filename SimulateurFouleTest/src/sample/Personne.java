package sample;

import java.util.List;

public class Personne {

    private Point coordCourant;
    private Point objectif; // Prochain point / direction du perso
    private Point objectifRayon;


    private double rayon;
    private double dx;
    private double dy;
    private double vitesse = 1.5; // Norme du vecteur de déplacement par frame, en unité par frame.

    public Personne(double posX , double posY){
        coordCourant = new Point(posX, posY);
        objectif = null;
        rayon = 5;
        objectifRayon = new Point();
    }

    public ControllerPersonne afficher() {
        return new ControllerPersonne(this);
    }

    // Retourne le x du vecteur allant du x de coordCourant à pointObjectif.
    public double getDx(Point pointObjectif) {
        return pointObjectif.getX() - coordCourant.getX();
    }

    // Retourne le y du vecteur allant du y de coordCourant à pointObjectif.
    public double getDy(Point pointObjectif) {
        return pointObjectif.getY() - coordCourant.getY();
    }

    // Normalise dx dy par rapport à la vitesse de la personne.
    public void setDxDyNormalise (Point pointObjectif) {
        double dx = getDx(pointObjectif);
        double dy = getDy(pointObjectif);

        //argument = sqrt(x^2 + y^2)
        double argument = Math.sqrt( (dx * dx) + (dy * dy) );

        this.dx = (vitesse/argument) * dx;
        this.dy = (vitesse/argument) * dy;
    }

    // Renvoie le prochain coordCourant suivant les dx, dy de Personne (donc normalement en direction de son Point objectif).
    // Si le perso dépasse en x ou y son objectif, cela signifie qu'il ateint son objectif et donc coordCourant prend les coord de l'objectif.
    // Sinon, il avance simplement de dx et dy
    public Point getProchainMouvement() {
        if (Math.abs(dx) > Math.abs(coordCourant.getX() - objectif.getX()))
            return new Point(objectif.getX(), objectif.getY());
        if (Math.abs(dy) > Math.abs(coordCourant.getY() - objectif.getY()))
            return new Point(objectif.getX(), objectif.getY());
        return new Point(coordCourant.getX() + dx, coordCourant.getY() + dy); // Cas normal.
    }

    // Fait avancer la personne suivant getProchainMouvement()
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
        //System.out.println("courant : " + coordCourant);
        //System.out.println("objectif : " + objectif);
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

    public boolean objectifAteint () {
        return coordCourant.equals(objectifRayon);
    }

    // Appelé par set objectif pour initialiser le premier objectif de la personne
    public Point findBonChemin (Salle salle) {
        Point premierObjectif = null;
        double distance, distanceCourante;
        distance = Double.POSITIVE_INFINITY;

        for (Point pointDirect : salle.getListePointsDirectes(coordCourant)) { // Fonctionne pas car personne pas dans le graphe
            if (MathsCalcule.distance(coordCourant, pointDirect) != 0) { // Car si 0,c'est lui meme.
                distanceCourante = MathsCalcule.distance(coordCourant, pointDirect) + pointDirect.getDistanceASortie();
                if (distanceCourante < distance) {
                    distance = distanceCourante;
                    premierObjectif = pointDirect;
                }
            }
        }
        return premierObjectif;
    }

    // Permet de savoir si le perso est sorti de la salle.
    public boolean estSorti() {
        return objectif.estSortie() && coordCourant.equals(objectif);
    }

    // Retourne vrais si la personne et son point de sortie intersectent le segment CD
    public boolean estTouche(Point coordSortie,Point coordC,Point coordD){
        return MathsCalcule.estCoupe(coordCourant,coordSortie,coordC,coordD);
    }

    // Retourne vrais si le segment coordCourant coordSortie est superposé à CD
    public boolean estSuperpose(Point coordSortie,Point coordC,Point coordD){
        return MathsCalcule.estSuperpose(coordCourant,coordSortie,coordC,coordD);
    }

    public List<Point> segmentObstacle(Point coordSortie,Obstacle o){
        return MathsCalcule.coordSegments(coordCourant,coordSortie,o);
    }

    public boolean estObstacle(Point coordObjectif,Obstacle o){
        if(coordObjectif.getX()>o.getListePoints().get(0).getX()
        && coordObjectif.getX()<o.getListePoints().get(1).getX()
        && coordObjectif.getY()>o.getListePoints().get(0).getY()
        && coordObjectif.getY()<o.getListePoints().get(3).getY()){
            return true;
        }else
            return !segmentObstacle(coordObjectif,o).isEmpty();
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

