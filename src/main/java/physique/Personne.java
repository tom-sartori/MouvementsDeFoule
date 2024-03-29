package physique;

import controller.ControllerPersonne;

import java.util.List;

public class Personne {

    private Point coordCourant;
    private Point objectif; // Prochain point / direction du perso

    private final double rayon = 10;
    private double dx;
    private double dy;
    private double vitesse = 1.5; // Norme du vecteur de déplacement par frame, en unité par frame.

    public Personne(double posX , double posY){
        coordCourant = new Point(posX, posY);
        objectif = null;
    }

    // Simplement pour récupérer le rayon.
    public Personne() {
    }

    public ControllerPersonne afficher() {
        return new ControllerPersonne(this);
    }

    // Retourne le x du vecteur allant du x de coordCourant à pointObjectif.
    public double getDx(Point pointObjectif) {
        return pointObjectif.getX() - coordCourant.getX();
    }

    // Retourne le x du vecteur allant du x de coordCourant à pointObjectif, normalisé par rapport à la vitesse.
    public double getDxNormalise(Point pointObjectif) {
        double dx = getDx(objectif);
        double dy = getDy(objectif);

        //argument = sqrt(x^2 + y^2)
        double argument = Math.sqrt( (dx * dx) + (dy * dy) );
        return (vitesse/argument) * dx;
    }

    // Retourne le y du vecteur allant du y de coordCourant à pointObjectif.
    public double getDy(Point pointObjectif) {
        return pointObjectif.getY() - coordCourant.getY();
    }

    // Retourne le y du vecteur allant du x de coordCourant à pointObjectif, normalisé par rapport à la vitesse.
    public double getDyNormalise(Point pointObjectif) {
        double dx = getDx(objectif);
        double dy = getDy(objectif);

        //argument = sqrt(x^2 + y^2)
        double argument = Math.sqrt( (dx * dx) + (dy * dy) );
        return (vitesse/argument) * dy;
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
        if (Math.abs(getDxNormalise(objectif)) > Math.abs(coordCourant.getX() - objectif.getX()))
            return new Point(objectif.getX(), objectif.getY());
        if (Math.abs(getDyNormalise(objectif)) > Math.abs(coordCourant.getY() - objectif.getY()))
            return new Point(objectif.getX(), objectif.getY());
        return new Point(coordCourant.getX() + getDxNormalise(objectif), coordCourant.getY() + getDyNormalise(objectif)); // Cas normal.
    }

    // Fait avancer la personne suivant getProchainMouvement()
    public void avancer(){
        coordCourant.setPoint(getProchainMouvement());
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
        objectif.setX(objectif.getX());
        objectif.setY(objectif.getY());
    }

    public boolean objectifAteint () {
        return coordCourant.equals(objectif);
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

    public List<Point> segmentObstacle(Point coordSortie, Obstacle o){
        return MathsCalcule.coordSegments(coordCourant,coordSortie,o);
    }

    public boolean estObstacle(Point coordObjectif, Obstacle o){
        if(coordObjectif.getX()>o.getListePointsPhysique().get(0).getX()
        && coordObjectif.getX()<o.getListePointsPhysique().get(1).getX()
        && coordObjectif.getY()>o.getListePointsPhysique().get(0).getY()
        && coordObjectif.getY()<o.getListePointsPhysique().get(3).getY()){
            return true;
        }else
            return !segmentObstacle(coordObjectif,o).isEmpty();
    }

    public Point getObjectif() {
        return objectif;
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

    // Renvoie la distance que la personne a encore à parcourir pour aller à la sortie.
    public double getDistance() {
        if (objectif.estSortie())
            return MathsCalcule.distance(coordCourant, objectif);
        else
            return MathsCalcule.distance(coordCourant, objectif) + objectif.getDistanceASortie();
    }
}

