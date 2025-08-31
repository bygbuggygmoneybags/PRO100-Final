package csc180.rodriguez.g.grodriguezfinal.controllers;

import csc180.rodriguez.g.grodriguezfinal.models.Playlists;
import csc180.rodriguez.g.grodriguezfinal.models.Songs;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.util.List;

public class ViewController {
    private Playlists playlist;
    private Songs song;

    @FXML
    private ListView<Songs> songsInPlaylist;
    @FXML
    private ListView<Songs> songsListView;
    @FXML
    private ListView<Playlists> playlistsListView;
    @FXML
    private BorderPane viewPane;
    @FXML
    private Label songLabel;
    @FXML
    private Label playlistLabel;

    public void setPlaylist(Playlists playlist, List<Songs> songs) {
        this.playlist = playlist;
        playlistLabel.setText(playlist.toString());
        songsInPlaylist.getItems().setAll(songs);

        viewPane.setCenter(playlistLabel);
        viewPane.setBottom(songsInPlaylist);
    }

    public void setSong(Songs song) {
        this.song = song;
        songLabel.setText(song.toString());

        viewPane.setCenter(songLabel);
    }

    public void setSongsListView(List<Songs> songs) {
        songsListView.getItems().setAll(songs);

        viewPane.setCenter(songsListView);
    }

    public void setPlaylistsListView(List<Playlists> playlists) {
        playlistsListView.getItems().setAll(playlists);

        viewPane.setCenter(playlistsListView);
    }

    @FXML
    public void onExit() {
        Stage stage = (Stage) viewPane.getScene().getWindow();
        stage.close();
    }
}
