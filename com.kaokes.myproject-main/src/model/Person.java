package model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serial;
import java.io.Serializable;

@Getter
@Setter
public class Person implements Serializable {

    @Serial
    private static final long serialVersionUID = 3346996092642584991L;
    private static Long count = 1L;

    private Long id;
    private String name;
    private String occupation;
    private AgeCategory ageCategory;
    private EmploymentCategory empCat;
    private String taxId;
    private boolean usCitizen;
    private Gender gender;

    public Person(String name, String occupation, AgeCategory ageCategory, EmploymentCategory empCat, String taxId, boolean usCitizen, Gender gender) {
        this.id = count++;
        this.name = name;
        this.occupation = occupation;
        this.ageCategory = ageCategory;
        this.empCat = empCat;
        this.taxId = taxId;
        this.usCitizen = usCitizen;
        this.gender = gender;
    }
}
