package ro.pao.repository;

import ro.pao.model.Student;

import java.util.List;
import java.util.UUID;

public interface StudentRepository extends UserRepository<Student>{
    void addMaterialToStudent(UUID studentId, UUID materialId);
    List<Student> getAllStudentByAvgGrade(Double averageGrade);
}
