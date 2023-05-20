package ro.pao.repository;

import ro.pao.model.sealed.Student;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends UserRepository<Student>{
    void enrollStudentToCourse(UUID studentId, UUID courseId);
    List<Student> getAllStudentByAvgGrade(Double averageGrade);
}
