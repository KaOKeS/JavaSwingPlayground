package gui;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

public class PrefsDialog extends JDialog {
    private final JButton okBtn;
    private final JButton cancelBtn;
    private final JSpinner portSpinner;
    private final SpinnerNumberModel spinnerModel;
    private final JTextField userField;
    private final JPasswordField passField;
    private PrefsListener prefsListener;

    public PrefsDialog(JFrame parent) {
        super(parent, "Preferences", false);
        okBtn = new JButton("Ok");
        cancelBtn = new JButton("Cancel");
        spinnerModel = new SpinnerNumberModel(3306, 0, 9999, 1);
        portSpinner = new JSpinner(spinnerModel);
        userField = new JTextField(10);
        passField = new JPasswordField(10);
        passField.setEchoChar('*');

        layoutControls();

        okBtn.addActionListener(e -> {
            Integer port = (Integer) portSpinner.getValue();
            String user = userField.getText();
            char[] password = passField.getPassword();

            if (prefsListener != null) {
                prefsListener.preferencesSet(user, new String(password), port);
            }

            setVisible(false);
        });

        cancelBtn.addActionListener(e -> setVisible(false));

        setSize(340, 250);
        setLocationRelativeTo(parent);
    }

    public void setPrefsListener(PrefsListener prefsListener) {
        this.prefsListener = prefsListener;
    }

    public void setDefaults(String user, String password, int port) {
        userField.setText(user);
        passField.setText(password);
        portSpinner.setValue(port);
    }

    private void layoutControls() {
        JPanel controlsPanel = new JPanel();
        JPanel buttonsPanel = new JPanel();

        Border titledBorder = BorderFactory.createTitledBorder("Database Preferences");
        Border spaceBorder = BorderFactory.createEmptyBorder(15, 15, 15, 15);
        controlsPanel.setBorder(BorderFactory.createCompoundBorder(spaceBorder, titledBorder));

        controlsPanel.setLayout(new GridBagLayout());

        GridBagConstraints gc = new GridBagConstraints();

        // add subpanels to dialog
        setLayout(new BorderLayout());
        add(controlsPanel, BorderLayout.CENTER);
        add(buttonsPanel, BorderLayout.SOUTH);

        Insets rightPadding = new Insets(0, 0, 0, 15);
        Insets noPadding = new Insets(0, 0, 0, 0);
        ///////////// FIRST ROW ///////////
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;
        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("User: "), gc);

        gc.gridx++;
        gc.insets = noPadding;
        gc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(userField, gc);

        ///////////// NEXT ROW ///////////
        gc.gridy++;
        gc.gridx = 0;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Password: "), gc);

        gc.gridx++;
        gc.insets = noPadding;
        gc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(passField, gc);

        ///////////// NEXT ROW ///////////
        gc.gridy++;
        gc.gridx = 0;

        gc.anchor = GridBagConstraints.EAST;
        gc.insets = rightPadding;
        controlsPanel.add(new JLabel("Port: "), gc);

        gc.gridx++;
        gc.insets = noPadding;
        gc.anchor = GridBagConstraints.WEST;
        controlsPanel.add(portSpinner, gc);

        ///////////// BUTTONS PANEL ///////////
        buttonsPanel.setLayout(new FlowLayout(FlowLayout.RIGHT));
        buttonsPanel.add(okBtn, gc);
        buttonsPanel.add(cancelBtn, gc);
        Dimension btnSize = cancelBtn.getPreferredSize();
        okBtn.setPreferredSize(btnSize);
    }
}
