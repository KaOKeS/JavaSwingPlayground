package controller;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final JTextArea textArea;
    private final JButton btn;

    public MainFrame(String title) {
        super(title);
        btn = new JButton("Ok");
        textArea = new JTextArea();

        btn.addActionListener(e -> textArea.append("test\n"));

        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        add(textArea,BorderLayout.CENTER);
        add(btn,BorderLayout.SOUTH);

        setVisible(true);
    }
}
