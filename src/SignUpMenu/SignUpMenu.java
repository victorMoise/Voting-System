package SignUpMenu;

import javax.swing.*;
import java.awt.*;
import MainMenu.MainMenu;

import Database.DatabaseManager;
import Utils.ButtonCustomizer;

public class SignUpMenu extends JFrame {
    public SignUpMenu() {
        setTitle("Sign Up Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Username:");
        JTextField usernameField = new JTextField();

        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField();

        JLabel confirmPasswordLabel = new JLabel("Confirm Password:");
        JPasswordField confirmPasswordField = new JPasswordField();

        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(passwordLabel);
        panel.add(passwordField);
        panel.add(confirmPasswordLabel);
        panel.add(confirmPasswordField);
        panel.add(ageLabel);
        panel.add(ageField);

        JButton signUpButton = new JButton("Sign Up");
        ButtonCustomizer buttonCustomizer = new ButtonCustomizer();
        buttonCustomizer.customizeButton(signUpButton);
        panel.add(signUpButton);

        signUpButton.addActionListener(e -> {
            String username = usernameField.getText();
            char[] password = passwordField.getPassword();
            char[] confirmPassword = confirmPasswordField.getPassword();
            String age = ageField.getText();

            SignUpChecker signUpChecker = new SignUpChecker();
            if (signUpChecker.checkSignUp(username, password, confirmPassword, age)) {
                DatabaseManager db = new DatabaseManager();
                db.createUser(username, String.valueOf(password), Integer.parseInt(age));
                JOptionPane.showMessageDialog(this, "Sign up successful", "Success", JOptionPane.INFORMATION_MESSAGE);

                new MainMenu();
                dispose();
            }
        });

        add(panel);
        setVisible(true);
    }
}
