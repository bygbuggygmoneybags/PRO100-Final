package csc180.rodriguez.g.grodriguezfinal.models;

public interface SongFunction {
    void addSong(Songs song);
    void removeSong(String name);
    Songs getSong(String name);
    void getAllSongs();
}
