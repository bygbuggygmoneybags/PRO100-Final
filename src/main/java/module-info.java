module csc180.rodriguez.g.grodriguezfinal {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    exports csc180.rodriguez.g.grodriguezfinal.views;
    exports csc180.rodriguez.g.grodriguezfinal.controllers;
    exports csc180.rodriguez.g.grodriguezfinal.models;

    opens csc180.rodriguez.g.grodriguezfinal.views to javafx.fxml;
    opens csc180.rodriguez.g.grodriguezfinal.controllers to javafx.fxml;
    opens csc180.rodriguez.g.grodriguezfinal.models to javafx.fxml;
}