package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.mapper.UserMapper;
import ro.pao.model.Student;
import ro.pao.repository.StudentRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StudentRepositoryImpl implements StudentRepository {
    private static final UserMapper userMapper = UserMapper.getInstance();
    @Override
    public Optional<Student> getObjectById(UUID id) throws SQLException {
        String sqlStatement = "SELECT * FROM USER u, STUDENT s ON u.user_id = s.user_id(+) WHERE u.user_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            return Optional.ofNullable(userMapper.mapToStudent(preparedStatement.executeQuery()));
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM USER WHERE u.user_id = ? AND LOWER(user_type) LIKE 'student'";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateObjectById(UUID id, Student newObject) {
        String sqlUserUpdate = "UPDATE USER SET first_name = ?, last_name = ?, email = ?, password = ? WHERE user_id = ?";
        String sqlStudentUpdate = "SELECT * FROM STUDENT WHERE 1>2";//UPDATE STUDENT SET document_type = ? WHERE material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement userUpdateStatement = connection.prepareStatement(sqlUserUpdate);
             PreparedStatement studentUpdateStatement = connection.prepareStatement(sqlStudentUpdate)) {
            connection.setAutoCommit(false);

            userUpdateStatement.setString(1, newObject.getFirstName()); //set first_name
            userUpdateStatement.setString(2, newObject.getLastName()); //set last_name
            userUpdateStatement.setString(3, newObject.getEmail()); //set email
            userUpdateStatement.setString(4, newObject.getPassword()); //set password
            userUpdateStatement.setString(5, id.toString()); //set user_id

            userUpdateStatement.executeUpdate();

            /* TO DO
            studentUpdateStatement.setString(2, id.toString()); //set user_id
            */

            studentUpdateStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewObject(Student newObject) {
        String sqlUserInsert = "INSERT INTO USER (user_id, first_name, last_name, email, password) VALUES(?, ?, ?, ?, ?)";
        String sqlStudentInsert = "SELECT * FROM STUDENT WHERE 1>2";//"INSERT INTO STUDENT (user_id) VALUES (?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement userInsertStatement = connection.prepareStatement(sqlUserInsert);
             PreparedStatement studentInsertStatement = connection.prepareStatement(sqlStudentInsert)) {
            connection.setAutoCommit(false);

            userInsertStatement.setString(1, newObject.getId().toString()); //set user_id
            userInsertStatement.setString(2, newObject.getFirstName()); //set first_name
            userInsertStatement.setString(3, newObject.getLastName()); //set last_name
            userInsertStatement.setString(4, newObject.getEmail()); //set email
            userInsertStatement.setString(5, newObject.getPassword()); //set password

            userInsertStatement.executeUpdate();

            /* TO DO
            studentInsertStatement.setString(1, newObject.getId().toString()); //set user_id
            */

            studentInsertStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Student> getAll() throws SQLException {
        String sqlStatement = "SELECT * FROM USER u, STUDENT s ON u.user_id = s.user_id WHERE LOWER(u.user_type) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, "student");

            return userMapper.mapToStudentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void addAllFromGivenList(List<Student> objectList) {
        objectList.forEach(this::addNewObject);
    }

    @Override
    public List<Student> getAllStudentByAvgGrade(Double averageGrade) {
        return null;
    }

    @Override
    public Optional<Student> getUserByEmail(String userEmail) {
        return Optional.empty();
    }
}
