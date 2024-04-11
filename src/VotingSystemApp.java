import javax.swing.*;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class VotingSystemApp {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(MainMenu::new);


        Connection connection = DatabaseConnector.connectToDatabase("jdbc:mysql://localhost:3306/voting_system", "root", "");
        if (connection != null) {
            try {
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM users");
                while(resultSet.next()) {
                    System.out.println(resultSet.getInt(1) + " " + resultSet.getString(2) + " " + resultSet.getString(3) + " " + resultSet.getString(4) + " " + resultSet.getInt(5));

                }
                // Perform other database operations...
            } catch (SQLException e) {
                System.out.println("Error creating statement");
            } finally {
                try {
                    connection.close();
                } catch (SQLException e) {
                    System.out.println("Error closing connection");
                }
            }
        } else {
            System.out.println("Failed to connect to the database");
        }
    }
}