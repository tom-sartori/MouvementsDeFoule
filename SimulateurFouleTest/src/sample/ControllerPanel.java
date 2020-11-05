package sample;

import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class ControllerPanel extends Parent {
    Button playButton;
    Button pauseButton;
    Button clearButton;
    Label statusLabel;

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

    public ControllerPanel(){
        this.minHeight(40);
        playButton = createButton("Play", 20);
        pauseButton = createButton("Pause", 70);
        clearButton = createButton("Clear", 130);

        statusLabel = new Label("Status : NOT RUNNING");
        statusLabel.setStyle("-fx-text-fill: red");
        statusLabel.setTranslateX(200);
        this.getChildren().addAll(statusLabel,playButton, pauseButton, clearButton);
    }

    public Button createButton(String text, int position){
        Button b = new Button(text);
        b.setMinHeight(30);
        b.setTranslateX(position);
        b.setStyle("-fx-border-color: white; -fx-border-width: 5px;");
        return b;
    }

}
