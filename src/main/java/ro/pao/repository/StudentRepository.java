package ro.pao.repository;

import ro.pao.model.Student;

import java.util.List;

public interface StudentRepository extends UserRepository<Student>{
    List<Student> getAllStudentByAvgGrade(Double averageGrade);
}
