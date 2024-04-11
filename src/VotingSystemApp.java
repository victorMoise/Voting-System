import MainMenu.MainMenu;

import javax.swing.*;
import java.sql.SQLException;

public class VotingSystemApp {
    public static void main(String[] args) throws SQLException {
        SwingUtilities.invokeLater(MainMenu::new);


    }
}