package Utils;

import javax.swing.*;
import java.awt.*;

public class ButtonCustomizer {
    public void customizeButton(JButton button) {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            button.setPreferredSize(new Dimension(120, 40));
            button.setBackground(new Color(51, 153, 255)); // Blue background color
            button.setForeground(Color.WHITE); // White text color
            button.setFocusPainted(false); // Remove focus border
            button.setFont(new Font("Arial", Font.BOLD, 14)); // Custom font
            button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding
        }
    }

    public void customizeButtonLarger(JButton button) {
        String osName = System.getProperty("os.name").toLowerCase();
        if (osName.contains("windows")) {
            button.setPreferredSize(new Dimension(200, 50));
            button.setBackground(new Color(51, 153, 255)); // Blue background color
            button.setForeground(Color.WHITE); // White text color
            button.setFocusPainted(false); // Remove focus border
            button.setFont(new Font("Arial", Font.BOLD, 14)); // Custom font
            button.setBorder(BorderFactory.createEmptyBorder(5, 15, 5, 15)); // Add padding
        }
    }
}
