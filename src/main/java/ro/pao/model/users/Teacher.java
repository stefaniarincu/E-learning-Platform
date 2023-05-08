package ro.pao.model.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.abstracts.User;

import java.util.List;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Teacher extends User {
    private List<Discipline> teachCourses;

    @Override
    public String toString() {
        StringBuilder matString = new StringBuilder();

        for (Discipline discipline: teachCourses) {
            matString.append(discipline.toString()).append("\n");
        }

        if (!matString.isEmpty()) {
            return "TEACHER: " + super.toString() + " uploaded materials for the following disciplines: "
                        + matString + "\n";
        } else {
            return "Student: " + super.toString() + ".\n";
        }
    }
}
