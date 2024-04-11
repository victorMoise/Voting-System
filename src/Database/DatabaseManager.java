package Database;

import java.sql.*;

import BCrypt.BCrypt;

public class DatabaseManager {
    private final Connection connection;

    // Constructor to initialize the database connection
    public DatabaseManager() {
        try {
            // Establish connection
            String password = "";
            String username = "root";
            String url = "jdbc:mysql://localhost:3306/voting_system";
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("Connected to database");
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to connect to database", ex);
        }
    }

    public Connection getConnection() {
        return connection;
    }

    // Method to close the database connection
    public void closeConnection() {
        try {
            connection.close();
            System.out.println("Connection closed");
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to close connection", ex);
        }
    }

    // Method to create a new user
    public void createUser(String username, String password, int age) {
        try {
            // Encrypt the password using BCrypt
            String hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());

            // Create a new user
            String query = "INSERT INTO users (username, password, age, role) VALUES (?, ?, ?, ?)";
            PreparedStatement statement = connection.prepareStatement(query, Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, username);
            statement.setString(2, hashedPassword);
            statement.setInt(3, age);
            statement.setString(4, "voter"); // Set the role parameter
            statement.executeUpdate();

            // Get the auto-generated ID of the new user
            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                int userId = generatedKeys.getInt(1);
                System.out.println("User created with ID: " + userId);
            } else {
                throw new SQLException("Failed to get auto-generated ID for user");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to create user", ex);
        }
    }

    public boolean loginUser(String username, String password) {
        try {
            // Query to get the user with the given username
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            // Check if the user exists
            if (resultSet.next()) {
                // Get the hashed password from the database
                String hashedPassword = resultSet.getString("password");

                // Check if the password matches the hashed password

                // Return true if the password matches, indicating successful login
                return BCrypt.checkpw(password, hashedPassword);
            } else {
                // If the user does not exist, return false
                return false;
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to check user", ex);
        }
    }
}
