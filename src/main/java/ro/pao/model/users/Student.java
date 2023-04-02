package ro.pao.model.users;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.Document;
import ro.pao.model.materials.Test;
import ro.pao.model.materials.Video;
import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.abstracts.User;

import java.text.DecimalFormat;
import java.util.*;

@SuperBuilder
@Getter
@Setter
@NoArgsConstructor
public class Student extends User {
    private List<Material> materials;
    private TreeMap<Discipline, List<Double>> grades;
    private Double averageGrade;

    public Student(UUID id, String firstName, String lastName, String email, String password)
    {
        super(id, firstName, lastName, email, password);

        this.materials = new ArrayList<>();

        this.grades = new TreeMap<>();

        this.averageGrade = 0.0;
    }

    public Student(UUID id, String firstName, String lastName, String email, String password, List<Material> materials)
    {
        super(id, firstName, lastName, email, password);

        this.materials = materials;
        Collections.sort(materials);

        this.grades = new TreeMap<>();

        this.averageGrade = 0.0;
    }

    public Student(UUID id, String firstName, String lastName, String email, String password, List<Material> materials, TreeMap<Discipline, List<Double>> grades)
    {
        super(id, firstName, lastName, email, password);

        this.materials = materials;
        Collections.sort(materials);

        this.grades = grades;

        this.averageGrade = calculateAverageGrade();
    }

    public double calculateAverageGrade() {
        DecimalFormat df = new DecimalFormat("#.##");

        if(grades == null)
            return 0.00;

        return Double.parseDouble(df.format(grades.values().stream()
                .mapToDouble(gradeList -> gradeList.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.00))
                .average()
                .orElse(0.00)));
    }

    @Override
    public String toString() {
        StringBuilder matString = new StringBuilder();

        for (Material material: materials) {
            if (material instanceof Document) {
                matString.append("DOCUMENT ---> ").append(material);
            } else if (material instanceof Video){
                matString.append("VIDEO ---> ").append(material);
            } else if (material instanceof Test){
                matString.append("TEST ---> ").append(material);
            }
        }

        if (!matString.isEmpty()) {
            return "STUDENT: " + super.toString() + " has the average grade: " + this.averageGrade + ".\n     Materials used: \n" + matString + "\n";
        } else {
            return "STUDENT: " + super.toString() + " has the average grade: " + this.averageGrade + ".\n";
        }
    }
}
