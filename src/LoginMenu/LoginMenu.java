package LoginMenu;

import javax.swing.*;
import java.awt.*;

import Database.DatabaseManager;
import Utils.ButtonCustomizer;
import VoterMenu.VoterMenu;
import AdminPanel.AdminMenu;

public class LoginMenu extends JFrame {
    public LoginMenu() {
        setTitle("Login Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);

        JButton loginButton = new JButton("Login");
        ButtonCustomizer buttonCustomizer = new ButtonCustomizer();
        buttonCustomizer.customizeButton(loginButton);
        panel.add(loginButton);

        loginButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();

            DatabaseManager db = new DatabaseManager();
            if (db.loginUser(username, String.valueOf(password))) {
                JOptionPane.showMessageDialog(this, "Login successful", "Success", JOptionPane.INFORMATION_MESSAGE);
                System.out.println("Logged in user: " + username + " as " + db.getUserRole(username));

                if (db.getUserRole(username).equals("admin")) {
                    new AdminMenu();
                    dispose();
                } else {
                    new VoterMenu();
                    dispose();
                }
            } else {
                JOptionPane.showMessageDialog(this, "Login failed", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        add(panel);
        setVisible(true);
    }
}
