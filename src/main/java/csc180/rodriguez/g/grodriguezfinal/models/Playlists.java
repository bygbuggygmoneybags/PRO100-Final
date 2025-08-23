package csc180.rodriguez.g.grodriguezfinal.models;

public class Playlists {
    private int id;
    private String name;
    private String description;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Playlists(int id, String name, String description) {
        setId(id);
        setName(name);
        setDescription(description);
    }

    @Override
    public String toString() {
        return name + ", " + description;
    }
}
