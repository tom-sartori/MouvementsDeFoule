package sample.controller;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;

public class ControllerPanel extends Parent {
    Button playButton; //lance la simulation
    Button pauseButton; //pause la simulation
    Button clearButton; //stop la simulation et vide la salle
    Button addPersonButton; //ajoute des personnes
    Button addObstacleButton; //ajoute des obstacles
    Button validerObstacleButton; //valide la création d'un obstacle
    Button supprimerObstacleButton; //supprime un obstacle
    Slider vitesse; //change la vitesse des personnes
    CheckBox graphe; //active/desactive affichage graphe
    CheckBox collisions; //active/desactive collisions
    CheckBox rayon; //active/desactive rayon

    public ControllerPanel(){
        this.minHeight(40);
        playButton = createButton("Play", 20);
        pauseButton = createButton("Pause", 70);
        clearButton = createButton("Clear", 130);
        addPersonButton = createButton("Ajouter Personne", 500);
        addObstacleButton = createButton("Ajouter Obstacle", 650);
        validerObstacleButton = createButton("Valider", 450);
        supprimerObstacleButton = createButton("Supprimer Obstacle", 650);
        supprimerObstacleButton.setTranslateY(50);
        validerObstacleButton.setVisible(false);
        vitesse = new Slider(0,5,0.1);
        vitesse.setValue(1.5);
        vitesse.setShowTickMarks(true);
        vitesse.setShowTickLabels(true);
        vitesse.setMajorTickUnit(0.25f);
        vitesse.setBlockIncrement(0.1f);
        vitesse.setTranslateX(20);
        vitesse.setTranslateY(50);
        graphe = new CheckBox("Afficher graphe");
        graphe.setTranslateX(200);
        collisions = new CheckBox("Activer les collisions");
        collisions.setTranslateX(200);
        collisions.setTranslateY(20);
        rayon = new CheckBox("Activer le rayon");
        rayon.setTranslateX(200);
        rayon.setTranslateY(40);

        this.getChildren().addAll(supprimerObstacleButton, validerObstacleButton, addObstacleButton, addPersonButton, rayon, collisions, graphe, vitesse, playButton, pauseButton, clearButton);
    }

    //Design des boutons prédéfinis
    public Button createButton(String text, int position){
        Button b = new Button(text);
        b.setMinHeight(30);
        b.setTranslateX(position);
        b.setStyle("-fx-border-color: white; -fx-border-width: 5px;");
        return b;
    }

    // Permet de changer la visibilité de certains boutons pour certaines options tels que la creation d'obstacle.
    public void visibility(boolean isVisible){
        playButton.setVisible(isVisible);
        pauseButton.setVisible(isVisible);
        clearButton.setVisible(isVisible);
        vitesse.setVisible(isVisible);
        rayon.setVisible(isVisible);
        collisions.setVisible(isVisible);
        graphe.setVisible(isVisible);
        addPersonButton.setVisible(isVisible);
        supprimerObstacleButton.setVisible(isVisible);
        validerObstacleButton.setVisible(!isVisible);
        addObstacleButton.setVisible(isVisible);
    }

    public Button getAddObstacleButton(){
        return addObstacleButton;
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

    public Button getAddPersonButton(){
        return addPersonButton;
    }

    public Button getValiderObstacleButton(){
        return validerObstacleButton;
    }
    public Button getSupprimerObstacleButton(){
        return supprimerObstacleButton;
    }
}
