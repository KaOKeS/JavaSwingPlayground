package gui;

import model.Person;

import javax.swing.table.AbstractTableModel;
import java.util.List;

public class PersonTableModel extends AbstractTableModel {

    private transient List<Person> db;
    private final String[] colNames = {"ID", "Name", "Occupation", "Age Category", "Employment Category", "US Citizen", "Tax ID"};

    public void setData(List<Person> db) {
        this.db = db;
    }

    @Override
    public int getRowCount() {
        return db.size();
    }

    @Override
    public int getColumnCount() {
        return 7;
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        Person person = db.get(rowIndex);
        return switch (columnIndex) {
            case 0 -> person.getId();
            case 1 -> person.getName();
            case 2 -> person.getOccupation();
            case 3 -> person.getAgeCategory();
            case 4 -> person.getEmpCat();
            case 5 -> person.isUsCitizen();
            case 6 -> person.getTaxId();
            default -> null;
        };
    }

    @Override
    public String getColumnName(int column) {
        return colNames[column];
    }
}
