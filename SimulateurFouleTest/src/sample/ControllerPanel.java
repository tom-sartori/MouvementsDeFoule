package sample;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

public class ControllerPanel extends Parent {
    Button playButton;
    Button pauseButton;
    Button clearButton;
    Slider vitesse;
    CheckBox graphe;
    CheckBox collisions;
    CheckBox rayon;

    public ControllerPanel(){
        this.minHeight(40);
        playButton = createButton("Play", 20);
        pauseButton = createButton("Pause", 70);
        clearButton = createButton("Clear", 130);
        vitesse = new Slider(0,5,0.1);
        vitesse.setValue(1.5);
        vitesse.setShowTickMarks(true);
        vitesse.setShowTickLabels(true);
        vitesse.setMajorTickUnit(0.25f);
        vitesse.setBlockIncrement(0.1f);
        vitesse.setTranslateX(200);
        graphe = new CheckBox("Afficher graphe");
        graphe.setTranslateX(350);
        collisions = new CheckBox("Activer les collisions");
        collisions.setTranslateX(350);
        collisions.setTranslateY(20);
        rayon = new CheckBox("Activer le rayon");
        rayon.setTranslateX(350);
        rayon.setTranslateY(40);

        this.getChildren().addAll(rayon, collisions, graphe, vitesse, playButton, pauseButton, clearButton);
    }

    public Button createButton(String text, int position){
        Button b = new Button(text);
        b.setMinHeight(30);
        b.setTranslateX(position);
        b.setStyle("-fx-border-color: white; -fx-border-width: 5px;");
        return b;
    }

    public Button getPlayButton(){
        return playButton;
    }

    public Button getPauseButton(){
        return pauseButton;
    }

    public Button getClearButton(){
        return clearButton;
    }

    public double getVitesseValue(){
        return vitesse.getValue();
    }

    public CheckBox getGrapheCB(){
        return graphe;
    }

    public Boolean getCollisionStatus(){
        return collisions.isSelected();
    }

    public Boolean getRayonStatus(){
        return rayon.isSelected();
    }
}
