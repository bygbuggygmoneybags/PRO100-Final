package csc180.rodriguez.g.grodriguezfinal.models;

import java.util.List;

public interface PlaylistSongsFunction {
    void addSongToPlaylist(PlaylistSongs ps);
    List<Songs> getSongs(Playlists playlist);
    void removeSongFromPlaylist(int songId,int playlistId);
}
