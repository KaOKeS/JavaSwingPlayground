package model;

import java.io.*;
import java.sql.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {
    private static final String CHECK_SQL = "SELECT COUNT(*) AS COUNT FROM PEOPLE WHERE ID=?";
    private static final String INSERT_SQL = "INSERT INTO PEOPLE (ID,NAME,AGE,EMPLOYMENT_STATUS,TAX_ID,US_CITIZEN,GENDER,OCCUPATION) VALUES (?,?,?,?,?,?,?,?)";
    private static final String UPDATE_SQL = "UPDATE PEOPLE SET NAME=?,AGE=?,EMPLOYMENT_STATUS=?,TAX_ID=?,US_CITIZEN=?,GENDER=?,OCCUPATION=? WHERE ID=?";
    private static final String SELECT_SQL = "SELECT ID,NAME,AGE,EMPLOYMENT_STATUS,TAX_ID,US_CITIZEN,GENDER,OCCUPATION FROM PEOPLE ORDER BY NAME";
    private final List<Person> people = new LinkedList<>();
    private Connection con;

    public void connect() throws Exception {
        if (con != null) {
            return;
        }
        try {
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/swingtest", "root", "admin");
        } catch (SQLException e) {
            throw new Exception("Can not connect to database");
        }
    }

    public void disconnect() {
        try {
            if (con != null) {
                con.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() throws SQLException {
        PreparedStatement checkStatement = con.prepareStatement(CHECK_SQL);

        for (Person person : people) {
            Long id = person.getId();
            String name = person.getName();
            String occupation = person.getOccupation();
            EmploymentCategory empCat = person.getEmpCat();
            AgeCategory ageCategory = person.getAgeCategory();
            String taxId = person.getTaxId();
            boolean usCitizen = person.isUsCitizen();
            Gender gender = person.getGender();

            checkStatement.setLong(1, id);
            ResultSet resultSet = checkStatement.executeQuery();
            resultSet.next();
            long count = resultSet.getLong(1);

            if (count == 0) {
                System.out.println("Inserting person with id: " + id);

                PreparedStatement insertStatement = con.prepareStatement(INSERT_SQL);
                insertStatement.setLong(1, id);
                insertStatement.setString(2, name);
                insertStatement.setString(3, ageCategory.name());
                insertStatement.setString(4, empCat.name());
                insertStatement.setString(5, taxId);
                insertStatement.setBoolean(6, usCitizen);
                insertStatement.setString(7, gender.name());
                insertStatement.setString(8, occupation);

                insertStatement.executeUpdate();
                insertStatement.close();
            } else {
                System.out.println("Updating person with id: " + id);

                PreparedStatement updateStatement = con.prepareStatement(UPDATE_SQL);
                int col = 1;
                updateStatement.setString(col++, name);
                updateStatement.setString(col++, ageCategory.name());
                updateStatement.setString(col++, empCat.name());
                updateStatement.setString(col++, taxId);
                updateStatement.setBoolean(col++, usCitizen);
                updateStatement.setString(col++, gender.name());
                updateStatement.setString(col++, occupation);
                updateStatement.setLong(col, id);

                updateStatement.executeUpdate();
                updateStatement.close();
            }
        }
        checkStatement.close();
    }

    public void load() throws SQLException {
        people.clear();
        Statement selectStatement = con.createStatement();
        ResultSet resultSet = selectStatement.executeQuery(SELECT_SQL);

        while (resultSet.next()) {
            long id = resultSet.getLong("id");
            String name = resultSet.getString("name");
            String age = resultSet.getString("age").toUpperCase();
            String emp = resultSet.getString("employment_status").toUpperCase();
            String taxId = resultSet.getString("tax_id");
            boolean isUs = resultSet.getBoolean("us_citizen");
            String gender = resultSet.getString("gender").toUpperCase();
            String occupation = resultSet.getString("occupation");
            //TODO: code not secured in case of wrong enum string
            people.add(new Person(id, name, occupation, AgeCategory.valueOf(age), EmploymentCategory.valueOf(emp), taxId, isUs, Gender.valueOf(gender)));
        }

        resultSet.close();
        selectStatement.close();
    }

    public void addPerson(Person person) {
        people.add(person);
    }

    public List<Person> getPeople() {
        return Collections.unmodifiableList(people);
    }

    public void saveToFile(File file) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(file)) {
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            Person[] persons = people.toArray(new Person[people.size()]);

            oos.writeObject(persons);
            oos.close();
        }
    }

    public void loadFromFile(File file) throws IOException {
        try (FileInputStream fis = new FileInputStream(file)) {
            ObjectInputStream ois = new ObjectInputStream(fis);

            Person[] persons = (Person[]) ois.readObject();
            people.clear();
            people.addAll(Arrays.asList(persons));

            ois.close();
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void removePerson(int index) {
        people.remove(index);
    }
}
