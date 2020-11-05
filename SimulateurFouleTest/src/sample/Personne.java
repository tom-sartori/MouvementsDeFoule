package sample;

import java.util.List;

public class Personne {

    private Point coordCourant;
    private Point objectif;

    private double dx;
    private double dy;
    private double vitesse = 2;

    public Personne(double posX , double posY){
        coordCourant = new Point(posX, posY);
        objectif = null;
    }

    public ControllerPersonne afficher() {
        return new ControllerPersonne(this);
    }

    public Point findDxDy (Point arrivee) {
        System.out.println("findDxDy : courant : " + coordCourant + " objectif : " + arrivee);
        Point dxdy = new Point();

        double distX = Math.abs(coordCourant.getX() - arrivee.getX());
        double distY = Math.abs(coordCourant.getY() - arrivee.getY());

        if (coordCourant.getX() < arrivee.getX())
            dxdy.setX(distX / distY);
        else
            dxdy.setX(- distX / distY);

        if (coordCourant.getY() < arrivee.getY())
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
    }


    public void avancer () {
        coordCourant.setPoint(coordCourant.getX() + dx, coordCourant.getY() + dy);
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

    // Obligé de faire environ égale avec une petite precision car les doubles ne sont pas égaux.
    public boolean objectifAteint () {
        if (coordCourant.environEgale(objectif)) {
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
        return MathsCalcule.estCouper(coordP,coordSortie,coordC,coordD);
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

    public Point getCoordCourant() {
        return coordCourant;
    }
}

