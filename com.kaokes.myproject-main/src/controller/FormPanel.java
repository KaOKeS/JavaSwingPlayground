package controller;

import lombok.Setter;
import model.AgeCategory;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import java.awt.*;

public class FormPanel extends JPanel {
    private final JLabel nameLabel;
    private final JLabel occupationLabel;
    private final JTextField nameField;
    private final JTextField occupationField;
    private final JButton submitBtn;
    private final JList<AgeCategory> ageList;

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
        DefaultListModel<AgeCategory> ageModel = new DefaultListModel<>();
        ageModel.addElement(AgeCategory.UNDER18);
        ageModel.addElement(AgeCategory.FR18TO65);
        ageModel.addElement(AgeCategory.OVER65);

        ageList.setModel(ageModel);
        ageList.setSelectedIndex(1);
        ageList.setPreferredSize(new Dimension(105, 60));
        ageList.setBorder(BorderFactory.createEtchedBorder());

        submitBtn = new JButton("Submit");
        submitBtn.addActionListener(e -> {
            FormEvent formEvent = new FormEvent(this, nameField.getText(), occupationField.getText(), ageList.getSelectedValue());
            if (formListener != null) {
                formListener.formEventOccurred(formEvent);
            }
        });


        setLayout(new GridBagLayout());
        GridBagConstraints gc = new GridBagConstraints();
        Insets rightInset = new Insets(0, 0, 0, 5);
        Insets zeroInset = new Insets(0, 0, 0, 0);

        /////// Row 1 ////////////
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

        /////// Row 2 ////////////
        gc.gridy = 1;

        gc.gridx = 0;
        gc.insets = rightInset;
        gc.anchor = GridBagConstraints.LINE_END;
        add(occupationLabel, gc);

        gc.gridx = 1;
        gc.insets = zeroInset;
        gc.anchor = GridBagConstraints.LINE_START;
        add(occupationField, gc);

        /////// Row 3 ////////////
        gc.gridy = 2;
        add(ageList, gc);

        /////// Row 4 ////////////
        gc.gridy = 3;

        gc.gridx = 1;
        gc.anchor = GridBagConstraints.FIRST_LINE_END;
        gc.weighty = 5;
        add(submitBtn, gc);
    }
}
