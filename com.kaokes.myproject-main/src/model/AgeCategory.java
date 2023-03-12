package model;

public enum AgeCategory {
    CHILD, ADULT, SENIOR;

    public static AgeCategory getAgeCategoryById(int id) {
        return switch (id) {
            case 0 -> AgeCategory.CHILD;
            case 2 -> AgeCategory.SENIOR;
            default -> AgeCategory.ADULT;
        };
    }
}
