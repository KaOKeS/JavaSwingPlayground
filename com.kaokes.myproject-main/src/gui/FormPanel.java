package gui;

import lombok.Setter;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;
import java.awt.event.KeyEvent;

public class FormPanel extends JPanel {
    private final JLabel nameLabel;
    private final JLabel occupationLabel;
    private final JTextField nameField;
    private final JTextField occupationField;
    private final JButton okBtn;
    private final JList<String> ageList;
    private final JComboBox<String> empCombo;
    private final JCheckBox citizenCheck;
    private final JTextField taxField;
    private final JLabel taxLabel;
    private final JRadioButton maleRadio;
    private final JRadioButton femaleRadio;
    private final ButtonGroup genderGroup;

    @Setter
    private transient FormListener formListener;

    public FormPanel() {
        setPreferredSize(new Dimension(250, 1));
        TitledBorder innerBorder = BorderFactory.createTitledBorder("Add Person");
        Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
        setBorder(BorderFactory.createCompoundBorder(outerBorder, innerBorder));

        nameLabel = new JLabel("Name: ");
        occupationLabel = new JLabel("Occupation: ");
        nameField = new JTextField(10);
        occupationField = new JTextField(10);
        ageList = new JList<>();
        citizenCheck = new JCheckBox();
        taxField = new JTextField(10);
        taxLabel = new JLabel("Tax ID: ");
        maleRadio = new JRadioButton("Male");
        femaleRadio = new JRadioButton("Female");
        genderGroup = new ButtonGroup();
        maleRadio.setSelected(true);
        maleRadio.setActionCommand("male");
        femaleRadio.setActionCommand("female");

        genderGroup.add(maleRadio);
        genderGroup.add(femaleRadio);

        taxField.setEnabled(false);
        taxLabel.setEnabled(false);
        citizenCheck.addActionListener(e -> {
            boolean isChecked = citizenCheck.isSelected();
            taxField.setEnabled(isChecked);
            taxLabel.setEnabled(isChecked);
        });

        DefaultListModel<String> ageModel = new DefaultListModel<>();
        ageModel.addElement("before 18");
        ageModel.addElement("18 to 65");
        ageModel.addElement("over 65");

        ageList.setModel(ageModel);
        ageList.setSelectedIndex(1);
        ageList.setPreferredSize(new Dimension(105, 60));
        ageList.setBorder(BorderFactory.createEtchedBorder());

        empCombo = new JComboBox<>();
        DefaultComboBoxModel<String> empModel = new DefaultComboBoxModel<>();
        empModel.addElement("employed");
        empModel.addElement("self-employed");
        empModel.addElement("unemployed");
        empCombo.setModel(empModel);

        okBtn = new JButton("OK");
        okBtn.addActionListener(e -> {
            String gender = genderGroup.getSelection().getActionCommand();

            FormEvent formEvent = new FormEvent(this,
                    nameField.getText(),
                    occupationField.getText(),
                    ageList.getSelectedIndex(),
                    (String) empCombo.getSelectedItem(),
                    taxField.getText(),
                    citizenCheck.isSelected(),
                    gender);
            if (formListener != null) {
                formListener.formEventOccurred(formEvent);
            }
        });

        okBtn.setMnemonic(KeyEvent.VK_O);
        nameLabel.setDisplayedMnemonic(KeyEvent.VK_N);
        nameLabel.setLabelFor(nameField);

        layoutComponents();
    }

    private void layoutComponents() {
        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        Insets rightInset = new Insets(0, 0, 0, 5);
        Insets zeroInset = new Insets(0, 0, 0, 0);

        /////// First Row  ////////////
        gc.gridy = 0;
        gc.weightx = 1;
        gc.weighty = 0.1;
        gc.fill = GridBagConstraints.NONE;

        gc.gridx = 0;

        gc.anchor = GridBagConstraints.LINE_END;
        gc.insets = rightInset;
        add(nameLabel, gc);

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.LINE_START;
        gc.insets = zeroInset;
        add(nameField, gc);

        /////// Next row ////////////
        gc.gridy++;

        gc.gridx = 0;
        gc.insets = rightInset;
        gc.anchor = GridBagConstraints.LINE_END;
        add(occupationLabel, gc);

        gc.gridx = 1;
        gc.insets = zeroInset;
        gc.anchor = GridBagConstraints.LINE_START;
        add(occupationField, gc);

        /////// Next row ////////////
        gc.gridy++;
        gc.weighty = 0.5;

        gc.gridx = 0;
        gc.insets = rightInset;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(new JLabel("Age: "), gc);

        gc.gridx = 1;
        gc.insets = zeroInset;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(ageList, gc);

        /////// Next row ////////////
        gc.gridy++;

        gc.gridx = 0;
        gc.insets = rightInset;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(new JLabel("Employment: "), gc);

        gc.gridx = 1;
        gc.insets = zeroInset;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(empCombo, gc);

        /////// Next row ////////////
        gc.gridy++;

        gc.gridx = 0;
        gc.insets = rightInset;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(new JLabel("US Citizen: "), gc);

        gc.gridx = 1;
        gc.insets = zeroInset;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(citizenCheck, gc);

        /////// Next row ////////////
        gc.gridy++;

        gc.gridx = 0;
        gc.insets = rightInset;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        add(taxLabel, gc);

        gc.gridx = 1;
        gc.insets = zeroInset;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(taxField, gc);

        /////// Next row ////////////
        gc.gridy++;
        gc.weighty = 0.05;

        gc.gridx = 0;
        gc.insets = rightInset;
        gc.anchor = GridBagConstraints.LINE_END;
        add(new JLabel("Gender: "), gc);

        gc.gridx = 1;
        gc.insets = zeroInset;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(maleRadio, gc);

        /////// Next row ////////////
        gc.gridy++;

        gc.gridx = 1;
        gc.weighty = 0.2;
        gc.insets = zeroInset;
        gc.anchor = GridBagConstraints.FIRST_LINE_START;
        add(femaleRadio, gc);

        /////// Next row ////////////
        gc.gridy++;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.CENTER;
        gc.weighty = 5;
        add(okBtn, gc);
    }
}
