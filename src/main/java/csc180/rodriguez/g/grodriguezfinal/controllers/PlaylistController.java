package csc180.rodriguez.g.grodriguezfinal.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class PlaylistController {
    @FXML
    private Label welcomeText;

    @FXML
    protected void onHelloButtonClick() {
        welcomeText.setText("Welcome to JavaFX Application!");
    }
}