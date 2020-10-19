package sample;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ToolBar;

public class Controller extends Parent {
    Button playButton;
    Button pauseButton;
    Button clearButton;
    Label statusLabel;

    public Controller(){
        playButton = new Button("Play");
        pauseButton = new Button("Pause");
        clearButton = new Button("Clear");
        this.minHeight(40);
        playButton.setTranslateX(20);
        playButton.setMinWidth(40);
        playButton.setStyle("-fx-border-color: white; -fx-border-width: 5px;");
        pauseButton.setTranslateX(70);
        pauseButton.setMinWidth(40);
        pauseButton.setStyle("-fx-border-color: white; -fx-border-width: 5px;");
        clearButton.setTranslateX(130);
        clearButton.setMinWidth(40);
        clearButton.setStyle("-fx-border-color: white; -fx-border-width: 5px;");

        statusLabel = new Label("Status : NOT RUNNING");
        statusLabel.setStyle("-fx-text-fill: red");

        this.getChildren().addAll(statusLabel,playButton, pauseButton, clearButton);
    }

    public static void createPersonne(double x, double y, Salle salle){
        Personne personne = new Personne(x, y);
        salle.addPersonne(personne);
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
    public Label getStatusLabel(){
        return statusLabel;
    }

    public void setStatusLabel(boolean isRunning){
        if(isRunning){
            statusLabel.setText("Status : RUNNING");
            statusLabel.setStyle("-fx-text-fill: green");
        } else{
            statusLabel.setText("Status : NOT RUNNING");
            statusLabel.setStyle("-fx-text-fill: red");
        }
    }
}
