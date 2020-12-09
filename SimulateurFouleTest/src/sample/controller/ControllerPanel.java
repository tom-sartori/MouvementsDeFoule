package sample.controller;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;

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
    Text titre1, titre2, titre3;

    public ControllerPanel(){
        this.minHeight(40);
        titre1 = new Text("SIMULATION");
        titre1.setTranslateX(20);
        titre1.setTranslateY(-10);
        playButton = createButton("", 20);
        Image imgPlay = new Image("sample/img/play.png");
        ImageView viewPlay = new ImageView(imgPlay);
        viewPlay.setFitHeight(20);
        viewPlay.setPreserveRatio(true);
        playButton.setGraphic(viewPlay);
        pauseButton = createButton("", 70);
        Image imgPause = new Image("sample/img/pause.png");
        ImageView viewPause = new ImageView(imgPause);
        viewPause.setFitHeight(20);
        viewPause.setPreserveRatio(true);
        pauseButton.setGraphic(viewPause);
        clearButton = createButton("", 120);
        Image imgClear = new Image("sample/img/clear.png");
        ImageView viewClear = new ImageView(imgClear);
        viewClear.setFitHeight(20);
        viewClear.setPreserveRatio(true);
        clearButton.setGraphic(viewClear);
        titre3 = new Text("OBJETS");
        titre3.setTranslateX(500);
        titre3.setTranslateY(-10);
        addPersonButton = createButton("Ajouter", 500);
        Image imgPerson = new Image("sample/img/person.png");
        ImageView viewPerson = new ImageView(imgPerson);
        viewPerson.setFitHeight(20);
        viewPerson.setPreserveRatio(true);
        addPersonButton.setGraphic(viewPerson);
        addObstacleButton = createButton("Ajouter", 500);
        addObstacleButton.setTranslateY(50);
        Image imgObstacle = new Image("sample/img/obstacle.png");
        ImageView viewObstacle = new ImageView(imgObstacle);
        viewObstacle.setFitHeight(20);
        viewObstacle.setPreserveRatio(true);
        addObstacleButton.setGraphic(viewObstacle);
        validerObstacleButton = createButton("Valider", 450);
        supprimerObstacleButton = createButton("Supprimer", 600);
        Image imgObstacle2 = new Image("sample/img/obstacle.png");
        ImageView viewObstacle2 = new ImageView(imgObstacle2);
        viewObstacle2.setFitHeight(20);
        viewObstacle2.setPreserveRatio(true);
        supprimerObstacleButton.setGraphic(viewObstacle2);
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
        titre2 = new Text("PARAMETRES");
        titre2.setTranslateX(250);
        titre2.setTranslateY(-10);
        graphe = new CheckBox("Afficher graphe");
        graphe.setTranslateX(250);
        collisions = new CheckBox("Activer les collisions");
        collisions.setTranslateX(250);
        collisions.setTranslateY(20);
        rayon = new CheckBox("Activer le rayon");
        rayon.setTranslateX(250);
        rayon.setTranslateY(40);

        this.getChildren().addAll(titre1, titre2, titre3, supprimerObstacleButton, validerObstacleButton, addObstacleButton, addPersonButton, rayon, collisions, graphe, vitesse, playButton, pauseButton, clearButton);
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
        titre1.setVisible(isVisible);
        titre2.setVisible(isVisible);
        titre3.setVisible(isVisible);
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
