package gui;

import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JToolBar implements ActionListener {
    private final JButton saveBtn;
    private final JButton refreshBtn;
    @Setter
    private transient ToolbarListener toolbarListener;

    public Toolbar() {
        setBorder(BorderFactory.createEtchedBorder());
        saveBtn = new JButton();
        saveBtn.setIcon(Utils.createIcon("/images/icons8-save-30.png"));
        saveBtn.setToolTipText("Save");
        saveBtn.setPreferredSize(new Dimension(30, 30));

        refreshBtn = new JButton();
        refreshBtn.setIcon(Utils.createIcon("/images/icons8-update-left-rotation-30.png"));
        refreshBtn.setToolTipText("Refresh");
        refreshBtn.setPreferredSize(new Dimension(30, 30));

        saveBtn.addActionListener(this);
        refreshBtn.addActionListener(this);

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
