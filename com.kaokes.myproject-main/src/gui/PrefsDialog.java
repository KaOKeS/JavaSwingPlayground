package gui;

import javax.swing.*;
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

        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();


        ///////////// FIRST ROW ///////////
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;

        add(new JLabel("User: "), gc);

        gc.gridx++;
        add(userField, gc);

        ///////////// NEXT ROW ///////////
        gc.gridy++;
        gc.gridx = 0;

        add(new JLabel("Password: "), gc);

        gc.gridx++;
        add(passField, gc);

        ///////////// NEXT ROW ///////////
        gc.gridy++;
        gc.gridx = 0;

        add(new JLabel("Port: "), gc);

        gc.gridx++;
        add(portSpinner, gc);

        ///////////// NEXT ROW ///////////
        gc.gridy++;

        gc.gridx = 0;
        add(okBtn, gc);

        gc.gridx++;
        add(cancelBtn, gc);

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

        setSize(400, 300);
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
}
