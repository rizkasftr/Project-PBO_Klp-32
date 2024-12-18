package api;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

//
public abstract class AbstractDatabase implements DatabaseConnection {
//var konstanta
    protected static final String URL = "jdbc:mysql://localhost:3306/fastfingers"; // Ganti dengan nama database Anda
    protected static final String USERNAME = "root"; // Ganti sesuai dengan username database Anda
    protected static final String PASSWORD = ""; // Ganti sesuai dengan password database Anda

    protected Connection connection;

    public AbstractDatabase() {
        try {
            this.connection = DriverManager.getConnection(URL, USERNAME, PASSWORD);
        } catch (SQLException e) {
            e.printStackTrace();
            throw new RuntimeException("Koneksi database gagal: " + e.getMessage());
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.connection;
    }
}