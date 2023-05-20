package ro.pao.model;

import lombok.*;
import lombok.experimental.SuperBuilder;
import ro.pao.model.abstracts.Material;
import ro.pao.model.enums.Discipline;
import ro.pao.model.sealed.Student;

import java.util.List;
import java.util.UUID;

@Getter
@Setter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Course {
   private UUID courseId;
   private String title;
   private Discipline discipline;
   private UUID teacherId;
   private List<Material> materialList;
   private List<Student> enrolledStudents;

}
