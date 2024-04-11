package SignUpMenu;

import Database.DatabaseManager;

import javax.swing.*;
import java.awt.*;

public class SignUpChecker extends Component {
    public boolean checkSignUp(String username, char[] password, char[] confirmPassword, String age) {
        // Check if any field is empty
        if (username.isEmpty() || password.length == 0 || confirmPassword.length == 0 || age.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill out every field", "Empty Fields", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if username is valid
        if (username.length() > 30) {
            JOptionPane.showMessageDialog(this, "Username cannot be empty or exceed 30 characters", "Invalid Username", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if the user already exists in the Database
        DatabaseManager db = new DatabaseManager();
        if (db.checkUserExists(username)) {
            JOptionPane.showMessageDialog(this, "Username already exists", "Invalid Username", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if password is valid
        if (password.length < 8) {
            JOptionPane.showMessageDialog(this, "Password must be at least 8 characters long", "Invalid Password", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if password and confirm password match
        if (!String.valueOf(password).equals(String.valueOf(confirmPassword))) {
            JOptionPane.showMessageDialog(this, "Passwords do not match", "Invalid Password", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // Check if age is valid
        try {
            int ageInt = Integer.parseInt(age);
            if (ageInt < 0 || ageInt > 150) {
                JOptionPane.showMessageDialog(this, "Age must be between 0 and 150", "Invalid Age", JOptionPane.ERROR_MESSAGE);
                return false;
            }
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Age must be a number", "Invalid Age", JOptionPane.ERROR_MESSAGE);
            return false;
        }

        // If all checks pass, return true
        return true;
    }
}
