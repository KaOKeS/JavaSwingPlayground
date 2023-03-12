package model;

public enum EmploymentCategory {
    EMPLOYED,
    SELF_EMPLOYED,
    UNEMPLOYED,
    OTHER;

    public static EmploymentCategory getEmploymentCategoryByName(String empCat) {
        switch (empCat) {
            case "employed" -> {
                return EmploymentCategory.EMPLOYED;
            }
            case "self-employed" -> {
                return EmploymentCategory.SELF_EMPLOYED;
            }
            case "unemployed" -> {
                return EmploymentCategory.UNEMPLOYED;
            }
            default -> {
                return EmploymentCategory.OTHER;
            }
        }
    }
}
