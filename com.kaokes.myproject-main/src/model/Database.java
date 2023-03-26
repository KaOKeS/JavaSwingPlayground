package model;

import java.io.*;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

public class Database {
    private List<Person> people = new LinkedList<>();

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
