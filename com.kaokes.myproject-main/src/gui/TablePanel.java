package gui;

import model.Person;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

public class TablePanel extends JPanel {
    private final JTable table;
    private final PersonTableModel tableModel;
    private JPopupMenu popup;
    private transient PersonTableListener personTableListener;

    public TablePanel() {
        this.tableModel = new PersonTableModel();
        this.table = new JTable(tableModel);
        this.popup = new JPopupMenu();

        JMenuItem removeItem = new JMenuItem("Delete row");
        popup.add(removeItem);

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                int row = table.rowAtPoint(e.getPoint());
                table.getSelectionModel().setSelectionInterval(row, row);
                if (e.getButton() == MouseEvent.BUTTON3) {
                    popup.show(table, e.getX(), e.getY());
                }
            }
        });

        removeItem.addActionListener(e -> {
            int row = table.getSelectedRow();
            if (personTableListener != null) {
                personTableListener.rowDeleted(row);
                tableModel.fireTableRowsDeleted(row, row);
            }
        });

        setLayout(new BorderLayout());
        add(new JScrollPane(table), BorderLayout.CENTER);
    }

    public void setData(List<Person> db) {
        tableModel.setData(db);
    }

    public void refresh() {
        tableModel.fireTableDataChanged();
    }

    public void addPersonTableListener(PersonTableListener listener) {
        this.personTableListener = listener;
    }
}
