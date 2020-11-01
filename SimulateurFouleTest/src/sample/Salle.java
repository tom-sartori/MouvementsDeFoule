package sample;

import java.util.ArrayList;
import java.util.List;

public class Salle {
    private double largeur;
    private double hauteur;
    private double marge = 20;
    private List<Sortie> listSorties;
    private List<Personne> listPersonnes;
    private List<Obstacle> listObstacles;

    public Salle(double lar, double hau) {  // Créée une salle rectangle avec une marge
        this.largeur = lar - (2 * marge);
        this.hauteur = hau - (2 * marge);
        this.listObstacles = new ArrayList<>();
        this.listSorties = new ArrayList<>();
        this.listPersonnes = new ArrayList<>();

    }

    public void addObstacle (Obstacle obstacle){
        listObstacles.add(obstacle);
    }

    public void addSortie (Sortie sortie) {
        listSorties.add(sortie);

        if (sortie.getMur() == 1) {
            sortie.setX1(marge + sortie.getDistance());
            sortie.setY1(marge);
            sortie.setX2(sortie.getX1() + sortie.getLongueur());
            sortie.setY2(marge);
        }
        if (sortie.getMur() == 2) {
            sortie.setX1(marge + getLargeur());
            sortie.setY1(marge + sortie.getDistance());
            sortie.setX2(marge + getLargeur());
            sortie.setY2(sortie.getY1() + sortie.getLongueur());
        }
        if (sortie.getMur() == 3) {
            sortie.setX1(marge + sortie.getDistance());
            sortie.setY1(marge + getHauteur());
            sortie.setX2(sortie.getX1() + sortie.getLongueur());
            sortie.setY2(marge + getHauteur());
        }
        if (sortie.getMur() == 4) {
            sortie.setX1(marge);
            sortie.setY1(marge + sortie.getDistance());
            sortie.setX2(marge);
            sortie.setY2(sortie.getY1() + sortie.getLongueur());
        }
//<<<<<<< Joachim
        sortie.setPoint1(sortie.getX1(), sortie.getY1());
        sortie.setPoint2(sortie.getX2(), sortie.getY2());
//=======

        sortie.setPoint1(sortie.getX1(), sortie.getY1());
        sortie.setPoint2(sortie.getX2(), sortie.getY2());


        this.getChildren().add(sortie);
    }

    public void addPersonne (Personne personne) {
        listPersonnes.add(personne);
        getChildren().add(personne);
    }

    public void addObstacle (Obstacle obstacle){
        listObstacles.add(obstacle);
        this.getChildren().add(obstacle);
//>>>>>>> main
    }

    public List<Obstacle> getListObstacles(){
        return listObstacles;
    }

//<<<<<<< Joachim
    public List<Sortie> getListSorties(){
        return listSorties;
    }
//=======
    public void demarrer () {
        if (!listPersonnes.isEmpty()) {
            for (Personne personne : listPersonnes) {   // Pour chaque personne de la salle
                personne.setDxDyNormalise(this);         // Initialise dx et dy
            }

            Salle salle = this; // Pas sur de la propreté de cette ligne mais ne fonctionnait pas dans la timeline sans

            if (loop == null) {

                loop = new Timeline(new KeyFrame(Duration.millis(20), new EventHandler<ActionEvent>() {
                    public void handle(ActionEvent arg) {

                        for (int i = 0; i < listPersonnes.size(); i++) {
                            if (listPersonnes.get(i).estSorti(salle))
                                removePersonne(listPersonnes.get(i));
                            else
                                listPersonnes.get(i).avancer();
                        }
                    }
                }));
                loop.setCycleCount(Timeline.INDEFINITE);
                loop.play();
            } else if (loop.getStatus() == Animation.Status.PAUSED) {
                loop.play();
            }
        }
    }

//>>>>>>> main

    public void initPersonneDxDy(){
        for (Personne personne : listPersonnes) {   // Pour chaque personne de la salle
            personne.setDxDyNormalise(this);         // Initialise dx et dy
        }
    }

    public double getLargeur() {
        return largeur;
    }

    public double getHauteur() {
        return hauteur;
    }

    public double getMarge() {
        return marge;
    }
    public void addPersonne(Personne personne){
        listPersonnes.add(personne);
    }

    public void removePersonne (Personne personne) {
        listPersonnes.remove(personne);
    }

    public void removeAllPersonne(){
        while(!listPersonnes.isEmpty())
            removePersonne(listPersonnes.get(0));
    }

        // Ne prend pas en compte les obstacles
        public Point findSortiePlusProcheIndirecte(Point A) {
            double distance1 = -1;
            double distance2 = -1;
    
            double distanceCourte = 1000000;
            Point plusProche = new Point();
    
            if (!listSorties.isEmpty()) {
    
                for (Sortie sortie : listSorties) {
    
                    distance1 = MathsCalcule.distance(A, sortie.getPoint1());
                    distance2 = MathsCalcule.distance(A, sortie.getPoint2());
    
                    if (Math.min(distance1, distance2) < distanceCourte) {
                        if (distance1 < distance2) {
                            distanceCourte = distance1;
                            plusProche = sortie.getPoint1();
                        } else {
                            distanceCourte = distance2;
                            plusProche = sortie.getPoint2();
                        }
                    }
                }
            }
            else
                System.out.println("Pas de sorties dans la salle");
            return plusProche;
        }
    
    
        public Point findSortiePlusProcheDirecte(Point A) {
            double distance;
            double distance1 = -1;
            double distance2 = -1;
    
            double distanceCourte = 1000000;
            Point plusProche = null;
    
            if (!listSorties.isEmpty()) {
    
                for (Sortie sortie : listSorties) {
                    for (Point pointSortie : sortie.getListePointsSortie()) {
                        if (!intersecObstacle(A, pointSortie)) {
                            distance = MathsCalcule.distance(A, pointSortie);
                            if (distance < distanceCourte) {
                                distanceCourte = distance;
                                plusProche = pointSortie;
                            }
                        }
                    }
                }
                return plusProche;
            }
            else {
                System.out.println("Pas de sorties dans la salle");
                return null;
            }
        }
    
    
        public boolean intersecObstacle(Point coordA,Point coordB) {
            boolean b = false;
            for(Obstacle obstacle: listObstacles) {
                if (MathsCalcule.coordSegments(coordA, coordB, obstacle).isEmpty()) {
                    b = false;
                } else
                    return true;
            }
    
            return b;
        }

}
