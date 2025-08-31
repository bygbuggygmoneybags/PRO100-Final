package csc180.rodriguez.g.grodriguezfinal.models;

import javafx.scene.control.ListView;


public class PlaylistSongs {
    private int playlistSongsID;
    private int playlistID;
    private int songID;
    private ListView<Songs> songs;

    public int getPlaylistSongsID() {
        return playlistSongsID;
    }

    public void setPlaylistSongsID(int playlistSongsID) {
        this.playlistSongsID = playlistSongsID;
    }

    public int getPlaylistID() {
        return playlistID;
    }

    public void setPlaylistID(int playlistID) {
        this.playlistID = playlistID;
    }

    public int getSongID() {
        return songID;
    }

    public void setSongID(int songID) {
        this.songID = songID;
    }

    public ListView<Songs> getSongs() {
        return songs;
    }

    public void setSongs(ListView<Songs> songs) {
        this.songs = songs;
    }

    public PlaylistSongs(int playlistID, ListView<Songs> songs) {
        setPlaylistID(playlistID);
        setSongs(songs);
    }

    public PlaylistSongs(int playlistID, int songID) {
        setPlaylistID(playlistID);
        setSongID(songID);
    }

    public PlaylistSongs(int playlistID, int songID, ListView<Songs> songs) {
        setPlaylistID(playlistID);
        setSongID(songID);
        setSongs(songs);
    }

}
