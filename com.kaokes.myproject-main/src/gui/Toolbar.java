package gui;

import lombok.Setter;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Toolbar extends JPanel implements ActionListener {
    private final JButton helloBtn;
    private final JButton goodbyeBtn;
    @Setter
    private transient StringListener textListener;

    public Toolbar() {
        setBorder(BorderFactory.createEtchedBorder());
        helloBtn = new JButton("Hello");
        goodbyeBtn = new JButton("Goodbye");
        helloBtn.addActionListener(this);
        goodbyeBtn.addActionListener(this);
        setLayout(new FlowLayout(FlowLayout.LEFT));
        add(helloBtn);
        add(goodbyeBtn);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        JButton clicked = (JButton) e.getSource();
        if (textListener != null) {
            if (clicked == helloBtn) {
                textListener.textEmitted("Hello\n");
            } else {
                textListener.textEmitted("Goodbye\n");
            }
        }
    }
}
