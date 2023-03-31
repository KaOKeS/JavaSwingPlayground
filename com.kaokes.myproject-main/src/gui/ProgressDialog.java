package gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class ProgressDialog extends JDialog {
    private JButton cancelButton;
    private JProgressBar progressBar;
    private ProgressDialogListener listener;

    public ProgressDialog(Window parent, String title) {
        super(parent, title, ModalityType.APPLICATION_MODAL);
        cancelButton = new JButton("Cancel");
        progressBar = new JProgressBar();
        progressBar.setStringPainted(true);
        progressBar.setString("Retrieving massages...");
        progressBar.setMaximum(10);

        setLayout(new FlowLayout());

        Dimension size = cancelButton.getPreferredSize();
        size.width = 400;
        progressBar.setPreferredSize(size);

        add(progressBar);
        add(cancelButton);

        cancelButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (listener != null) {
                    listener.progressDialogCancelled();
                }
            }
        });

        pack();
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                listener.progressDialogCancelled();
            }
        });

        setLocationRelativeTo(parent);
    }

    public void setListener(ProgressDialogListener listener) {
        this.listener = listener;
    }

    public void setMaximum(int value) {
        progressBar.setMaximum(value);
    }

    public void setValue(int value) {
        int progress = value * 100 / progressBar.getMaximum();
        progressBar.setString(String.format("%d%% complete", progress));
        progressBar.setValue(value);
    }

    @Override
    public void setVisible(final boolean visible) {
        SwingUtilities.invokeLater(() -> {
            if (!visible) {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            } else {
                progressBar.setValue(0);
            }
            ProgressDialog.super.setVisible(visible);
        });
    }
}
