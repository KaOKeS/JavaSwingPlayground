package controller;

import lombok.Getter;
import lombok.Setter;

import java.util.EventObject;

@Getter
@Setter
public class FormEvent extends EventObject {
    private String name;
    private String occupation;

    public FormEvent(Object source, String name, String occupation) {
        super(source);
        this.name = name;
        this.occupation = occupation;
    }
}
