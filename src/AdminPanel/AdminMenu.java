package AdminPanel;

import javax.swing.*;
import java.awt.*;

import Utils.ButtonCustomizer;
import MainMenu.MainMenu;

public class AdminMenu extends JFrame {
    public AdminMenu() {
        setTitle("Admin Panel");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new GridBagLayout());

        // Create GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Padding

        ButtonCustomizer buttonCustomizer = new ButtonCustomizer(); // Create ButtonCustomizer object

        // Button for creating a new poll
        JButton createPollButton = new JButton("Create New Poll");
        createPollButton.addActionListener(event -> new CreatePollMenu());
        buttonCustomizer.customizeButton(createPollButton); // Customize button
        createPollButton.setPreferredSize(new Dimension(200, 50)); // Set preferred size
        add(createPollButton, gbc);

        // Button for viewing poll results
        JButton viewResultsButton = new JButton("See Poll Results");
        gbc.gridy = 1;
        viewResultsButton.addActionListener(event -> new PollResultsMenu());
        buttonCustomizer.customizeButton(viewResultsButton); // Customize button
        viewResultsButton.setPreferredSize(new Dimension(200, 50)); // Set preferred size
        add(viewResultsButton, gbc);

        // Button for managing users
        JButton manageUsersButton = new JButton("Manage Users");
        gbc.gridy = 2;
        manageUsersButton.addActionListener(event -> new UserManagerMenu());
        buttonCustomizer.customizeButton(manageUsersButton); // Customize button
        manageUsersButton.setPreferredSize(new Dimension(200, 50)); // Set preferred size
        add(manageUsersButton, gbc);

        JButton logoutButton = new JButton("Logout");
        gbc.gridy = 3;
        logoutButton.addActionListener(event -> {
            new MainMenu();
            dispose();
        });
        buttonCustomizer.customizeButton(logoutButton);
        logoutButton.setPreferredSize(new Dimension(200, 50));
        add(logoutButton, gbc);


        // Pack the components to ensure proper layout
        pack();

        // Center the frame on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
