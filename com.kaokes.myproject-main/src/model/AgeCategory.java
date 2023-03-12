package model;

public enum AgeCategory {
    CHILD {
        @Override
        public String toString() {
            return "under 18";
        }
    }, ADULT {
        @Override
        public String toString() {
            return "18 to 65";
        }
    }, SENIOR {
        @Override
        public String toString() {
            return "over 65";
        }
    };

    public static AgeCategory getAgeCategoryById(int id) {
        switch (id) {
            case 0 -> {
                return AgeCategory.CHILD;
            }
            case 2 -> {
                return AgeCategory.SENIOR;
            }
            default -> {
                return AgeCategory.ADULT;
            }
        }
    }
}
