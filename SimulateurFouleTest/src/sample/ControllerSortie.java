package sample;

import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class ControllerSortie extends Parent {
    private Sortie sortie;
    private double epaisseur = 15;
    private int mur;

    public Sortie getSortie(){
        return sortie;
    }

    public int getMur(){
        return mur;
    }
    
    public ControllerSortie(int mur, double longueur, double d){
        sortie = new Sortie(mur, longueur, d);
        Rectangle sortieGraphic = new Rectangle();
        if(mur==1 || mur==3){
            sortieGraphic.setWidth(longueur);
            sortieGraphic.setHeight(epaisseur);
        }
        else if(mur==2 || mur==4){
            sortieGraphic.setHeight(longueur);
            sortieGraphic.setWidth(epaisseur);
        }
        else {
            System.out.println("Erreur dans constructeur Sortie");
            return;
        }
        sortieGraphic.setFill(Color.NAVY);
        this.mur = mur;
        this.getChildren().add(sortieGraphic);
    }
}
