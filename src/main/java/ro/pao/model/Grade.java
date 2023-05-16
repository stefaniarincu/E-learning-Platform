package ro.pao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

import java.util.UUID;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class Grade {
    private UUID gradeId;
    private UUID studentId;
    private UUID testId;
    private Double grade;

    public Grade() {

    }
}
