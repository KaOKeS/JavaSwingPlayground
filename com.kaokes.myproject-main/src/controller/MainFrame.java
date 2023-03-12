package controller;

import model.AgeCategory;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;

public class MainFrame extends JFrame {

    private final TextPanel textPanel;
    private final Toolbar toolbar;
    private final FormPanel formPanel;

    public MainFrame(String title) {
        super(title);
        textPanel = new TextPanel();
        toolbar = new Toolbar();
        formPanel = new FormPanel();
        setJMenuBar(createMenuBar());


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
            String empCat = e.getEmpCat();

            textPanel.appendText(name + ": " + occupation + ": " + ageCat.ordinal() + ": " + empCat + "\n");
        });

        setVisible(true);
    }

    private JMenuBar createMenuBar() {
        JMenuBar menuBar = new JMenuBar();

        JMenu fileMenu = new JMenu("File");
        JMenuItem exportDataItem = new JMenuItem("Export Data...");
        JMenuItem importDataItem = new JMenuItem("Import Data...");
        JMenuItem exitItem = new JMenuItem("Exit");
        fileMenu.add(exportDataItem);
        fileMenu.add(importDataItem);
        fileMenu.addSeparator();
        fileMenu.add(exitItem);

        JMenu windowMenu = new JMenu("Window");
        JMenu showMenu = new JMenu("Show");
        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person form");
        showFormItem.setSelected(true);
        showMenu.add(showFormItem);
        windowMenu.add(showMenu);

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        showFormItem.addActionListener(e -> {
            JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
            formPanel.setVisible(menuItem.isSelected());
        });

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.addActionListener(e -> System.exit(0));
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));

        return menuBar;
    }
}
