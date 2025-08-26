package csc180.rodriguez.g.grodriguezfinal.views;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class PlaylistGUI extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(PlaylistGUI.class.getResource("/MainView.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 1000, 850);
        stage.setTitle("Playlist Manager");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}