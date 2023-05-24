package ro.pao.repository;

import ro.pao.model.Course;

import java.util.List;
import java.util.UUID;

public interface CourseRepository extends RepositoryGeneric<Course>{
    List<Course> getAllCoursesByTeacherId(UUID teacherId);
    List<Course> getAllCoursesByStudentId(UUID studentId);
}
