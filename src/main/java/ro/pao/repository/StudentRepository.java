package ro.pao.repository;

import ro.pao.model.sealed.Student;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends UserRepository<Student>{
    void enrollStudentToCourse(UUID studentId, UUID courseId);
    List<Student> getAllStudentsWithHigherAvgGrade(Double averageGrade);
    List<Student> getAllStudentsWithLowerAvgGrade(Double averageGrade);
    void updateStudentAverageGrade(UUID studentId, Double averageGrade);
    List<Student> getAllStudentsByCourseId(UUID courseId);
}
