package gui;

import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel implements ActionListener {
    private final JButton saveBtn;
    private final JButton refreshBtn;
    @Setter
    private transient ToolbarListener toolbarListener;

    public Toolbar() {
        setBorder(BorderFactory.createEtchedBorder());
        saveBtn = new JButton("Save");
        refreshBtn = new JButton("Refresh");
        saveBtn.addActionListener(this);
        refreshBtn.addActionListener(this);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(saveBtn);
        add(refreshBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (toolbarListener != null) {
            if (clicked == saveBtn) {
                toolbarListener.saveEventOccured();
            } else if (clicked == refreshBtn) {
                toolbarListener.refreshEventOccured();
            }
        }
    }
}
