package api;

import java.sql.Connection;

public class Database extends AbstractDatabase {
    public Database() {
        super();
    }

    @Override
    public Connection getConnection() {
        return this.connection;
    }

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("MySQL JDBC Driver not found.");
        }
    }
}