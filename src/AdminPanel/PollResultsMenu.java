package AdminPanel;

import Database.DatabaseManager;
import Utils.ButtonCustomizer;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class PollResultsMenu extends JFrame {
    public PollResultsMenu() {
        setTitle("Poll Results");
        setLayout(new GridBagLayout());
        List<Map<String, Object>> pollResults = new DatabaseManager().getPollResults();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(5, 5, 5, 5); // Padding between components

        // Add label for "Available Polls"
        JLabel availablePollsLabel = new JLabel("Available Polls");
        availablePollsLabel.setFont(new Font("Arial", Font.BOLD, 16));
        add(availablePollsLabel, gbc);
        gbc.gridy++; // Move to the next row

        ButtonCustomizer buttonCustomizer = new ButtonCustomizer();

        // Add buttons for each poll
        for (Map<String, Object> pollData : pollResults) {
            String pollTitle = (String) pollData.get("title");
            List<Map<String, Object>> options = (List<Map<String, Object>>) pollData.get("options");
            JButton button = new JButton(pollTitle);
            button.addActionListener(e -> {
                // Handle button click event
                displayPollPopup(pollTitle, options);
            });
            buttonCustomizer.customizeButtonLarger(button);
            add(button, gbc);
            gbc.gridy++; // Move to the next row for the next button
        }

        pack(); // Pack the frame to fit the components
        setLocationRelativeTo(null); // Center the frame on the screen
        setVisible(true); // Make the frame visible
    }

    private void displayPollPopup(String pollTitle, List<Map<String, Object>> options) {
        StringBuilder message = new StringBuilder("Poll Title: " + pollTitle + "\n\n");

        int totalVotes = 0;
        Map<String, Integer> votesPerOption = new HashMap<>();

        for (Map<String, Object> option : options) {
            String optionTitle = (String) option.get("option");
            int votersCount = ((List<?>) option.get("voters")).size();
            totalVotes += votersCount;
            votesPerOption.put(optionTitle, votersCount);
        }

        int place = 1;
        for (Map.Entry<String, Integer> entry : votesPerOption.entrySet()) {
            String optionTitle = entry.getKey();
            int votes = entry.getValue();
            double percentage = ((double) votes / totalVotes) * 100;
            message.append(place).append(". ").append(optionTitle).append(": ").append(votes).append(" votes (").append(String.format("%.2f", percentage)).append("%)\n");
            place++;
        }

        // Determine the winner
        String winner = "";
        int maxVotes = 0;
        for (Map.Entry<String, Integer> entry : votesPerOption.entrySet()) {
            if (entry.getValue() > maxVotes) {
                maxVotes = entry.getValue();
                winner = entry.getKey();
            }
        }

        message.append("\nWinner: ").append(winner).append(" with ").append(maxVotes).append(" votes");

        JOptionPane.showMessageDialog(this, message.toString(), "Poll Details", JOptionPane.INFORMATION_MESSAGE);
    }

}
