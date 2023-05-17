package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.mapper.UserMapper;
import ro.pao.model.Teacher;
import ro.pao.repository.TeacherRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TeacherRepositoryImpl implements TeacherRepository {
    private static final UserMapper userMapper = UserMapper.getInstance();
    @Override
    public Optional<Teacher> getObjectById(UUID id) throws SQLException {
        String sqlStatement = "SELECT * FROM USER u, TEACHER t ON u.user_id = t.user_id(+) WHERE u.user_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            return Optional.ofNullable(userMapper.mapToTeacher(preparedStatement.executeQuery()));
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM USER WHERE u.user_id = ? AND LOWER(user_type) LIKE 'teacher'";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateObjectById(UUID id, Teacher newObject) {
        String sqlUserUpdate = "UPDATE USER SET first_name = ?, last_name = ?, email = ?, password = ? WHERE user_id = ?";
        String sqlTeacherUpdate = "SELECT * FROM TEACHER WHERE 1>2";//UPDATE TEACHER SET document_type = ? WHERE material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement userUpdateStatement = connection.prepareStatement(sqlUserUpdate);
             PreparedStatement teacherUpdateStatement = connection.prepareStatement(sqlTeacherUpdate)) {
            connection.setAutoCommit(false);

            userUpdateStatement.setString(1, newObject.getFirstName()); //set first_name
            userUpdateStatement.setString(2, newObject.getLastName()); //set last_name
            userUpdateStatement.setString(3, newObject.getEmail()); //set email
            userUpdateStatement.setString(4, newObject.getPassword()); //set password
            userUpdateStatement.setString(5, id.toString()); //set user_id

            userUpdateStatement.executeUpdate();

            /* TO DO
            teacherUpdateStatement.setString(2, id.toString()); //set user_id
            */

            teacherUpdateStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewObject(Teacher newObject) {
        String sqlUserInsert = "INSERT INTO USER (user_id, first_name, last_name, email, password) VALUES(?, ?, ?, ?, ?)";
        String sqlTeacherInsert = "SELECT * FROM TEACHER WHERE 1>2";//"INSERT INTO TEACHER (user_id) VALUES (?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement userInsertStatement = connection.prepareStatement(sqlUserInsert);
             PreparedStatement teacherInsertStatement = connection.prepareStatement(sqlTeacherInsert)) {
            connection.setAutoCommit(false);

            userInsertStatement.setString(1, newObject.getId().toString()); //set user_id
            userInsertStatement.setString(2, newObject.getFirstName()); //set first_name
            userInsertStatement.setString(3, newObject.getLastName()); //set last_name
            userInsertStatement.setString(4, newObject.getEmail()); //set email
            userInsertStatement.setString(5, newObject.getPassword()); //set password

            userInsertStatement.executeUpdate();

            /* TO DO
            teacherInsertStatement.setString(1, newObject.getId().toString()); //set user_id
            */

            teacherInsertStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Teacher> getAll() throws SQLException {
        String sqlStatement = "SELECT * FROM USER u, TEACHER t ON u.user_id = t.user_id WHERE LOWER(u.user_type) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, "teacher");

            return userMapper.mapToTeacherList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void addAllFromGivenList(List<Teacher> objectList) {
        objectList.forEach(this::addNewObject);
    }

    @Override
    public List<Teacher> getAllTeacherByDegree(String degree) {
        return null;
    }

    @Override
    public Optional<Teacher> getUserByEmail(String userEmail) {
        return Optional.empty();
    }
}