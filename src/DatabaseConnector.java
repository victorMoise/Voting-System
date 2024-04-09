import java.sql.*;

public class DatabaseConnector {
    public static Connection connectToDatabase(String url, String username, String password) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            return DriverManager.getConnection(url, username, password);
        } catch (ClassNotFoundException e) {
            System.out.println("MySQL JDBC Driver not found");
        } catch (SQLException e) {
            System.out.println("Connection failed");
        }

        return null;
    }
}
