import javax.swing.*;
import java.awt.*;

/**
 * Main class representing the entry point of the application.
 */
public class Main {
    /**
     * The main method, starting the application.
     *
     * @param args The command-line arguments (not used in this case).
     */
    public static void main(String[] args) {
        JFrame mainFrame = new JFrame();
        mainFrame.setSize(420, 420);
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        Container container = mainFrame.getContentPane();
        container.setLayout(new GridBagLayout());

        Canvas canvas = new Canvas();

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(10, 10, 10, 10); // Margins

        container.add(canvas, gbc);

        mainFrame.pack();
        mainFrame.setLocationRelativeTo(null); // Center the window on the screen
        mainFrame.setVisible(true);
    }
}
