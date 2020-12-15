package tools;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionPool {
    private static final String DB_URL = "jdbc:postgresql://127.0.0.1:5432/sportgames/";
    private static final String USER = "postgres";
    private static final String PASS = "r10t1337";
    public ConnectionPool(){}

    public static Connection getConnection() throws SQLException {

        return DriverManager.getConnection(DB_URL, USER, PASS);
    }
}
