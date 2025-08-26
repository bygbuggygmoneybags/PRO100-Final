package csc180.rodriguez.g.grodriguezfinal.controllers;

import csc180.rodriguez.g.grodriguezfinal.DBConnect;
import csc180.rodriguez.g.grodriguezfinal.models.*;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;

public class PlaylistController implements PlaylistFunction, SongFunction, PlaylistSongsFunction {
    @FXML
    private BorderPane toolBarPane;
    @FXML
    private ListView<Playlists> playlistsView;
    @FXML
    private ListView<String> songsInPlaylist;
    @FXML
    private ToolBar toolBar;
    @FXML
    private Button create;
    @FXML
    private Button edit;
    @FXML
    private Button view;
    @FXML
    private Button help;
    @FXML
    private Button about;
    @FXML
    private ContextMenu createMenu;
    @FXML
    private MenuItem createPlaylistButton;
    @FXML
    private void clickCreate(){
        create.setOnAction(e -> {
            if(createMenu.isShowing()) {
                createMenu.hide();
            } else {
                createMenu.show(create, Side.BOTTOM,0,0);
            }
        });
    }

    @Override
    public void createPlaylist(Playlists playlist) {
        String sql = "INSERT INTO Playlists(Name,Description) VALUES (?,?)";
        try (Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, playlist.getName());
            stmt.setString(2, playlist.getDescription());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickCreatePlaylist() {
        createPlaylistButton.setOnAction(e -> {
            int indexToAdd = 0;
            String name = "";
            String description = "";

            TextInputDialog namePopup = new TextInputDialog();
            namePopup.setTitle("Create Playlist");
            namePopup.setHeaderText("Name your playlist:");
            namePopup.setContentText("Name: ");
            Optional<String> result = namePopup.showAndWait();
            if(result.isPresent()) {
                name = result.get();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setHeaderText("Input error");
                dialog.setContentText("Input was cancelled by user");
            }
            namePopup.close();

            TextInputDialog descPopup = new TextInputDialog();
            descPopup.setTitle("Description");
            descPopup.setHeaderText("Type a small description for your playlist: ");
            descPopup.setContentText("Description: ");
            Optional<String> desc = descPopup.showAndWait();
            if(desc.isPresent()) {
                description = desc.get();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setHeaderText("Input error");
                dialog.setContentText("Input was cancelled by user");
            }
            descPopup.close();
            createPlaylist(new Playlists(name, description));
            playlistsView.getItems().add(indexToAdd, new Playlists(name, description));
            indexToAdd++;
        });
    }

    @FXML
    private ContextMenu editMenu;
    @FXML
    private MenuItem add;
    @FXML
    private MenuItem removeSong;
    @FXML
    public void initialize() {
        songsInPlaylist.setVisible(false);
        add.setDisable(true);
        removeSong.setDisable(true);

        playlistsView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasPlaylist = newValue != null;
            add.setDisable(!hasPlaylist);

            boolean hasSong = !songsInPlaylist.getSelectionModel().isEmpty();
            removeSong.setDisable(!hasPlaylist || !hasSong);
        });

