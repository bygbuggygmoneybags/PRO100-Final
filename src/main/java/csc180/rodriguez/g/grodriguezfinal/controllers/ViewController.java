package csc180.rodriguez.g.grodriguezfinal.controllers;

import csc180.rodriguez.g.grodriguezfinal.models.Playlists;
import csc180.rodriguez.g.grodriguezfinal.models.Songs;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

public class ViewController {
    private Playlists playlist;
    private Songs song;

    @FXML
    private ListView<Songs> songsInPlaylist;
    @FXML
    private ListView<Songs> songsListView;
    @FXML
    private ListView<Playlists> playlistsListView;
    @FXML
    private BorderPane viewPane;
    @FXML
    private ToolBar toolBar;
    @FXML
    private Button export;
    @FXML
    private ContextMenu exportMenu;
    @FXML
    private Button exit;
    @FXML
    private Label songLabel;
    @FXML
    private Label playlistLabel;

    public void setPlaylist(Playlists playlist, List<Songs> songs) {
        this.playlist = playlist;
        playlistLabel.setText(playlist.toString());
        songsInPlaylist.getItems().setAll(songs);

        viewPane.setCenter(playlistLabel);
        viewPane.setBottom(songsInPlaylist);
    }

    public void setSong(Songs song) {
        this.song = song;
        songLabel.setText(song.toString());

        viewPane.setCenter(songLabel);
    }

    public void setSongsListView(List<Songs> songs) {
        songsListView.getItems().setAll(songs);

        viewPane.setCenter(songsListView);
    }

    public void setPlaylistsListView(List<Playlists> playlists) {
        playlistsListView.getItems().setAll(playlists);

        viewPane.setCenter(playlistsListView);
    }

    @FXML
    private void onExport() {
        if(exportMenu.isShowing()) {
            exportMenu.hide();
        } else {
            exportMenu.show(export, Side.BOTTOM,0,0);
        }
    }

    private void exportAsHTML(File file) {
        try(PrintWriter pw = new PrintWriter(file, "UTF-8")) {
            pw.println("<html><head><title>Export As HTML</title></head><body>");
            pw.println("<h1>Export As HTML</h1>");

            if(!songsListView.getItems().isEmpty()) {
                pw.println("<h2>Songs</h2><ul>");
                for (Songs song : songsListView.getItems()) {
                    pw.println("<li>" + song.toString() + "</li>");
                }
                pw.println("</ul>");
            }

            if(!playlistsListView.getItems().isEmpty()) {
                pw.println("<h2>Playlists</h2><ul>");
                for (Playlists playlist : playlistsListView.getItems()) {
                    pw.println("<li>" + playlist.toString() + "</li>");

                    List<Songs> songs = new PlaylistController().getSongs(playlist);
                    if(!songs.isEmpty()) {
                        pw.println("<ul>");
                        for (Songs song : songs) {
                            pw.println("<li>" + song.toString() + "</li>");
                        }
                        pw.println("</ul>");
                    } else {
                        pw.println("<p><i>No songs found</i></p>");
                    }
                }
                pw.println("</ul>");
            }
            pw.println("</body></html>");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickExportHTML() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Export As HTML");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("HTML Files", "*.html"));

        File file = fc.showSaveDialog(viewPane.getScene().getWindow());
        if(file != null) {
            exportAsHTML(file);
        }
    }

    private void exportAsPDF(File file) {
        try(PDDocument pd = new PDDocument()) {
            PDPage p = new PDPage();
            pd.addPage(p);

            PDPageContentStream c = new PDPageContentStream(pd, p);
            c.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_BOLD),16);
            c.beginText();
            c.newLineAtOffset(50, 750);
            c.showText("Exported Data: ");
            c.endText();

            c.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ITALIC),14);
            int y = 680;


            if(!songsListView.getItems().isEmpty()) {
                if (y < 50) {
                    c.close();
                    p = new PDPage();
                    pd.addPage(p);
                    c = new PDPageContentStream(pd, p);
                    c.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN),12);
                    y = 680;
                }
                c.beginText();
                c.newLineAtOffset(50, y);
                c.showText("All Songs: ");
                c.endText();
                y -= 20;

                for (Songs s : songsListView.getItems()) {
                    if (y < 50) {
                        c.close();
                        p = new PDPage();
                        pd.addPage(p);
                        c = new PDPageContentStream(pd, p);
                        c.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN),11);
                        y = 680;
                    }

                    c.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN),12);
                    c.beginText();
                    c.newLineAtOffset(70, y);
                    c.showText(s.toString());
                    c.endText();
                    y -= 15;
                }
            }

            if(!playlistsListView.getItems().isEmpty()) {
                y = 680;
                c.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ITALIC),14);
                c.beginText();
                c.newLineAtOffset(50, y);
                c.showText("Playlists: ");
                c.endText();
                y -= 20;

                for(Playlists playlist : playlistsListView.getItems()) {
                    if (y < 50) {
                        c.close();
                        p = new PDPage();
                        pd.addPage(p);
                        c = new PDPageContentStream(pd, p);
                        c.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN),11);
                        y = 680;
                    }

                    c.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN),12);
                    c.beginText();
                    c.newLineAtOffset(70, y);
                    c.showText(playlist.toString());
                    c.endText();
                    y -= 15;

                    List<Songs> songs = new PlaylistController().getSongs(playlist);
                    if(!songs.isEmpty()) {
                        for (Songs song : songs) {
                            c.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN),11);
                            c.beginText();
                            c.newLineAtOffset(70, y);
                            c.showText(song.toString());
                            c.endText();
                            y -= 10;
                        }
                    } else {
                        c.setFont(new PDType1Font(Standard14Fonts.FontName.TIMES_ROMAN),11);
                        c.beginText();
                        c.newLineAtOffset(70, y);
                        c.showText("No Songs found");
                        c.endText();
                        y -= 10;
                    }
                    y -= 7;
                }
            }
            c.close();
            pd.save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void clickExportPDF() {
        FileChooser fc = new FileChooser();
        fc.setTitle("Export As PDF");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("PDF Files", "*.pdf"));

        File file = fc.showSaveDialog(viewPane.getScene().getWindow());
        if(file != null) {
            exportAsPDF(file);
        }
    }
    @FXML
    private void onExit() {
        Stage stage = (Stage) viewPane.getScene().getWindow();
        stage.close();
    }
}
