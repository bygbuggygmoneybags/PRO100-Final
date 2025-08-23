module csc180.rodriguez.g.grodriguezfinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;

    opens csc180.rodriguez.g.grodriguezfinal to javafx.fxml;
    exports csc180.rodriguez.g.grodriguezfinal;
}