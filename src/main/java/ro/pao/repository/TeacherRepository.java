package ro.pao.repository;

import ro.pao.model.Teacher;
import ro.pao.model.enums.Discipline;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface TeacherRepository extends UserRepository<Teacher> {
    List<Discipline> getAllCoursesByTeacherId(UUID teacherId) throws SQLException;
    List<Teacher> getAllTeachersByDegree(String degree);
}
