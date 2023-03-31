package gui;

import controller.Controller;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.prefs.Preferences;

public class MainFrame extends JFrame {

    private final Toolbar toolbar;
    private final FormPanel formPanel;
    private final JFileChooser fileChooser;
    private final transient Controller controller;
    private final TablePanel tablePanel;
    private final PrefsDialog prefsDialog;
    private final Preferences prefs;
    private final JSplitPane splitPane;
    private final JTabbedPane tabbedPane;
    private final MessagePanel messagePanel;

    public MainFrame(String title) {
        super(title);
        toolbar = new Toolbar();
        formPanel = new FormPanel();
        controller = new Controller();
        tablePanel = new TablePanel();
        prefsDialog = new PrefsDialog(this);
        tabbedPane = new JTabbedPane();
        messagePanel = new MessagePanel(this);
        splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, formPanel, tabbedPane);
        splitPane.setOneTouchExpandable(true);

        tabbedPane.addTab("Person Database", tablePanel);
        tabbedPane.addTab("Messages", messagePanel);

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

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.out.println("Window closing...");
                controller.disconnect();
                dispose();
                //swing bug elimination
                System.gc();
            }
        });

        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        setSize(500, 500);
        setMinimumSize(new Dimension(500, 500));
        setLayout(new BorderLayout());

        add(toolbar, BorderLayout.PAGE_START);
        add(splitPane, BorderLayout.CENTER);

        toolbar.setToolbarListener(new ToolbarListener() {
            @Override
            public void saveEventOccured() {
                connect();
                try {
                    controller.save();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Unable to save to database.", "Database save problem", JOptionPane.ERROR_MESSAGE);
                }
            }

            @Override
            public void refreshEventOccured() {
                connect();
                try {
                    controller.load();
                } catch (SQLException e) {
                    JOptionPane.showMessageDialog(MainFrame.this, "Unable to load from database.", "Database load problem", JOptionPane.ERROR_MESSAGE);
                }
                tablePanel.refresh();
            }
        });
        formPanel.setFormListener(e -> {
            controller.addPerson(e);
            tablePanel.refresh();
        });

        setVisible(true);
    }

    private void connect() {
        try {
            controller.connect();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(MainFrame.this, "Cannot connect to database.", "Database connection problem", JOptionPane.ERROR_MESSAGE);
        }
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
            if (menuItem.isSelected()) {
                splitPane.setDividerLocation((int) formPanel.getMinimumSize().getWidth());
            }
            formPanel.setVisible(menuItem.isSelected());
        });

        fileMenu.setMnemonic(KeyEvent.VK_F);
        exitItem.setMnemonic(KeyEvent.VK_X);
        exitItem.addActionListener(e -> {
            int action = JOptionPane.showConfirmDialog(MainFrame.this, "Do you really want to exit application?", "Confirm Exit", JOptionPane.OK_CANCEL_OPTION);
            if (action == JOptionPane.OK_OPTION) {
                WindowListener[] windowListeners = getWindowListeners();
                for (WindowListener listener : windowListeners) {
                    listener.windowClosing(new WindowEvent(MainFrame.this, 0));
                }
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
        prefsItem.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_P, InputEvent.CTRL_DOWN_MASK));

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
