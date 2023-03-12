package model;

public enum Gender {
    MALE,
    FEMALE;

    public static Gender getGenderByName(String gender) {
        return gender.equals("male") ? Gender.MALE : Gender.FEMALE;
    }
}
