package ro.pao.model.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.Arrays;

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
                .filter(enumElement -> enumElement.typeString.equalsIgnoreCase(field))
                .findAny()
                .orElse(NONE);
    }
}
