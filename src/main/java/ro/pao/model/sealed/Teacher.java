package ro.pao.model.sealed;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.Course;

import java.util.List;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
@AllArgsConstructor
public non-sealed class Teacher extends User {
    private String degree;

    private List<Course> teachCourses;

    public Teacher() {

    }

    @Override
    public String toString() {
        StringBuilder matString = new StringBuilder();

        for (Course course: teachCourses) {
            matString.append(course.getTitle()).append("\n");
        }

        if (!matString.isEmpty()) {
            return "TEACHER: " + super.toString() + " (has the degree: " + this.degree + ") teaches the courses: "
                        + matString + "\n";
        } else {
            return "TEACHER: " + super.toString() + " (has the degree: " + this.degree + ").\n";
        }
    }
}
