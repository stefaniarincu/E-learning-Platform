package ro.pao.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

@Getter
@AllArgsConstructor
public enum Discipline {
    MATHEMATICS ("mathematics"),
    INFORMATICS ("informatics"),
    PHYSICS ("physics"),
    CHEMISTRY ("chemistry"),
    ENGLISH ("english"),
    NONE ("none");

    private final String disciplineString;

    public static Discipline getEnumByFieldString(String field) {
        return Arrays.stream(Discipline.values())
                .filter(enumElement -> enumElement.disciplineString.equalsIgnoreCase(field))
                .findAny()
                .orElse(NONE);
    }
}
