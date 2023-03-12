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
    }
}
