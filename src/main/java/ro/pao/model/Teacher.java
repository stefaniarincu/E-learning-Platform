package ro.pao.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.enums.Discipline;
import ro.pao.model.abstracts.User;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
public class Teacher extends User {
    private String degree;
    @Setter
    private List<Discipline> teachCourses;

    public Teacher() {

    }

    @Override
    public String toString() {
        StringBuilder matString = new StringBuilder();

        for (Discipline discipline: teachCourses) {
            matString.append(discipline.toString()).append("\n");
        }

        if (!matString.isEmpty()) {
            return "TEACHER: " + super.toString() + " (has the degree: " + this.degree + ") uploaded materials for the following disciplines: "
                        + matString + "\n";
        } else {
            return "TEACHER: " + super.toString() + " (has the degree: " + this.degree + ").\n";
        }
    }
}
