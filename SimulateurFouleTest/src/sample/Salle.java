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
    private ControllerSalle controllerSalle;

    public ControllerSalle getControllerSalle(){
        return controllerSalle;
    }

    public Salle(double lar, double hau) {  // Créée une salle rectangle avec une marge
        this.largeur = lar - (2 * marge);
        this.hauteur = hau - (2 * marge);
        this.listObstacles = new ArrayList<>();
        this.listSorties = new ArrayList<>();
        this.listPersonnes = new ArrayList<>();
        controllerSalle = new ControllerSalle(lar,hau,this);
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

        controllerSalle.addSortie(sortie.getControllerSortie(), sortie.getMur());
    }

    public List<Obstacle> getListObstacles(){
        return listObstacles;
    }

    public List<Sortie> getListSorties(){
        return listSorties;
    }

    public void initPersonneDxDy(){
        for (Personne personne : listPersonnes) {   // Pour chaque personne de la salle
            personne.getDxDy(this);         // Initialise dx et dy
        }
    }

    public void demarrer () {    
        Salle salle = this; // Pas sur de la propreté de cette ligne mais ne fonctionnait pas dans la timeline sans
        for (int i = 0; i < listPersonnes.size(); i ++) {
            if (listPersonnes.get(i).getControllerPersonne().estSorti(salle))
                removePersonne(listPersonnes.get(i));
            else
                listPersonnes.get(i).avancer();
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
    public void addPersonne(double x, double y){
        Personne personne = new Personne(x, y);
        listPersonnes.add(personne);
        controllerSalle.createPersonne(personne.getControllerPersonne());
    }

    public void removePersonne (Personne personne) {
        listPersonnes.remove(personne);
        controllerSalle.removePersonneGraphic(personne.getControllerPersonne());
    }

    public void removeAllPersonne(){
        controllerSalle.removeAllPersonneGraphic();
        while(!listPersonnes.isEmpty())
            removePersonne(listPersonnes.get(0));
    }
}
