package sample;

import javafx.scene.Parent;
import javafx.scene.shape.Rectangle;
import javafx.scene.paint.Color;

public class ControllerSortie extends Parent {
    private Sortie sortie;
    
    public Sortie getSortie(){
        return sortie;
    }



    public ControllerSortie(int mur, double longueur, double d, double e, Sortie sortie){
        this.sortie = sortie;
        Rectangle sortieGraphic = new Rectangle();
        if(mur==1 || mur==3){
            sortieGraphic.setWidth(longueur);
            sortieGraphic.setHeight(e);
        }
        else if(mur==2 || mur==4){
            sortieGraphic.setHeight(longueur);
            sortieGraphic.setWidth(e);
        }
        else {
            System.out.println("Erreur dans constructeur Sortie");
            return;
        }
        sortieGraphic.setFill(Color.NAVY);
        this.getChildren().add(sortieGraphic);
    }
}
