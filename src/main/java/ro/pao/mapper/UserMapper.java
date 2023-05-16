package ro.pao.mapper;

import ro.pao.model.Student;
import ro.pao.model.Teacher;
import ro.pao.model.abstracts.User;

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
                    .build();
        } else {
            return null;
        }
    }

    public List<Student> mapToStudentList(ResultSet resultSet) throws SQLException {
        List<Student> studentList = new ArrayList<>();

        while (resultSet.next()) {
            studentList.add(mapToStudent(resultSet));
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
                    .build();
        } else {
            return null;
        }
    }

    public List<Teacher> mapToTeacherList(ResultSet resultSet) throws SQLException {
        List<Teacher> teacherList = new ArrayList<>();

        while (resultSet.next()) {
            teacherList.add(mapToTeacher(resultSet));
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

    public List<User> mapToUserList(ResultSet resultSet) throws SQLException {
        List<User> userList = new ArrayList<>();

        while (resultSet.next()){
            if (resultSet.getString("user_type").equalsIgnoreCase("student"))
                userList.add(mapToStudent(resultSet));
            else
                userList.add(mapToTeacher(resultSet));
        }
        return userList;
    }
}
