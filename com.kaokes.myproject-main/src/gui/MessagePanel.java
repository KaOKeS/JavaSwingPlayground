package gui;

import controller.MessageServer;
import model.Message;

import javax.swing.*;
import javax.swing.event.CellEditorListener;
import javax.swing.event.ChangeEvent;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreeCellRenderer;
import javax.swing.tree.TreeSelectionModel;
import java.awt.*;
import java.util.Set;
import java.util.TreeSet;

public class MessagePanel extends JPanel {
    private final JTree serverTree;
    private final TreeCellRenderer treeCellRenderer = new ServerTreeCellRenderer();
    private final ServerTreeCellEditor treeCellEditor = new ServerTreeCellEditor();
    private final Set<Integer> selectedServers = new TreeSet<>();
    private final MessageServer messageServer = new MessageServer();

    public MessagePanel() {
        selectedServers.add(0);
        selectedServers.add(1);
        selectedServers.add(4);
        serverTree = new JTree(createTree());
        serverTree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        serverTree.setCellRenderer(treeCellRenderer);
        serverTree.setEditable(true);
        serverTree.setCellEditor(treeCellEditor);

        treeCellEditor.addCellEditorListener(new CellEditorListener() {
            @Override
            public void editingStopped(ChangeEvent e) {
                ServerInfo serverInfo = (ServerInfo) treeCellEditor.getCellEditorValue();
                System.out.println("Server with ID: " + serverInfo.getId() + " enabled: " + serverInfo.isChecked());
                int serverId = serverInfo.getId().intValue();

                if (serverInfo.isChecked()) {
                    selectedServers.add(serverId);
                } else {
                    selectedServers.remove(serverId);
                }
                messageServer.setSelectedServers(selectedServers);
                System.out.println("Messages waiting: " + messageServer.getMessageCount());

                for(Message message : messageServer){
                    System.out.println(message.getTitle());
                }
            }

            @Override
            public void editingCanceled(ChangeEvent e) {
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(serverTree), BorderLayout.CENTER);
    }

    private DefaultMutableTreeNode createTree() {
        DefaultMutableTreeNode top = new DefaultMutableTreeNode("Servers");

        DefaultMutableTreeNode branch1 = new DefaultMutableTreeNode("USA");
        DefaultMutableTreeNode server1 = new DefaultMutableTreeNode(new ServerInfo("New York", 0L, selectedServers.contains(0)));
        DefaultMutableTreeNode server2 = new DefaultMutableTreeNode(new ServerInfo("Boston", 1L, selectedServers.contains(1)));
        DefaultMutableTreeNode server3 = new DefaultMutableTreeNode(new ServerInfo("Los Angeles", 2L, selectedServers.contains(2)));

        DefaultMutableTreeNode branch2 = new DefaultMutableTreeNode("UK");
        DefaultMutableTreeNode server4 = new DefaultMutableTreeNode(new ServerInfo("London", 3L, selectedServers.contains(3)));
        DefaultMutableTreeNode server5 = new DefaultMutableTreeNode(new ServerInfo("Edinburgh", 4L, selectedServers.contains(4)));

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
