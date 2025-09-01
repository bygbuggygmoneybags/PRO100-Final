package csc180.rodriguez.g.grodriguezfinal.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Hyperlink;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.awt.*;
import java.net.URI;

public class AboutController {
    @FXML
    private BorderPane aboutPane;
    @FXML
    private Hyperlink link;
    @FXML
    private void onExit() {
        Stage stage = (Stage) aboutPane.getScene().getWindow();
        stage.close();
    }
    @FXML
    private void onLink() {
        try {
            Desktop.getDesktop().browse(new URI("https://github.com/bygbuggygmoneybags/PRO100-Final"));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
}
