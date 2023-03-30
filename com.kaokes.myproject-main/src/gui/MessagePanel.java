package gui;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;

@AllArgsConstructor
class ServerInfo {
    private String name;
    @Getter
    private Long id;
    @Setter
    @Getter
    private boolean checked;

    @Override
    public String toString() {
        return name;
    }
}

public class MessagePanel extends JPanel {
    private final JTree serverTree = new JTree(createTree());
    private final TreeCellRenderer treeCellRenderer = new ServerTreeCellRenderer();
    private final ServerTreeCellEditor treeCellEditor = new ServerTreeCellEditor();

    public MessagePanel() {
        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        serverTree.setCellRenderer(treeCellRenderer);
        serverTree.setEditable(true);
        serverTree.setCellEditor(treeCellEditor);

        serverTree.addTreeSelectionListener(e -> {
            DefaultMutableTreeNode node = (DefaultMutableTreeNode) serverTree.getLastSelectedPathComponent();
            Object userObject = node.getUserObject();
            if (userObject instanceof ServerInfo serverInfo) {
                Long id = serverInfo.getId();
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(serverTree), BorderLayout.CENTER);
    }

    private DefaultMutableTreeNode createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
        DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfo("New York", 0L, true));
        DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfo("Boston", 1L, false));
        DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new ServerInfo("Los Angeles", 2L, true));

        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo("London", 3L, false));
        DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo("Edinburgh", 4L, true));

        branch1.add(server1);
        branch1.add(server2);
        branch1.add(server3);

        branch2.add(server4);
        branch2.add(server5);

        top.add(branch1);
        top.add(branch2);
        return top;
    }
}
