package sample.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
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
    CheckBox graphe; //active/desactive affichage graphe
    CheckBox collisions; //active/desactive collisions
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

        this.getChildren().addAll(timerDisplay, titre1, titre2, titre3, supprimerObstacleButton, validerObstacleButton, addObstacleButton, addPersonButton, collisions, graphe, vitesse, playButton, pauseButton, clearButton);
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
