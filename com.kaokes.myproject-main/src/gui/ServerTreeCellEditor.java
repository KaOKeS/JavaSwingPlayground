package gui;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellEditor;
import javax.swing.tree.TreePath;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.util.EventObject;

public class ServerTreeCellEditor extends AbstractCellEditor implements TreeCellEditor {

    public ServerTreeCellEditor() {
        this.renderer = new ServerTreeCellRenderer();
    }

    private final ServerTreeCellRenderer renderer;
    private JCheckBox checkBox;
    private ServerInfo info;

    @Override
    public Component getTreeCellEditorComponent(JTree tree, Object value, boolean isSelected, boolean expanded, boolean leaf, int row) {
        Component component = renderer.getTreeCellRendererComponent(tree, value, isSelected, expanded, leaf, row, true);

        if (leaf) {
            DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) value;
            info = (ServerInfo) treeNode.getUserObject();
            checkBox = (JCheckBox) component;

            ItemListener itemListener = new ItemListener() {
                @Override
                public void itemStateChanged(ItemEvent e) {
                    fireEditingStopped();
                    checkBox.removeItemListener(this);
                }
            };

            checkBox.addItemListener(itemListener);
        }
        return component;
    }

    @Override
    public Object getCellEditorValue() {
        info.setChecked(checkBox.isSelected());
        return info;
    }

    @Override
    public boolean isCellEditable(EventObject e) {
        if (!(e instanceof MouseEvent mouseEvent)) {
            return false;
        }
        JTree tree = (JTree) mouseEvent.getSource();
        TreePath path = tree.getPathForLocation(mouseEvent.getX(), mouseEvent.getY());

        if (path == null) {
            return false;
        }

        Object lastComponent = path.getLastPathComponent();
        if (lastComponent == null) {
            return false;
        }

        DefaultMutableTreeNode treeNode = (DefaultMutableTreeNode) lastComponent;
        return treeNode.isLeaf();
    }
}
