//class turunan AbstrakUserDAO dan mengimplementasikan method UserDAO  menyimpan koneksi db
package api;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAOImpl extends AbstractUserDAO {
    public UserDAOImpl(Connection connection) {
        super(connection);
    }

    @Override
    //ekseskusi query utk nambah user
    public void addUser(String username, String password, String jmlh_attempt, String email) throws SQLException {
        String sql = "INSERT INTO user (username, password, jmlh_attempt, email) VALUES (?, ?, ?, ?)";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setString(1, username);
            statement.setString(2, password);
            statement.setString(3, jmlh_attempt);
            statement.setString(4, email);
            statement.executeUpdate();
        }
    }

    @Override
    public void getUser(int id) throws SQLException {
        String sql = "SELECT * FROM user WHERE id_user = ?";
        try (PreparedStatement statement = connection.prepareStatement(sql)) {
            statement.setInt(1, id);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()) {
                System.out.println("ID: " + resultSet.getInt("id_user"));
                System.out.println("Username: " + resultSet.getString("username"));
                System.out.println("Email: " + resultSet.getString("email"));
            }
        }
    }

    @Override
    public Connection getConnection() throws SQLException {
        return this.connection;
    }
}