package controller;

import gui.FormEvent;
import model.*;

import java.io.File;
import java.io.IOException;
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
}
