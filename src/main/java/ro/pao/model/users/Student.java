package ro.pao.model.users;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.users.abstracts.User;

import java.util.List;

@SuperBuilder
@Getter
@Setter
public class Student extends User {
    private Double averageGrade;
    private List<Double> grades;
}
