package model;

public enum AgeCategory {
    UNDER18 {
        @Override
        public String toString() {
            return "under 18";
        }
    }, FR18TO65 {
        @Override
        public String toString() {
            return "18 to 65";
        }
    }, OVER65 {
        @Override
        public String toString() {
            return "over 65";
        }
    }
}
