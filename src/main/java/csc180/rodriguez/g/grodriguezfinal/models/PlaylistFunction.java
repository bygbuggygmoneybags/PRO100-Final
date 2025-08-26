package csc180.rodriguez.g.grodriguezfinal.models;

public interface PlaylistFunction {
    void createPlaylist(Playlists playlist);
    void removePlaylist(String name);
    Playlists getPlaylist(String name);
    void getAllPlaylists();
}
