package ro.pao.repository;

import ro.pao.model.sealed.Teacher;

import java.util.List;

public interface TeacherRepository extends UserRepository<Teacher> {
    List<Teacher> getAllTeachersByDegree(String degree);
}
