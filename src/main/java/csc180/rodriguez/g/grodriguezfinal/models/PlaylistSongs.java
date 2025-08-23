package csc180.rodriguez.g.grodriguezfinal.models;

public class PlaylistSongs {
    private int playlistID;
    private int songID;

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

    public PlaylistSongs(int playlistID, int songID) {
        setPlaylistID(playlistID);
        setSongID(songID);
    }
}
