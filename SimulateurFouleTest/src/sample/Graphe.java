package sample;

import java.util.ArrayList;
import java.util.List;

public class Graphe {

    private Salle salle;
    private List<Chemin> listeChemins;  // Que pour l'affichage
    private List<Point> listePointsObstacles;
    private List<Point> listePointsSorties;


    public Graphe (Salle s) {
        this.salle = s;
        listeChemins = new ArrayList<>();

        listePointsObstacles = new ArrayList<>();
        listePointsSorties = new ArrayList<>();

        for (Obstacle obstacle : salle.getListObstacles()) {
            for (Point pointObstacle : obstacle.getListePoints()) {
                listePointsObstacles.add(pointObstacle);
                Point pointSortieDirectProche = salle.findPointSortiePlusProcheDirect(pointObstacle);

                if (pointSortieDirectProche != null) {
                    //addChemin(new Chemin(pointObstacle, pointSortieDirectProche));
                    if (!listePointsSorties.contains(pointSortieDirectProche)) {
                        listePointsSorties.add(pointSortieDirectProche);
                    }
                }
            }
        }

        System.out.println("Nombre de points obstacles : " + listePointsObstacles.size());
        System.out.println("Nombre de points sorties : " + listePointsSorties.size());
    }

    public void addChemin (Chemin chemin) {
        listeChemins.add(chemin);
    }


    // Créée les chemins dans l'ordre de la liste.
    public ControllerGraphe addChemin (List<Point> parcourPoints) {
        for (int i = 0; i < parcourPoints.size() -1; i++) {
            addChemin(new Chemin(parcourPoints.get(i), parcourPoints.get(i +1)));
        }
        return new ControllerGraphe(this);
    }


    // Fonction inutile mais reste là en cas de besoin.
    // Fait une sorte de produit cartésien de tous les points du graphe et trace des chemins entre eux.
    // Pas très utile car trace des chemins sur un meme point et compare des memes diagoles.
    public void creerTousLesChemins(Salle salle) {
        for (Obstacle obstacle : salle.getListObstacles()) {
            for (Point point : obstacle.getListePoints()) {
                for (Obstacle obstacle1 : salle.getListObstacles()) {
                    for (Point point1 : obstacle1.getListePoints()) {
                        if (!salle.intersecObstacle(point, point1))
                            addChemin(new Chemin(point, point1));
                    }
                }
                Point pointSortie = salle.findPointSortiePlusProcheDirect(point);
                if (pointSortie != null) {
                    addChemin(new Chemin(point, pointSortie));
                }
            }
        }
    }

    // Pour un point de départ, cette fonction calcule tous les plus courts chemins vers ce point (algorithme de Dijkstra).
    // Cette fonctionne affecte donc pour chaque Point, son précédent ainsi que la distance vers le depart.
    // Nous avons donc le chemin inverse entre le depart et la sortie grace aux précédents.
    // Pré-requis : depart est un point obstacle
    public void creerPlusCourtChemin(Point pointSortie) {
        List<Point> listeCourante = new ArrayList<>();

        for (Point point : listePointsObstacles) {
            point.setDistanceCourante(Double.POSITIVE_INFINITY);
            listeCourante.add(point);
        }
        listeCourante.add(pointSortie);
        pointSortie.setDistanceCourante(0);

        while (!listeCourante.isEmpty()) {
            double distance = Double.POSITIVE_INFINITY;
            Point courant = null;

            for (Point point : listeCourante) {
                if (point.getDistanceCourante() < distance) {
                    distance = point.getDistanceCourante();
                    courant = point;
                }
            }

            listeCourante.remove(courant);
            List<Point> listeVoisins = getListePointsDirectes(salle, courant);  // Points sorties non comprits appart celui en param.

            for (Point voisin : listeVoisins) {
                double nouvelleDistance = courant.getDistanceCourante() + MathsCalcule.distance(courant, voisin);
                if (nouvelleDistance < voisin.getDistanceCourante()) {
                    voisin.setDistanceCourante(nouvelleDistance);
                    voisin.setSuivantCourant(courant);
                }
            }
        }
    }

    // Pour chaque point obstacle, regarde si sa distance à la sortie courant, est inférieure à sa distance à la sortie actuelle.
    // Si la courante est inférieure, alors l'actuelle devient la courante et le point suivant devient le suivant courant.
    public void affecteVraisSuivants () {
        for (Point pointObstacle : listePointsObstacles) {
            if (pointObstacle.getSuivant() == null) {
                pointObstacle.setSuivant(pointObstacle.getSuivantCourant());
                pointObstacle.setDistanceASortie(pointObstacle.getDistanceCourante());
            }
            else {
                if (pointObstacle.getDistanceCourante() < pointObstacle.getDistanceASortie()) {
                    pointObstacle.setSuivant(pointObstacle.getSuivantCourant());
                    pointObstacle.setDistanceASortie(pointObstacle.getDistanceCourante());
                }
            }
        }
    }

    // Affecte à chaque point obstacle le point suivant et la distance, pour aller à la sortie ayant le chemin le plus court.
    public void creerTousLesPlusCourtsChemins() {
        for (Point pointSortie : listePointsSorties) {
            creerPlusCourtChemin(pointSortie);
            affecteVraisSuivants();
        }
        afficherSuivants();
    }

    // Permet l'afficher, pour tous les point obstacles, de leur suivant à chacun.
    // Donc, affiche le graphe de plus court chemin.
    public void afficherSuivants() {
        for (Point point : listePointsObstacles) {
            addChemin(new Chemin(point, point.getSuivant()));
        }
    }

    // Renvoie la partie graphique du graphe.
    // Attention, la partie graphique n'affiche que des Chemin et non pas des Point.
    public ControllerGraphe afficher () {
        return new ControllerGraphe(this);
    }

    // Permet de renvoyer tous les points directs au Point A en parametre.
    // Attention, ne fonctionne pas si le Point est un Personnage car les personnages ne font pas partie du Graphe.
    // Si besoin avec un perso, appeler la fonction getListePointsDirectesPerso(Salle salle, Point A).
    public List<Point> getListePointsDirectes (Salle salle, Point A) {
        List<Point> listePointsDirectes = new ArrayList<>();

        for (Point point : listePointsObstacles) {
            if (!salle.intersecObstacle(A, point))
                listePointsDirectes.add(point);
        }
        if (!A.estSortie()) {
            for (Point point : listePointsSorties) {
                if (!salle.intersecObstacle(A, point))
                    listePointsDirectes.add(point);
            }
        }

        return listePointsDirectes;
    }

    public List<Chemin> getListeChemins() {
        return listeChemins;
    }
}