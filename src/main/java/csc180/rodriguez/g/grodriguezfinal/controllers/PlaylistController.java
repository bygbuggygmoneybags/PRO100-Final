package csc180.rodriguez.g.grodriguezfinal.controllers;

import csc180.rodriguez.g.grodriguezfinal.DBConnect;
import csc180.rodriguez.g.grodriguezfinal.models.*;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Side;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PlaylistController implements PlaylistFunction, SongFunction, PlaylistSongsFunction {
    @FXML
    private BorderPane toolBarPane;
    @FXML
    private ListView<Playlists> playlistsView;
    @FXML
    private ListView<Songs> songsInPlaylist;
    @FXML
    private ListView<Songs> songsPlaylist;
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
        create.setOnMouseClicked(e -> {
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
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1, playlist.getName());
            stmt.setString(2, playlist.getDescription());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                playlist.setId(rs.getInt(1));
            }
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
            } else if(desc.isEmpty()) {
                description = "No description";
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
        songsPlaylist.setVisible(false);
        add.setDisable(true);
        removeSong.setDisable(true);

        playlistsView.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasPlaylist = newValue != null;
            add.setDisable(!hasPlaylist);
            if(hasPlaylist) {
                showSongsInPlaylist(newValue);
            }

            boolean hasSong = !songsPlaylist.getSelectionModel().isEmpty();
            removeSong.setDisable(!hasPlaylist || !hasSong);
        });

        songsPlaylist.getSelectionModel().selectedItemProperty().addListener((observable, oldValue, newValue) -> {
            boolean hasPlaylist = playlistsView.getSelectionModel().getSelectedItem() != null;
            removeSong.setDisable(!hasPlaylist);
        });
    }
    @FXML
    private MenuItem remove;
    @FXML
    private void clickEdit() {
        edit.setOnMouseClicked(e -> {
            if(editMenu.isShowing()) {
                editMenu.hide();
            } else {
                editMenu.show(edit, Side.BOTTOM,0,0);
            }
        });
    }

    private void showSongsInPlaylist(Playlists playlist) {
        List<Songs> songsList = getSongs(getPlaylist(playlist.getName()));
        songsPlaylist.getItems().setAll(songsList);

    }

    @Override
    public void addSong(Songs song) {
        String sql = "INSERT INTO Songs(Title,Artist,Album,Genre,Length) VALUES (?,?,?,?,?)";
        try(Connection conn = DBConnect.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setString(1,song.getTitle());
            stmt.setString(2,song.getArtist());
            stmt.setString(3,song.getAlbum());
            stmt.setString(4,song.getGenre());
            stmt.setString(5,song.getLength());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if(rs.next()) {
                song.setId(rs.getInt(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addSongToPlaylist(PlaylistSongs ps) {
        String sql = "INSERT INTO PlaylistSongs(PlaylistID,SongID) VALUES (?,?)";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1,ps.getPlaylistID());
            stmt.setInt(2,ps.getSongID());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Songs> getSongs(Playlists playlist) {
        List<Songs> songs = new ArrayList<>();
        String sql = "SELECT * " + "FROM Songs s " + "JOIN PlaylistSongs ps on s.SongID = ps.songID "
                + "WHERE ps.playlistID = ?";
        try(Connection conn=DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, playlist.getId());
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                Songs song = new Songs(
                        rs.getString("Title"),
                        rs.getString("Artist"),
                        rs.getString("Album"),
                        rs.getString("Genre"),
                        rs.getString("Length")
                );
                song.setId(rs.getInt("SongID"));
                songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
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

            } else if(albums.isEmpty()) {
                album = "Single";
            }else {
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
            songsInPlaylist.getItems().add(new Songs(name, artist, album, genre, length));
            songsPlaylist.getItems().add(new Songs(name, artist, album, genre, length));
            addSongToPlaylist(new PlaylistSongs(getPlaylist(playlistsView.getSelectionModel().getSelectedItem().getName()).getId(),getSong(name).getId()));
            songsPlaylist.setVisible(true);
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
            removeSong(songsPlaylist.getSelectionModel().getSelectedItem().getTitle());
            songsInPlaylist.getItems().remove(songsPlaylist.getSelectionModel().getSelectedItem().getTitle());
            removeSongFromPlaylist(songsPlaylist.getSelectionModel().getSelectedItem().getId(),playlistsView.getSelectionModel().getSelectedItem().getId());
            songsPlaylist.getItems().remove(songsPlaylist.getSelectionModel().getSelectedItem());
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
            removePlaylist(playlistsView.getSelectionModel().getSelectedItem().getName());
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
        view.setOnMouseClicked(e -> {
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
            stmt.setString(1,name.trim());
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    String songTitle = rs.getString("Title");
                    String artist = rs.getString("Artist");
                    String album = rs.getString("Album");
                    String genre = rs.getString("Genre");
                    String length = rs.getString("Length");
                    Songs song = new Songs(songTitle,artist,album,genre,length);
                    song.setId(rs.getInt("SongID"));
                    return song;
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @FXML
    private void clickShowSong() {
        showSong.setOnAction (e -> {
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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewWindow.fxml"));
                Parent root = loader.load();

                ViewController viewController = loader.getController();
                viewController.setPlaylist(playlistsView.getSelectionModel().getSelectedItem(),getSongs(playlistsView.getSelectionModel().getSelectedItem()));
                viewController.setSong(getSong(name));

                Stage stage = new Stage();
                stage.setTitle("Viewing Searched Song");
                stage.setScene(new Scene(root,320,550));
                stage.show();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });
    }

    @Override
    public List<Songs> getAllSongs() {
        List<Songs> songs = new ArrayList<>();
        String sql = "SELECT * FROM Songs";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();
            while(rs.next()) {
                int songId = rs.getInt("SongID");
                String title = rs.getString("Title");
                String artist = rs.getString("Artist");
                String album = rs.getString("Album");
                String genre = rs.getString("Genre");
                String length = rs.getString("Length");

                Songs song = new Songs(title,artist,album,genre,length);
                song.setId(songId);

                songs.add(song);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return songs;
    }

    @FXML
    private void clickShowSongs() {

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewWindow.fxml"));
            Parent root = loader.load();

            ViewController viewController = loader.getController();
            viewController.setSongsListView(getAllSongs());

            Stage stage = new Stage();
            stage.setTitle("Viewing all Songs");
            stage.setScene(new Scene(root,320,550));
            stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }

    @Override
    public Playlists getPlaylist(String name) {
        String sql = "SELECT * FROM Playlists WHERE Name = ?";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1,name.trim());
            try(ResultSet rs = stmt.executeQuery()) {
                if(rs.next()) {
                    int playlistId = rs.getInt("PlaylistID");
                    String playlistName = rs.getString("Name");
                    String playlistDescription = rs.getString("Description");

                    Playlists playlist = new Playlists(playlistName,playlistDescription);
                    playlist.setId(playlistId);
                    return playlist;
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
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewWindow.fxml"));
                Parent root = loader.load();

                ViewController viewController = loader.getController();
                viewController.setPlaylist(getPlaylist(name),getSongs(getPlaylist(name)));

                Stage stage = new Stage();
                stage.setTitle("Viewing Searched Playlist");
                stage.setScene(new Scene(root, 320,550));
                stage.show();
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        });
    }

    @Override
    public List<Playlists> getAllPlaylists() {
        List<Playlists> playlists = new ArrayList<>();
        String sql = "SELECT * FROM Playlists";
        try(Connection conn = DBConnect.getConnection();
            PreparedStatement stmt = conn.prepareStatement(sql)) {
            ResultSet rs = stmt.executeQuery();

            while(rs.next()) {
                int playlistId = rs.getInt("PlaylistID");
                String name = rs.getString("Name");
                String description = rs.getString("Description");

                Playlists playlist = new Playlists(name,description);
                playlist.setId(playlistId);

                playlists.add(playlist);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return playlists;
    }

    @FXML
    private void clickShowPlaylists() {
        showPlaylists.setOnAction(e -> {
            try {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/ViewWindow.fxml"));
                Parent root = loader.load();

                ViewController viewController = loader.getController();
                viewController.setPlaylistsListView(getAllPlaylists());

                Stage stage = new Stage();
                stage.setTitle("Viewing all Playlists");
                stage.setScene(new Scene(root, 320, 550));
                stage.show();
            } catch (IOException ioe) {
                ioe.printStackTrace();
            }
        });
    }
}