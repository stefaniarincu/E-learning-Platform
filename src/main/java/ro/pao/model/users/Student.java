package ro.pao.model.users;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.abstracts.User;

import java.util.*;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Student extends User {
    private TreeMap<Discipline, List<Double>> grades;
    private Double averageGrade;

    public Student(UUID id, String firstName, String lastName, String email, String password, List<Material> materials)
    {
        super(id, firstName, lastName, email, password, materials);
        this.grades = new TreeMap<>();
        this.averageGrade = 0.0;
    }

    public Student(UUID id, String firstName, String lastName, String email, String password, List<Material> materials, TreeMap<Discipline, List<Double>> grades)
    {
        super(id, firstName, lastName, email, password, materials);

        this.grades = grades;

        this.averageGrade = grades.values().stream()
                .mapToDouble(gradeList -> gradeList.stream()
                                .mapToDouble(Double::doubleValue)
                                .average()
                                .orElse(0.0))
                .average()
                .orElse(0.0);
    }
}
