package ro.pao.mapper;

import ro.pao.model.sealed.Student;
import ro.pao.model.sealed.Teacher;
import ro.pao.model.sealed.User;
import ro.pao.model.enums.Discipline;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class UserMapper {
    private static final UserMapper INSTANCE = new UserMapper();

    private UserMapper() {
    }

    public static UserMapper getInstance() {
        return INSTANCE;
    }

    public Student mapToStudent(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Student().toBuilder()
                    .id(UUID.fromString(resultSet.getString("user_id")))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .averageGrade(resultSet.getDouble("average_grade"))
                    .build();
        } else {
            return null;
        }
    }

    public Student mapToStudentWithoutNext(ResultSet resultSet) throws SQLException {
        return new Student().toBuilder()
                .id(UUID.fromString(resultSet.getString("user_id")))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .averageGrade(resultSet.getDouble("average_grade"))
                .build();
    }

    public List<Student> mapToStudentList(ResultSet resultSet) throws SQLException {
        List<Student> studentList = new ArrayList<>();

        while (resultSet.next()) {
            studentList.add(mapToStudentWithoutNext(resultSet));
        }

        return studentList;
    }

    public Teacher mapToTeacher(ResultSet resultSet) throws SQLException {
        if (resultSet.next()) {
            return new Teacher().toBuilder()
                    .id(UUID.fromString(resultSet.getString("user_id")))
                    .firstName(resultSet.getString("first_name"))
                    .lastName(resultSet.getString("last_name"))
                    .email(resultSet.getString("email"))
                    .password(resultSet.getString("password"))
                    .degree(resultSet.getString("degree"))
                    .build();
        } else {
            return null;
        }
    }

    public Teacher mapToTeacherWithoutNext(ResultSet resultSet) throws SQLException {
        return new Teacher().toBuilder()
                .id(UUID.fromString(resultSet.getString("user_id")))
                .firstName(resultSet.getString("first_name"))
                .lastName(resultSet.getString("last_name"))
                .email(resultSet.getString("email"))
                .password(resultSet.getString("password"))
                .degree(resultSet.getString("degree"))
                .build();
    }

    public List<Teacher> mapToTeacherList(ResultSet resultSet) throws SQLException {
        List<Teacher> teacherList = new ArrayList<>();

        while (resultSet.next()) {
            teacherList.add(mapToTeacherWithoutNext(resultSet));
        }

        return teacherList;
    }

    public User mapToUser(ResultSet resultSet) throws SQLException {
        if (resultSet.next()){
            if (resultSet.getString("user_type").equalsIgnoreCase("student"))
                return mapToStudent(resultSet);
            else
                return mapToTeacher(resultSet);
        } else {
            return null;
        }
    }

    public User mapToUserWithoutNext(ResultSet resultSet) throws SQLException {
        if (resultSet.getString("user_type").equalsIgnoreCase("student"))
            return mapToStudentWithoutNext(resultSet);
        return mapToTeacherWithoutNext(resultSet);
    }

    public List<User> mapToUserList(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();

        while (resultSet.next()){
            if (resultSet.getString("user_type").equalsIgnoreCase("student"))
                userList.add(mapToStudentWithoutNext(resultSet));
            else
                userList.add(mapToTeacherWithoutNext(resultSet));
        }
        return userList;
    }
}
