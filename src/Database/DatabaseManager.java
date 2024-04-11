package Database;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.simple.JSONArray;
import BCrypt.BCrypt;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

    public boolean checkUserExists(String username) {
        try {
            // Query to get the user with the given username
            String query = "SELECT * FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            // Return true if the user exists
            return resultSet.next();
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to check user", ex);
        }
    }

    public String getUserRole(String username) {
        try {
            // Query to get the user with the given username
            String query = "SELECT role FROM users WHERE username = ?";
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();

            // Return the role of the user
            if (resultSet.next()) {
                return resultSet.getString("role");
            } else {
                throw new SQLException("User not found");
            }
        } catch (SQLException ex) {
            throw new RuntimeException("Failed to get user role", ex);
        }
    }

    // Method to create a new poll
    public void createPoll(String title, String[] options) {
        String sql = "INSERT INTO poll_table(poll, options) VALUES(?, ?)";
        JSONArray optionsArray = new JSONArray();

        // Creating JSON objects for each option
        for (String optionTitle : options) {
            JSONObject optionObject = new JSONObject();
            optionObject.put("option", optionTitle);
            optionObject.put("voters", new JSONArray());
            optionsArray.add(optionObject);
        }

        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, title);
            stmt.setString(2, optionsArray.toJSONString());
            stmt.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("Failed to create poll", e);
        }
    }

    public List<Map<String, Object>> getPollResults() {
        List<Map<String, Object>> polls = new ArrayList<>();
        String sql = "SELECT poll, options FROM poll_table";

        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                String pollTitle = rs.getString("poll");
                String optionsJson = rs.getString("options");
                JSONArray optionsArray = (JSONArray) JSONValue.parse(optionsJson);
                List<Map<String, Object>> options = new ArrayList<>();
                for (Object optionObj : optionsArray) {
                    JSONObject optionObject = (JSONObject) optionObj;
                    Map<String, Object> optionData = new HashMap<>();
                    optionData.put("option", optionObject.get("option"));
                    optionData.put("voters", optionObject.get("voters"));
                    options.add(optionData);
                }
                Map<String, Object> pollData = new HashMap<>();
                pollData.put("title", pollTitle);
                pollData.put("options", options);
                polls.add(pollData);
            }
        } catch (SQLException e) {
            throw new RuntimeException("Failed to retrieve polls", e);
        }

        return polls;
    }
}
