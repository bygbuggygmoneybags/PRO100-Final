package csc180.rodriguez.g.grodriguezfinal.models;

import java.util.List;

public interface PlaylistFunction {
    void createPlaylist(Playlists playlist);
    void removePlaylist(Playlists playlist);
    Playlists getPlaylist(int id);
    List<Playlists> getAllPlaylists();
}
