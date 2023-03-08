package controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final TextPanel textPanel;
    private final Toolbar toolbar;

    public MainFrame(String title) {
        super(title);
        textPanel = new TextPanel();
        toolbar = new Toolbar();

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        add(toolbar,BorderLayout.NORTH);
        add(textPanel,BorderLayout.CENTER);

        toolbar.setTextListener(textPanel::appendText);

        setVisible(true);
    }
}
