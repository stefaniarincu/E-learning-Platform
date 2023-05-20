package ro.pao.mapper;

import ro.pao.model.Course;
import ro.pao.model.Grade;
import ro.pao.model.enums.Discipline;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class CourseMapper {
    private static final CourseMapper INSTANCE = new CourseMapper();

    private CourseMapper() {
    }

    public static CourseMapper getInstance() {
        return INSTANCE;
    }

    public Course mapToCourse(ResultSet resultSet) throws SQLException {
        if(resultSet.next()) {
            return new Course().toBuilder()
                    .courseId(UUID.fromString(resultSet.getString("course_id")))
                    .title(resultSet.getString("title"))
                    .discipline(Discipline.valueOf(resultSet.getString("discipline")))
                    .teacherId(UUID.fromString(resultSet.getString("teacher_id")))
                    .build();
        } else {
            return null;
        }
    }

    public Course mapToCourseWithoutNext(ResultSet resultSet) throws SQLException {
        return new Course().toBuilder()
                .courseId(UUID.fromString(resultSet.getString("course_id")))
                .title(resultSet.getString("title"))
                .discipline(Discipline.valueOf(resultSet.getString("discipline")))
                .teacherId(UUID.fromString(resultSet.getString("teacher_id")))
                .build();
    }

    public List<Course> mapToCourseList(ResultSet resultSet) throws SQLException {
        List<Course> courseList = new ArrayList<>();

        while (resultSet.next()) {
            courseList.add(mapToCourseWithoutNext(resultSet));
        }

        return courseList;
    }
}
