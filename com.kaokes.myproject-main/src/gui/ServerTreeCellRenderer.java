package gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.DefaultTreeCellRenderer;
import javax.swing.tree.TreeCellRenderer;
import java.awt.*;

public class ServerTreeCellRenderer implements TreeCellRenderer {
    private final JCheckBox leafRenderer;
    private final DefaultTreeCellRenderer nonLeafRenderer;
    private final Color textForeground;
    private final Color textBackground;
    private final Color selectionForeground;
    private final Color selectionBackground;


    public ServerTreeCellRenderer() {
        leafRenderer = new JCheckBox();
        nonLeafRenderer = new DefaultTreeCellRenderer();
        nonLeafRenderer.setLeafIcon(Utils.createIcon("/images/icons8-server-16.png"));
        nonLeafRenderer.setOpenIcon(Utils.createIcon("/images/icons8-internet-folder-16.png"));
        nonLeafRenderer.setClosedIcon(Utils.createIcon("/images/icons8-internet-folder-16.png"));

        textForeground = UIManager.getColor("Tree.textForeground");
        textBackground = UIManager.getColor("Tree.textBackground");
        selectionForeground = UIManager.getColor("Tree.selectionForeground");
        selectionBackground = UIManager.getColor("Tree.selectionBackground");
    }

    @Override
    public Component getTreeCellRendererComponent(JTree tree, Object value, boolean selected, boolean expanded, boolean leaf, int row, boolean hasFocus) {
        if (leaf) {
            if (value instanceof DefaultMutableTreeNode node) {
                ServerInfo nodeInfo = (ServerInfo) node.getUserObject();
                leafRenderer.setText(nodeInfo.toString());
                leafRenderer.setSelected(nodeInfo.isChecked());
            }
            if (selected) {
                leafRenderer.setForeground(selectionForeground);
                leafRenderer.setBackground(selectionBackground);
            } else {
                leafRenderer.setForeground(textForeground);
                leafRenderer.setBackground(textBackground);
            }
            return leafRenderer;
        } else {
            return nonLeafRenderer.getTreeCellRendererComponent(tree, value, selected, expanded, false, row, hasFocus);
        }
    }
}
