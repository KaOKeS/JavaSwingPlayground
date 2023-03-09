package controller;

import lombok.Getter;
import lombok.Setter;
import model.AgeCategory;

import java.util.EventObject;

@Getter
@Setter
public class FormEvent extends EventObject {
    private String name;
    private String occupation;
    private AgeCategory ageCategory;
    private String empCat;

    public FormEvent(Object source, String name, String occupation, AgeCategory ageCategory, String empCat) {
        super(source);
        this.name = name;
        this.occupation = occupation;
        this.ageCategory = ageCategory;
        this.empCat = empCat;
    }
}
