package model;

public enum EmploymentCategory {
    EMPLOYED,
    SELF_EMPLOYED,
    UNEMPLOYED,
    OTHER;

    public static EmploymentCategory getEmploymentCategoryByName(String empCat) {
        return switch (empCat) {
            case "employed" -> EmploymentCategory.EMPLOYED;
            case "self-employed" -> EmploymentCategory.SELF_EMPLOYED;
            case "unemployed" -> EmploymentCategory.UNEMPLOYED;
            default -> EmploymentCategory.OTHER;
        };
    }
}
