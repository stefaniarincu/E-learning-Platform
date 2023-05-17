package ro.pao.service;

import ro.pao.model.Student;

import java.util.*;

public interface StudentService extends UserService<Student>{
    List<Student> getStudentsByMaterialId(UUID materialId);

    List<Student> getStudentsWithLowerGrade(Double averageGrade);

    List<Student> getStudentsWithHigherGrade(Double averageGrade);
}
