package csc180.rodriguez.g.grodriguezfinal.controllers;

import csc180.rodriguez.g.grodriguezfinal.models.Playlists;
import csc180.rodriguez.g.grodriguezfinal.models.Songs;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.BorderPane;

public class ViewController {
    private Playlists playlist;
    private Songs song;
    boolean searchedSong = false;
    boolean allSongs = false;
    boolean searchedPlaylist = false;
    boolean allPlaylists = false;
    @FXML
    private ListView<String> songsListView;
    @FXML
    private ListView<Playlists> playlistsListView;
    @FXML
    private BorderPane viewPane;
    @FXML
    private Label songLabel;
    @FXML
    private Label playlistLabel;

    public void setPlaylist(Playlists playlist) {
        this.playlist = playlist;
        searchedPlaylist = true;
    }

    public void setSong(Songs song) {
        this.song = song;
        searchedSong = true;
    }

    public void setSongsListView(ListView<String> songsListView) {
        this.songsListView = songsListView;
        allSongs = true;
    }

    public void setPlaylistsListView(ListView<Playlists> playlistsListView) {
        this.playlistsListView = playlistsListView;
        allPlaylists = true;
    }

    public Playlists getPlaylist() {
        return playlist;
    }

    public Songs getSong() {
        return song;
    }

    public ListView<String> getSongsListView() {
        return songsListView;
    }

    public ListView<Playlists> getPlaylistsListView() {
        return playlistsListView;
    }

    public void setSongLabel(Label songLabel) {
        this.songLabel = songLabel;
    }

    public void setPlaylistLabel(Label playlistLabel) {
        this.playlistLabel = playlistLabel;
    }

    @FXML
    private void initialize() {
        if (searchedSong) {
            setSongLabel(new Label(getSong().toString()));
            viewPane.setCenter(songLabel);
        } else if (allSongs) {
            viewPane.setCenter(getSongsListView());
        } else if (searchedPlaylist) {
            setPlaylistLabel(new Label(getPlaylist().toString()));
            viewPane.setCenter(playlistLabel);
        } else if (allPlaylists) {
            viewPane.setCenter(getPlaylistsListView());
        }
    }
}
