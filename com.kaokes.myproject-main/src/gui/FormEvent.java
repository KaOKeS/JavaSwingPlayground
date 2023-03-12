package gui;

import lombok.Getter;
import lombok.Setter;

import java.util.EventObject;

@Getter
@Setter
public class FormEvent extends EventObject {
    private String name;
    private String occupation;
    private int ageCategory;
    private String empCat;
    private String taxId;
    private boolean usCitizen;
    private String gender;

    public FormEvent(Object source, String name, String occupation, int ageCategory, String empCat, String taxId, boolean usCitizen, String gender) {
        super(source);
        this.name = name;
        this.occupation = occupation;
        this.ageCategory = ageCategory;
        this.empCat = empCat;
        this.taxId = taxId;
        this.usCitizen = usCitizen;
        this.gender = gender;
    }
}
