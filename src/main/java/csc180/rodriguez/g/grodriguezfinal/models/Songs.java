package csc180.rodriguez.g.grodriguezfinal.models;

public class Songs {
    private int id;
    private String title;
    private String artist;
    private String album;
    private String genre;
    private String length;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArtist() {
        return artist;
    }

    public void setArtist(String artist) {
        this.artist = artist;
    }

    public String getAlbum() {
        return album;
    }

    public void setAlbum(String album) {
        this.album = album;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getLength() {
        return length;
    }

    public void setLength(String length) {
        this.length = length;
    }

    public Songs(String title) {
        setTitle(title);
    }

    public Songs(String title, String artist, String album, String genre, String length) {
        setTitle(title);
        setArtist(artist);
        setAlbum(album);
        setGenre(genre);
        setLength(length);
    }

    @Override
    public String toString() {
        return  title +
                ", " + artist +
                ", Album: " + album +
                ", Genre: " + genre +
                ", " + length;
    }
}
