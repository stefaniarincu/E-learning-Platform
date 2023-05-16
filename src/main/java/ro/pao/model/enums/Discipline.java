package ro.pao.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@ToString
@Getter
@AllArgsConstructor
public enum Discipline {
    MATHEMATICS ("mathematics") {
        @Override
        public String toString() {
            return "Mathematics";
        }
    },
    INFORMATICS ("informatics") {
        @Override
        public String toString() {
            return "Informatics";
        }
    },
    PHYSICS ("physics") {
        @Override
        public String toString() {
            return "Physics";
        }
    },
    CHEMISTRY ("chemistry") {
        @Override
        public String toString() {
            return "Chemistry";
        }
    },
    ENGLISH ("english") {
        @Override
        public String toString() {
            return "English";
        }
    },
    NONE ("none");

    private final String disciplineString;

    public static Discipline getEnumByFieldString(String field) {
        return Arrays.stream(Discipline.values())
                .filter(enumElement -> enumElement.disciplineString.equals(field))
                .findAny()
                .orElse(NONE);
    }
}
