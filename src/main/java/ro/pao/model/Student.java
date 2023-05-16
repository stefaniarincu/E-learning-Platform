package ro.pao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.abstracts.Material;
import ro.pao.model.abstracts.User;

import java.text.DecimalFormat;
import java.util.*;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class Student extends User {
    private List<Material> materials;
    private List<Grade> grades;
    private Double averageGrade;

    public Student(UUID id, String firstName, String lastName, String email, String password)
    {
        super(id, firstName, lastName, email, password);

        this.materials = new ArrayList<>();

        this.grades = new ArrayList<>();

        this.averageGrade = 0.0;
    }

    public Student(UUID id, String firstName, String lastName, String email, String password, List<Material> materials)
    {
        super(id, firstName, lastName, email, password);

        this.materials = materials;
        Collections.sort(materials);

        this.grades = new ArrayList<>();

        this.averageGrade = 0.0;
    }

    public Student(UUID id, String firstName, String lastName, String email, String password, List<Material> materials, List<Grade> grades)
    {
        super(id, firstName, lastName, email, password);

        this.materials = materials;
        Collections.sort(materials);

        this.grades = grades;

        this.averageGrade = calculateAverageGrade();
    }

    public Student() {

    }

    public double calculateAverageGrade() {
        DecimalFormat df = new DecimalFormat("#.##");

        //if(grades == null)
            return 0.00;

        /*return Double.parseDouble(df.format(grades.stream()
                .mapToDouble(gradeList -> gradeList.stream()
                        .mapToDouble(Double::doubleValue)
                        .average()
                        .orElse(0.00))
                .average()
                .orElse(0.00)));*/
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
