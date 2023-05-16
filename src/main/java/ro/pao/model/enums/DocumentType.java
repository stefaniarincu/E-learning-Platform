package ro.pao.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.ToString;

import java.util.Arrays;

@ToString
@Getter
@AllArgsConstructor
public enum DocumentType {
    COURSE("course"),
    PRACTICE("practice"),
    IMPLEMENTATION("implementation"),
    NONE("none");

    private final String typeString;

    public static DocumentType getEnumByFieldString(String field) {
        return Arrays.stream(DocumentType.values())
                .filter(enumElement -> enumElement.typeString.equals(field))
                .findAny()
                .orElse(NONE);
    }
}
