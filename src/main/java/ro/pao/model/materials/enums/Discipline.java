package ro.pao.model.materials.enums;

import lombok.ToString;

@ToString
public enum Discipline {
    MATHEMATICS {
        @Override
        public String toString() {
            return "Mathematics";
        }
    },
    INFORMATICS {
        @Override
        public String toString() {
            return "Informatics";
        }
    },
    PHYSICS {
        @Override
        public String toString() {
            return "Physics";
        }
    },
    CHEMISTRY {
        @Override
        public String toString() {
            return "Chemistry";
        }
    },
    ENGLISH {
        @Override
        public String toString() {
            return "English";
        }
    }
}
