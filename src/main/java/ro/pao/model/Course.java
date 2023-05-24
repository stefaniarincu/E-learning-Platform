package ro.pao.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.pao.model.abstracts.Material;
import ro.pao.model.enums.Discipline;
import ro.pao.model.sealed.Student;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Course {
   private UUID courseId;
   private String title;
   private Discipline discipline;
   private UUID teacherId;
   private List<Material> materialList;
   private List<Student> enrolledStudents;

   @Override
   public String toString() {
      String materials = this.materialList.stream()
              .map(Material::getTitle)
              .collect(Collectors.joining(", "));

      String students = this.enrolledStudents.stream()
              .map(Student::getFirstName)
              .collect(Collectors.joining(", "));

      if (!students.equals("") && !materials.equals("")) {
         return "Course: " + this.title + " belongs to discipline " + this.discipline.getDisciplineString() +
                 ".\nIt has has assigned the materials " + materials + ".\nAlso the students enrolled are: " +
                 students + ".\n";
      } else if (!students.equals("")) {
         return "Course: " + this.title + " belongs to discipline " + this.discipline.getDisciplineString() +
                 ".\nThe students enrolled are: " + students + ".\n";
      } else if (!materials.equals("")) {
         return "Course: " + this.title + " belongs to discipline " + this.discipline.getDisciplineString() +
                 ".\nIt has has assigned the materials " + materials + ".\n";
      } else {
         return "Course: " + this.title + " belongs to discipline " + this.discipline.getDisciplineString() + ".\n";
      }

   }
}
