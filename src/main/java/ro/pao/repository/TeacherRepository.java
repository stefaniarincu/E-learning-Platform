package ro.pao.repository;

import ro.pao.model.Teacher;

import java.util.List;

public interface TeacherRepository extends UserRepository<Teacher> {
    List<Teacher> getAllTeacherByDegree(String degree);
}
