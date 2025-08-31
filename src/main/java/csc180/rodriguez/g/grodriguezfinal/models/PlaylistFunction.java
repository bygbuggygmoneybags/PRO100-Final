package csc180.rodriguez.g.grodriguezfinal.models;

import java.util.List;

public interface PlaylistFunction {
    void createPlaylist(Playlists playlist);
    void removePlaylist(String name);
    Playlists getPlaylist(String name);
    List<Playlists> getAllPlaylists();
}
