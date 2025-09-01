package csc180.rodriguez.g.grodriguezfinal.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.layout.BorderPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class HelpController {
    @FXML
    private BorderPane helpPane;
    @FXML
    private TextArea helpText;
    @FXML
    private void initialize() {
        helpText.setEditable(false);
        helpText.setWrapText(true);

        try(InputStream is = getClass().getResourceAsStream("/Help.txt");
            BufferedReader br = new BufferedReader(new InputStreamReader(is))) {
            StringBuilder sb = new StringBuilder();
            String line;
            while((line = br.readLine()) != null ) {
                sb.append(line).append("\n");
            }
            helpText.setText(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    @FXML
    private void onExit() {
        Stage stage = (Stage)helpPane.getScene().getWindow();
        stage.close();
    }
}
