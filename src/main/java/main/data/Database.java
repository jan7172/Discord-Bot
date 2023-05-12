package main.data;

import java.sql.*;

public class Database {

    private final static Database DATABASE = new Database();
    private Connection connection;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (Exception e) {
            System.err.println("Error bei Treiber");
            e.printStackTrace();
        }
    }

    private Database() {}

    public static Database getInstance() {
        return DATABASE;
    }

    public void initConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:" + System.getProperty("user.dir") + System.getProperty("file.separator") + "Storage.db");
        connection.setClientInfo("autoReconnect", "true");
    }

    public void closeConnection() throws SQLException {
        connection.close();
    }

    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql);
    }

    public Statement getStatement() throws SQLException {
        return connection.createStatement();
    }

}
