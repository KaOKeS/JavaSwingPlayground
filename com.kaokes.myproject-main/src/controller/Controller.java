package controller;

import gui.FormEvent;
import model.*;

public class Controller {
    Database db = new Database();

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
}
