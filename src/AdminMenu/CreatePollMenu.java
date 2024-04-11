package AdminMenu;

import Utils.ButtonCustomizer;
import Database.DatabaseManager;

import javax.swing.*;
import java.awt.*;

public class CreatePollMenu extends JFrame {
    public CreatePollMenu() {
        setTitle("Poll Creation Menu");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        JLabel usernameLabel = new JLabel("Title:");
        JTextField usernameField = new JTextField();

        JLabel optionsLabel = new JLabel("Options: (separated by commas)");
        JTextField optionsField = new JTextField();

        panel.add(usernameLabel);
        panel.add(usernameField);
        panel.add(optionsLabel);
        panel.add(optionsField);

        JButton createPollButton = new JButton("Create Poll");
        ButtonCustomizer buttonCustomizer = new ButtonCustomizer();
        buttonCustomizer.customizeButton(createPollButton);
        panel.add(createPollButton);

        createPollButton.addActionListener(e -> {
            String title = usernameField.getText();
            String options = optionsField.getText();
            String[] optionsArray = options.split(",");
            for (String option : optionsArray) {
                System.out.println(option);
            }
            DatabaseManager db = new DatabaseManager();
            db.createPoll(title, optionsArray);
            JOptionPane.showMessageDialog(this, "Poll created successfully", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
        });

        add(panel);

        // Pack the components to ensure proper layout
        pack();

        // Center the frame on the screen
        setLocationRelativeTo(null);
        setVisible(true);
    }
}
