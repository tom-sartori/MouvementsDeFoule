package controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;


public class ControllerPanel extends Parent {
    Button playButton; //lance la simulation
    Button pauseButton; //pause la simulation
    Button clearButton; //stop la simulation et vide la salle
    Button addPersonButton; //ajoute des personnes
    Button addObstacleButton; //ajoute des obstacles
    Button validerObstacleButton; //valide la création d'un obstacle
    Button supprimerObstacleButton; //supprime un obstacle
    Slider vitesse; //change la vitesse des personnes
    ToggleGroup groupeGraphe; //Groupe des radioButton de tous les graphes
    RadioButton graphe;
    RadioButton grapheDiagonale;
    RadioButton grapheCartesien;
    RadioButton graphePhysique;
    RadioButton enleverGraphe;
    CheckBox collisions; //active/desactive collisions
    CheckBox rayon; //active/desactive rayon
    Text titre1, titre2, titre3;
    Label timerDisplay; 
    int timer=0;
    Timeline loopTimer; //compteur pour le timer

    public ControllerPanel(){
        this.minHeight(40);
        titre1 = new Text("SIMULATION");
        titre1.setTranslateX(20);
        titre1.setTranslateY(-10);
        playButton = createButton("", 20);
        Image imgPlay = new Image("img/play.png");
        ImageView viewPlay = new ImageView(imgPlay);
        viewPlay.setFitHeight(20);
        viewPlay.setPreserveRatio(true);
        playButton.setGraphic(viewPlay);
        pauseButton = createButton("", 70);
        Image imgPause = new Image("img/pause.png");
        ImageView viewPause = new ImageView(imgPause);
        viewPause.setFitHeight(20);
        viewPause.setPreserveRatio(true);
        pauseButton.setGraphic(viewPause);
        clearButton = createButton("", 120);
        Image imgClear = new Image("img/clear.png");
        ImageView viewClear = new ImageView(imgClear);
        viewClear.setFitHeight(20);
        viewClear.setPreserveRatio(true);
        clearButton.setGraphic(viewClear);
        timerDisplay = new Label("00:00");
        loopTimer = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>(){
            public void handle(ActionEvent arg) {			                	
                timer++;
                int minutes, seconds;
                String minText, secText;
                minutes = timer/60;
                minText = ""+minutes;
                if(minutes<10) minText="0"+minText;
                seconds = timer%60;
                secText = ""+seconds;
                if(seconds<10) secText="0"+secText;
                timerDisplay.setText(minText+":"+secText);
            }
          }));
        loopTimer.setCycleCount(Timeline.INDEFINITE);
        timerDisplay.setTranslateX(120);
        timerDisplay.setTranslateY(-23);

        titre3 = new Text("OBJETS");
        titre3.setTranslateX(650);
        titre3.setTranslateY(-10);
        addPersonButton = createButton("Ajouter", 650);
        Image imgPerson = new Image("img/person.png");
        ImageView viewPerson = new ImageView(imgPerson);
        viewPerson.setFitHeight(20);
        viewPerson.setPreserveRatio(true);
        addPersonButton.setGraphic(viewPerson);
        addObstacleButton = createButton("Ajouter", 650);
        addObstacleButton.setTranslateY(50);
        Image imgObstacle = new Image("img/obstacle.png");
        ImageView viewObstacle = new ImageView(imgObstacle);
        viewObstacle.setFitHeight(20);
        viewObstacle.setPreserveRatio(true);
        addObstacleButton.setGraphic(viewObstacle);
        validerObstacleButton = createButton("Valider", 450);
        supprimerObstacleButton = createButton("Supprimer", 750);
        Image imgObstacle2 = new Image("img/obstacle.png");
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
        groupeGraphe = new ToggleGroup();
        graphe = new RadioButton("Afficher graphe court");
        graphe.setToggleGroup(groupeGraphe);
        grapheDiagonale = new RadioButton("Afficher graphe diagonale");
        grapheDiagonale.setToggleGroup(groupeGraphe);
        grapheCartesien = new RadioButton("Afficher graphe cartésien");
        grapheCartesien.setToggleGroup(groupeGraphe);
        graphePhysique = new RadioButton("Afficher graphe physique");
        graphePhysique.setToggleGroup(groupeGraphe);
        enleverGraphe = new RadioButton("Ne pas afficher graphe");
        enleverGraphe.setSelected(true);
        enleverGraphe.setToggleGroup(groupeGraphe);
        graphe.setTranslateX(250);
        grapheDiagonale.setTranslateX(250);
        grapheDiagonale.setTranslateY(20);
        grapheCartesien.setTranslateX(250);
        grapheCartesien.setTranslateY(40);
        graphePhysique.setTranslateX(250);
        graphePhysique.setTranslateY(60);
        enleverGraphe.setTranslateX(250);
        enleverGraphe.setTranslateY(80);
        collisions = new CheckBox("Activer les collisions");
        collisions.setTranslateX(450);
        rayon = new CheckBox("Activer le rayon");
        rayon.setTranslateX(450);
        rayon.setTranslateY(20);

        this.getChildren().addAll(timerDisplay, titre1, titre2, titre3, supprimerObstacleButton, validerObstacleButton, addObstacleButton, addPersonButton, collisions, rayon, enleverGraphe, graphe, grapheDiagonale, grapheCartesien, graphePhysique, vitesse, playButton, pauseButton, clearButton);
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
        timerDisplay.setVisible(isVisible);
        playButton.setVisible(isVisible);
        pauseButton.setVisible(isVisible);
        clearButton.setVisible(isVisible);
        vitesse.setVisible(isVisible);
        collisions.setVisible(isVisible);
        rayon.setVisible(isVisible);
        graphe.setVisible(isVisible);
        grapheCartesien.setVisible(isVisible);
        grapheDiagonale.setVisible(isVisible);
        graphePhysique.setVisible(isVisible);
        enleverGraphe.setVisible(isVisible);
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
    public ToggleGroup getGroupeGraphe(){
        return groupeGraphe;
    }

    public RadioButton getGrapheRB(){
        return graphe;
    }

    public RadioButton getGrapheDiagonaleRB(){
        return grapheDiagonale;
    }

    public RadioButton getGrapheCartesienRB(){
        return grapheCartesien;
    }

    public RadioButton getGraphePhysiqueRB(){
        return graphePhysique;
    }

    public RadioButton getEnleverGrapheRB(){
        return enleverGraphe;
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

    public void setLoopTimer(int status){
        if(status==0) loopTimer.play();
        else if(status==1) loopTimer.pause();
        else{
            loopTimer.pause();
            timer = 0;
            timerDisplay.setText("00:00");
        }
    }
}
