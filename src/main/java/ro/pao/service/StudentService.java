package ro.pao.service;

import ro.pao.model.Grade;
import ro.pao.model.sealed.Student;

import java.util.*;

public interface StudentService extends UserService<Student>{
    List<Student> getStudentsWithLowerGrade(Double averageGrade);
    List<Student> getStudentsWithHigherGrade(Double averageGrade);
    void enrollStudent(UUID studentId, UUID courseId);
    void updateStudentGradeList(UUID studentId);
}
