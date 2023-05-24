package ro.pao.service;

import ro.pao.model.Course;

import java.util.List;
import java.util.UUID;

public interface CourseService extends ServiceGeneric<Course> {
    List<Course> getCoursesByTeacherId(UUID teacherId);
    List<Course> getCoursesByStudentId(UUID studentId);
}
