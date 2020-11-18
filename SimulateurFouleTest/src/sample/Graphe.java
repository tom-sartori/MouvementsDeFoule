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
            point.setDistanceCourante(100000);  // Distance infinie.
            listeCourante.add(point);
        }
        listeCourante.add(pointSortie);
        pointSortie.setDistanceCourante(0);

        while (!listeCourante.isEmpty()) {
            double distance = 1000000;  // Distance infinie.
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

    public void afficherPrecedentsPointObstacle(Point pointObstacle) {
        Point courant = new Point(pointObstacle);
        Point suivant = new Point(courant.getSuivant());
        do {
            addChemin(new Chemin(courant, suivant));
            System.out.println("Courant : " + courant + " suivant : " + suivant);
            courant = suivant;
            suivant = courant.getSuivant();
        }
        while (suivant != null);
        System.out.println("");
    }

    public void afficherPrecedentsListPointsObstacles() {
        for (Point point : listePointsObstacles) {
            afficherPrecedentsPointObstacle(point);
            System.out.println("Changement point obstacle. ");
            System.out.println("");
        }
    }


    // Premet, pour chaque coin d'obstacle, de créer le plus court chemin entre ce point et la sortie la plus proche de ce point.
    // Créée et ajoute aussi les Chemins correspondants dans le graphe afin de permettre l'affichage des plus courts chemins dans le graphe.
    public void creerTousLesPlusCourtsChemins() {
        for (Point pointSortie : listePointsSorties) {
            affecteVraisSuivants();

            creerPlusCourtChemin(pointSortie);
            affecteVraisSuivants();

            // Affecte à chaque point du plus court chemin vers la sortie, le point suivant ainsi que la distance à la sortie.
            //affecteSuivants(getListePointsCheminPlusCourt(pointSortie, salle.findPointSortiePlusProcheIndirecte(pointSortie)));

            // Ajoute les chemins pour l'affichage.
            //addChemin(getListePointsCheminPlusCourt(pointSortie, salle.findPointSortiePlusProcheIndirecte(pointSortie)));
        }
        affecteVraisSuivants();
        afficherPrecedentsListPointsObstacles();
    }


    // Apres que tous les plus courts chemins ont été calculés pour un point,
    // cette fonction permet de retourner une liste, composée des points du plus court chemin, du départ à l'arrivé en paramètre.
    // On utilise le point précédent, de chaque point, de l'arrivé au départ.
    // La liste retournée est dans l'ordre : [depart, ..., arrive]
    public List<Point> getListePointsCheminPlusCourt(Point depart, Point arrive) {
        System.out.println("depart : " + depart + " arrive : " + arrive);
        List<Point> cheminPlusCourt = new ArrayList<>();
        Point courant = arrive;

        while (!courant.equals(depart)) {
            System.out.println("test");
            cheminPlusCourt.add(0, courant);
            courant = courant.getSuivantCourant();
        }
        cheminPlusCourt.add(0, depart);

        System.out.println("sortie");
        return cheminPlusCourt;
    }

    public void affecteVraisSuivants () {
        for (Point pointObstacle : listePointsObstacles) {
            System.out.println("");
            System.out.println("affecte suivant : point : " + pointObstacle.toStringV2());
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
            System.out.println("affecte suivant : point : " + pointObstacle.toStringV2());
            System.out.println("");
        }
    }


    // Renvoie la partie graphique du graphe.
    // Attention, la partie graphique n'affiche que des Chemin et non pas des Point.
    public ControllerGraphe afficher () {
        return new ControllerGraphe(this);
    }


    public List<Chemin> getListeChemins() {
        return listeChemins;
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
}