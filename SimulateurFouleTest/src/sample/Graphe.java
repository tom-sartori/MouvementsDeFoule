package sample;

import java.util.ArrayList;
import java.util.List;

public class Graphe {

    private List<Chemin> listeChemins;  // Que pour l'affichage
    private List<Point> listePoints;    // Probleme redondance
    private List<Point> listePointsObstacles;


    public Graphe (Salle salle) {
        listeChemins = new ArrayList<>();

        listePoints = new ArrayList<>();    // Pas forcement Array
        listePointsObstacles = new ArrayList<>();
        for (Obstacle obstacle : salle.getListObstacles()) {
            for (Point point : obstacle.getListePoints()) {
                listePoints.add(point);
                listePointsObstacles.add(point);
                Point pointSortieDirectProche = salle.findSortiePlusProcheDirecte(point);
                System.out.println("point : " + pointSortieDirectProche);
                if (pointSortieDirectProche != null) {
                    listePoints.add(pointSortieDirectProche);
                    salle.addPersonne(new Personne(pointSortieDirectProche.getX(), pointSortieDirectProche.getY()));
                }
            }
        }
        /*
        for (Sortie sortie : salle.getListSorties()) {
            for (Point point : sortie.getListePointsSortie())
                listePoints.add(point);
        }

         */

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
                Point pointSortie = salle.findSortiePlusProcheDirecte(point);
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
    public void creerPlusCourtChemin(Salle salle, Point depart) {
        List<Point> listeCourante = new ArrayList<>();

        for (Point point : listePoints) {
            point.setDistance(100000);  // Distance infinie.
            listeCourante.add(point);
        }
        depart.setDistance(0);

        while (!listeCourante.isEmpty()) {
            double distance = 1000000;  // Distance infinie.

            Point courant = new Point();

            for (Point point : listeCourante) {
                if (point.getDistance() < distance) {
                    distance = point.getDistance();
                    courant = point;
                }
            }

            listeCourante.remove(courant);
            List<Point> listeVoisins = getListePointsDirectes(salle, courant);

            for (Point voisin : listeVoisins) {
                double nouveau = courant.getDistance() + MathsCalcule.distance(courant, voisin);
                if (nouveau < voisin.getDistance()) {
                    //addChemin(new Chemin(voisin, courant)); // Premet d'ajouter tous les chemins possibles pour les afficher.

                    voisin.setDistance(nouveau);
                    voisin.setPrecedent(new Point(courant));
                }
            }
        }
    }


    // Premet, pour chaque coin d'obstacle, de créer le plus court chemin entre ce point et la sortie la plus proche de ce point.
    // Créée et ajoute aussi les Chemins correspondants dans le graphe afin de permettre l'affichage des plus courts chemins dans le graphe.
    public void creerCheminPlusCourtAvecSortie (Salle salle) {
        for (Point pointObstacle : listePointsObstacles) {
            creerPlusCourtChemin(salle, pointObstacle);

            // Affecte à chaque point du plus court chemin vers la sortie, le point suivant ainsi que la distance à la sortie.
            affecteSuivants(getListePointsCheminPlusCourt(pointObstacle, salle.findSortiePlusProcheIndirecte(pointObstacle)));

            // Ajoute les chemins pour l'affichage.
            addChemin(getListePointsCheminPlusCourt(pointObstacle, salle.findSortiePlusProcheIndirecte(pointObstacle)));
        }
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
            courant = courant.getPrecedent();
        }
        cheminPlusCourt.add(0, depart);

        System.out.println("sortie");
        return cheminPlusCourt;
    }


    // Pour une liste de points ordonnés d'un depart vers une sortie,
    // cette fonction affecte pour chaque point, son suivant ainsi que sa distance à la sortie.
    public void affecteSuivants (List<Point> listePointsChemin) {
        listePointsChemin.get(listePointsChemin.size() -1).setDistanceSortie(0);

        for (int i = listePointsChemin.size() -2; i >= 0; i--) {
            listePointsChemin.get(i).setSuivant(listePointsChemin.get(i +1));
            listePointsChemin.get(i).setDistanceSortie(listePointsChemin.get(i + 1).getDistanceSortie() + MathsCalcule.distance(listePointsChemin.get(i), listePointsChemin.get(i +1)));
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


    public List<Point> getListePoints() {
        return listePoints;
    }


    // Permet de renvoyer tous les points directs au Point A en parametre.
    // Attention, ne fonctionne pas si le Point est un Personnage car les personnages ne font pas partie du Graphe.
    // Si besoin avec un perso, appeler la fonction getListePointsDirectesPerso(Salle salle, Point A).
    public List<Point> getListePointsDirectes (Salle salle, Point A) {
        List<Point> listePointsDirectes = new ArrayList<>();

        for (Point point : listePoints) {
            if (!salle.intersecObstacle(A, point))
                listePointsDirectes.add(point);
        }
        return listePointsDirectes;
    }


    // Permet de renvoyer tous les points directes au Point A en parametre.
    // Ce point peut etre un perso car il est ajouté puis retiré de la liste principale.
    // Attention, le point directe le plus proche du perso est lui meme (donc à distance 0).
    public List<Point> getListePointsDirectesPerso(Salle salle, Point A) {
        List<Point> listePointsDirectes = new ArrayList<>();

        listePoints.add(A);
        listePoints.add(salle.findSortiePlusProcheDirecte(A));
        for (Point point : listePoints) {
            if (!salle.intersecObstacle(A, point))
                listePointsDirectes.add(point);
        }
        listePoints.remove(A);
        listePoints.remove(salle.findSortiePlusProcheDirecte(A));
        return listePointsDirectes;
    }

}