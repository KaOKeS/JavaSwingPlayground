import controller.MainFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new MainFrame("Hello world"));
    }
}
