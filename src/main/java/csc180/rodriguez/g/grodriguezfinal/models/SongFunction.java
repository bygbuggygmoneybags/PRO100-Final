package csc180.rodriguez.g.grodriguezfinal.models;

import java.util.List;

public interface SongFunction {
    void addSong(Songs song);
    void removeSong(String name);
    Songs getSong(String name);
    List<Songs> getAllSongs();
}
