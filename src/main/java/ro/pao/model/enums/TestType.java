package ro.pao.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@ToString
@Getter
@AllArgsConstructor
public enum TestType {
    QUIZ("quiz"),
    EXAM("exam"),
    NONE("none");

    private final String typeString;

    public static TestType getEnumByFieldString(String field) {
        return Arrays.stream(TestType.values())
                .filter(enumElement -> enumElement.typeString.equals(field))
                .findAny()
                .orElse(NONE);
    }
}
