package csc180.rodriguez.g.grodriguezfinal;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnect {
    private static final String url = "jdbc:sqlite:identifier.sqlite";

    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(url);
    }
}
