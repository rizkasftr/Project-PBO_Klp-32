//class interface nya
package api;

import java.sql.Connection;
import java.sql.SQLException;

public interface UserDAO {
    void addUser(String username, String password, String jmlh_attempt, String email) throws SQLException;
    void getUser(int id) throws SQLException;
    Connection getConnection() throws SQLException;
}