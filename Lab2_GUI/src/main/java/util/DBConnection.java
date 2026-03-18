package util;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    private static final String URL = "jdbc:mysql://localhost:3306/lab2_db";
    private static final String USER = "root";
    private static final String PASSWORD = "Ushindi123!"; // change this

    public static Connection getConnection() {
        try {
            Connection conn = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Connected to database!");
            return conn;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}