package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame {

    private final TextPanel textPanel;
    private final Toolbar toolbar;
    private final FormPanel formPanel;
    private final JFileChooser fileChooser;
    private final transient Controller controller;
    private final TablePanel tablePanel;
    private final PrefsDialog prefsDialog;
    private final Preferences prefs;

    public MainFrame(String title) {
        super(title);
        textPanel = new TextPanel();
        toolbar = new Toolbar();
        formPanel = new FormPanel();
        controller = new Controller();
        tablePanel = new TablePanel();
        prefsDialog = new PrefsDialog(this);

        prefs = Preferences.userRoot().node("db");
        tablePanel.setData(controller.getPeople());

        tablePanel.addPersonTableListener(controller::removePerson);
        prefsDialog.setPrefsListener((user, password, port) -> {
            prefs.put("user", user);
            prefs.put("password", password);
            prefs.put("port", String.valueOf(port));
        });
        String user = prefs.get("user", "");
        String password = prefs.get("password", "");
        int port = prefs.getInt("port", 3306);

        prefsDialog.setDefaults(user, password, port);

        fileChooser = new JFileChooser();
        fileChooser.addChoosableFileFilter(new PersonFileFilter());

        setJMenuBar(createMenuBar());


        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(500, 500);
        setMinimumSize(new Dimension(500, 500));
        setLayout(new BorderLayout());

        add(toolbar, BorderLayout.NORTH);
        add(tablePanel, BorderLayout.CENTER);
        add(formPanel, BorderLayout.WEST);

        toolbar.setTextListener(textPanel::appendText);
        formPanel.setFormListener(e -> {
            controller.addPerson(e);
            tablePanel.refresh();
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
        JMenuItem prefsItem = new JMenuItem("Preferences...");
        JCheckBoxMenuItem showFormItem = new JCheckBoxMenuItem("Person form");
        showFormItem.setSelected(true);
        showMenu.add(showFormItem);
        windowMenu.add(showMenu);
        windowMenu.add(prefsItem);

        prefsItem.addActionListener(e -> prefsDialog.setVisible(true));

        menuBar.add(fileMenu);
        menuBar.add(windowMenu);

        showFormItem.addActionListener(e -> {
            JCheckBoxMenuItem menuItem = (JCheckBoxMenuItem) e.getSource();
            formPanel.setVisible(menuItem.isSelected());
        });

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.addActionListener(e -> {
            int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit application?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
            if (action == JOptionPane.OK_OPTION) {
                System.exit(0);
            }
        });
        exitItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_X, InputEvent.CTRL_DOWN_MASK));


        importDataItem.addActionListener(e -> {
            if (fileChooser.showOpenDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.loadFromFile(fileChooser.getSelectedFile());
                    tablePanel.refresh();
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Could not load data from file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });
        importDataItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I, InputEvent.CTRL_DOWN_MASK));

        exportDataItem.addActionListener(e -> {
            if (fileChooser.showSaveDialog(MainFrame.this) == JFileChooser.APPROVE_OPTION) {
                try {
                    controller.saveToFile(fileChooser.getSelectedFile());
                } catch (IOException ex) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Could not save data to file", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        return menuBar;
    }
}