        songsInPlaylist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasPlaylist = playlistsView.getSelectionModel().getSelectedItem() != null;
            removeSong.setDisable(!hasPlaylist);
        });
    }
    @FXML
    private MenuItem remove;
    @FXML
    private void clickEdit() {
        edit.setOnAction(e -> {
            if(editMenu.isShowing()) {
                editMenu.hide();
            } else {
                editMenu.show(edit, Side.BOTTOM,0,0);
            }
        });
    }

    @Override
    public void addSong(Songs song) {
        String sql = "INSERT INTO Songs(Title,Artist,Album,Genre,Length) VALUES (?,?,?,?,?)";
        try(Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,song.getTitle());
            stmt.setString(2,song.getArtist());
            stmt.setString(3,song.getAlbum());
            stmt.setString(4,song.getGenre());
            stmt.setString(5,song.getLength());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSongToPlaylist(int songId, int playlistId) {
        String sql = "INSERT INTO PlaylistSongs(PlaylistID,SongID) VALUES (?,?)";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1,playlistId);
            stmt.setInt(2,songId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void removeSongFromPlaylist(int songId, int playlistId) {
        String sql = "DELETE FROM PlaylistSongs WHERE PlaylistID=? AND SongID=?";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1,playlistId);
            stmt.setInt(2,songId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickAddSong() {
        add.setOnAction(e -> {
            String name = "";
            String artist = "";
            String album = "";
            String genre = "";
            String length = "";

            TextInputDialog namePopup = new TextInputDialog();
            namePopup.setTitle("Add song");
            namePopup.setHeaderText("Type the name of the song:");
            namePopup.setContentText("Name: ");
            Optional<String> result = namePopup.showAndWait();
            if(result.isPresent()) {
                name = result.get();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setHeaderText("Input error");
                dialog.setContentText("Input was cancelled by user");
            }
            namePopup.close();

            TextInputDialog artPopup = new TextInputDialog();
            artPopup.setTitle("Artist");
            artPopup.setHeaderText("Type the artist's name:");
            artPopup.setContentText("Artist: ");
            Optional<String> art = artPopup.showAndWait();
            if(art.isPresent()) {
                artist = art.get();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setHeaderText("Input error");
                dialog.setContentText("Input was cancelled by user");
            }
            artPopup.close();

            TextInputDialog albumPopup = new TextInputDialog();
            albumPopup.setTitle("Album");
            albumPopup.setHeaderText("Type the album's name(leave empty if song is a single):");
            albumPopup.setContentText("Album: ");
            Optional<String> albums = albumPopup.showAndWait();
            if(albums.isPresent()) {
                album = albums.get();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setHeaderText("Input error");
                dialog.setContentText("Input was cancelled by user");
            }
            albumPopup.close();

            TextInputDialog genrePopup = new TextInputDialog();
            genrePopup.setTitle("Genre");
            genrePopup.setHeaderText("What genre is the song:");
            genrePopup.setContentText("Genre: ");
            Optional<String> genres = genrePopup.showAndWait();
            if(genres.isPresent()) {
                genre = genres.get();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setHeaderText("Input error");
                dialog.setContentText("Input was cancelled by user");
            }
            genrePopup.close();

            TextInputDialog lengthPopup = new TextInputDialog();
            lengthPopup.setTitle("Length");
            lengthPopup.setHeaderText("How long is the song?:");
            lengthPopup.setContentText("Length: ");
            Optional<String> lengths = lengthPopup.showAndWait();
            if(lengths.isPresent()) {
                length = lengths.get();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setHeaderText("Input error");
                dialog.setContentText("Input was cancelled by user");
            }
            lengthPopup.close();
            addSong(new Songs(name, artist, album, genre, length));
            songsInPlaylist.getItems().add(name);
            addSongToPlaylist(getSong(name).getId(),getPlaylist(playlistsView.getSelectionModel().getSelectedItem().getName()).getId());
            songsInPlaylist.setVisible(true);
        });
    }

    @Override
    public void removeSong(String name) {
        String sql = "DELETE FROM Songs WHERE Title = ?";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickRemoveSong() {
        removeSong.setOnAction(e -> {
            String name = "";

            TextInputDialog namePopup = new TextInputDialog();
            namePopup.setTitle("Remove Song");
            namePopup.setHeaderText("Type the name of the song you wish to remove:");
            namePopup.setContentText("Name: ");
            Optional<String> result = namePopup.showAndWait();
            if (result.isPresent()) {
                name = result.get();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setHeaderText("Input error");
                dialog.setContentText("Input was cancelled by user");
            }
            namePopup.close();
            removeSong(name);
            songsInPlaylist.getItems().remove(name);
            removeSongFromPlaylist(getSong(name).getId(),getPlaylist(playlistsView.getSelectionModel().getSelectedItem().getName()).getId());
        });
    }

    @Override
    public void removePlaylist(String name) {
        String sql = "DELETE FROM Playlists WHERE Name = ?";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,name);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickRemovePlaylist() {
        remove.setOnAction(e -> {
            String name = "";

            TextInputDialog namePopup = new TextInputDialog();
            namePopup.setTitle("Remove Playlist");
            namePopup.setHeaderText("Type the name of the playlist you wish to remove:");
            namePopup.setContentText("Name: ");
            Optional<String> result = namePopup.showAndWait();
            if(result.isPresent()) {
                name = result.get();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setHeaderText("Input error");
                dialog.setContentText("Input was cancelled by user");
            }
            namePopup.close();
            removePlaylist(name);
            playlistsView.getItems().remove(playlistsView.getSelectionModel().getSelectedItem());
        });
    }

    @FXML
    private ContextMenu viewMenu;
    @FXML
    private MenuItem showSong;
    @FXML
    private MenuItem showSongs;
    @FXML
    private MenuItem showPlaylist;
    @FXML
    private MenuItem showPlaylists;
    @FXML
    private void clickView() {
        view.setOnAction(e -> {
            if(viewMenu.isShowing()) {
                viewMenu.hide();
            } else {
                viewMenu.show(view, Side.BOTTOM,0, 0);
            }
        });
    }

    @Override
    public Songs getSong(String name) {
        String sql = "SELECT * FROM Songs WHERE Title = ?";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,name);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    String songTitle = rs.getString("Title");
                    return new Songs(songTitle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void clickShowSong() {
        showSong.setOnAction(e -> {
            String name = "";

            TextInputDialog namePopup = new TextInputDialog();
            namePopup.setTitle("Show Song");
            namePopup.setHeaderText("Type the name of the song you wish to search:");
            namePopup.setContentText("Name: ");
            Optional<String> result = namePopup.showAndWait();
            if(result.isPresent()) {
                name = result.get();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setHeaderText("Input error");
                dialog.setContentText("Input was cancelled by user");
            }
            namePopup.close();
            getSong(name);
        });
    }

    @Override
    public void getAllSongs() {
        String sql = "SELECT * FROM Songs";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickShowSongs() {
        showSongs.setOnAction(e -> {
            getAllSongs();
        });
    }

    @Override
    public Playlists getPlaylist(String name) {
        String sql = "SELECT * FROM Playlists WHERE Name = ?";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,name);
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    String playlistName = rs.getString("Name");
                    return new Playlists(playlistName);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void clickShowPlaylist() {
        showPlaylist.setOnAction(e -> {
            String name = "";

            TextInputDialog namePopup = new TextInputDialog();
            namePopup.setTitle("Show Playlist");
            namePopup.setHeaderText("Type the name of the playlist you wish to search:");
            namePopup.setContentText("Name: ");
            Optional<String> result = namePopup.showAndWait();
            if(result.isPresent()) {
                name = result.get();
            } else {
                Dialog<String> dialog = new Dialog<>();
                dialog.setTitle("Error");
                dialog.setHeaderText("Input error");
            }
            namePopup.close();
            getPlaylist(name);
        });
    }

    @Override
    public void getAllPlaylists() {
        String sql = "SELECT * FROM Playlists";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickShowPlaylists() {
        showPlaylists.setOnAction(e -> getAllPlaylists());
    }
}