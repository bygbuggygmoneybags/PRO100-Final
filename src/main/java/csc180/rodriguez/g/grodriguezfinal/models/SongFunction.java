package csc180.rodriguez.g.grodriguezfinal.models;

import java.util.List;

public interface SongFunction {
    void addSong(Songs song);
    void removeSong(Songs song);
    Songs getSong(int id);
    List<Songs> getAllSongs();
}
