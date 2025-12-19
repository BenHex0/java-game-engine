package engine.database;

import java.sql.*;

public class Database {

    private static final String URL = "jdbc:mysql://localhost:3306/game_db";
    private static final String USER = "root";
    private static final String PASSWORD = "admin";

    private Connection connection;

    // Constructor
    public Database() {
        connect();
    }

    // Connect once
    private void connect() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection(URL, USER, PASSWORD);
            System.out.println("Database connected");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Insert player score
    public void saveScore(String playerName, int score) {
        String sql = "INSERT INTO scores (player_name, score) VALUES (?, ?)";

        try (PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setString(1, playerName);
            ps.setInt(2, score);
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Get top scores
    public ResultSet getTopScores(int limit) {
        try {
            String sql = "SELECT * FROM scores ORDER BY score DESC LIMIT ?";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, limit);
            return ps.executeQuery();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    // Close DB on exit
    public void close() {
        try {
            if (connection != null)
                connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
