package controller;

import gui.FormEvent;
import model.*;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class Controller {
    Database db = new Database();

    public List<Person> getPeople() {
        return db.getPeople();
    }

    public void addPerson(FormEvent ev) {
        String name = ev.getName();
        String occupation = ev.getOccupation();
        int ageCatId = ev.getAgeCategory();
        String empCat = ev.getEmpCat();
        boolean isUs = ev.isUsCitizen();
        String taxId = ev.getTaxId();
        String genderName = ev.getGender();

        AgeCategory ageCategory = AgeCategory.getAgeCategoryById(ageCatId);
        EmploymentCategory employmentCategory = EmploymentCategory.getEmploymentCategoryByName(empCat);
        Gender gender = Gender.getGenderByName(genderName);

        Person person = new Person(name, occupation, ageCategory, employmentCategory, taxId, isUs, gender);
        db.addPerson(person);
    }

    public void saveToFile(File file) throws IOException {
        db.saveToFile(file);
    }

    public void loadFromFile(File file) throws IOException {
        db.loadFromFile(file);
    }

    public void removePerson(int index) {
        db.removePerson(index);
    }

    public void save() throws SQLException {
        db.save();
    }

    public void disconnect() {
        db.disconnect();
    }

    public void connect() throws Exception {
        db.connect();
    }

    public void load() throws SQLException {
        db.load();
    }
}
