import javax.swing.*;
import java.awt.*;

class MainMenu extends JFrame {
    public MainMenu() {
        setTitle("Voting System");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700); // Adjusted initial size
        setLayout(new GridBagLayout());
        getContentPane().setBackground(Color.WHITE); // Set background color

        // Use a panel for better organization
        JPanel buttonPanel = new JPanel(new GridBagLayout());
        buttonPanel.setBackground(Color.WHITE); // Set background color

        JButton loginButton = new JButton("Login");
        JButton signUpButton = new JButton("Sign Up");

        // Customize button appearance only on windows, on Mac it does not work
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            customizeButton(loginButton);
            customizeButton(signUpButton);
        }

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10);
        buttonPanel.add(loginButton, gbc);

        gbc.gridy = 1;
        buttonPanel.add(signUpButton, gbc);

        signUpButton.addActionListener(e -> new SignUpMenu());
        loginButton.addActionListener(e -> new LoginMenu());

        // Add button panel to the main frame
        GridBagConstraints mainGBC = new GridBagConstraints();
        mainGBC.gridx = 0;
        mainGBC.gridy = 0;
        mainGBC.fill = GridBagConstraints.CENTER;
        add(buttonPanel, mainGBC);

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    // Method to customize button appearance
    private void customizeButton(JButton button) {
        button.setPreferredSize(new Dimension(120, 40));
        button.setBackground(new Color(51, 153, 255)); // Blue background color
        button.setForeground(Color.WHITE); // White text color
        button.setFocusPainted(false); // Remove focus border
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Custom font
        button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding
    }
}
