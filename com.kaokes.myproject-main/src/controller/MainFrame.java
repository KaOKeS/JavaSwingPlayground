package controller;

import model.AgeCategory;

import javax.swing.*;
import java.awt.*;

public class MainFrame extends JFrame {

    private final TextPanel textPanel;
    private final Toolbar toolbar;
    private final FormPanel formPanel;

    public MainFrame(String title) {
        super(title);
        textPanel = new TextPanel();
        toolbar = new Toolbar();
        formPanel = new FormPanel();


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setLayout(new BorderLayout());

        add(toolbar, BorderLayout.NORTH);
        add(textPanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.WEST);

        toolbar.setTextListener(textPanel::appendText);
        formPanel.setFormListener(e -> {
            String name = e.getName();
            String occupation = e.getOccupation();
            AgeCategory ageCat = e.getAgeCategory();

            textPanel.appendText(name + ": " + occupation + ": " + ageCat.ordinal() + "\n");
        });

        setVisible(true);
    }
}
