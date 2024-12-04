package api;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/fastfingers"; // Ganti dengan nama database Anda
    private static final String USERNAME = "root"; // Ganti sesuai dengan username database Anda
    private static final String PASSWORD = ""; // Ganti sesuai dengan password database Anda

    public static Connection database;

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver"); // Pastikan driver JDBC benar
            database = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Koneksi database gagal: " + e.getMessage());
        }
    }
}
