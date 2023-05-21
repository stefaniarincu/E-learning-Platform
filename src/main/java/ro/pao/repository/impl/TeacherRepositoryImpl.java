package ro.pao.repository.impl;

import ro.pao.application.csv.CsvLogger;
import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.exceptions.UserNotFoundException;
import ro.pao.mapper.UserMapper;
import ro.pao.model.sealed.Teacher;
import ro.pao.repository.TeacherRepository;
import ro.pao.service.impl.LogServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class TeacherRepositoryImpl implements TeacherRepository {
    private static final UserMapper userMapper = UserMapper.getInstance();
    @Override
    public Optional<Teacher> getObjectById(UUID id) throws SQLException, ObjectNotFoundException {
        String sqlStatement = "SELECT * FROM _USER u LEFT JOIN TEACHER t on u.user_id = t.user_id WHERE u.user_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<Teacher> teacher = Optional.ofNullable(userMapper.mapToTeacher(resultSet));

            if (teacher.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Select student by id failed!"));

                throw new UserNotFoundException("No teacher found with the id: " + id);
            } else {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Selected from Teacher!"));
            }

            return teacher;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM _USER WHERE user_id = ? AND LOWER(user_type) LIKE 'teacher'";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Teacher deleted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Delete teacher failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void updateObjectById(UUID id, Teacher newObject) {
        String sqlUserUpdate = "UPDATE _USER SET first_name = ?, last_name = ?, email = ?, password = ? WHERE user_id = ?";
        String sqlTeacherUpdate = "UPDATE TEACHER SET degree = ? WHERE user_id = ?";

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

            teacherUpdateStatement.setString(1, newObject.getDegree()); //set degree
            teacherUpdateStatement.setString(2, id.toString()); //set user_id

            teacherUpdateStatement.executeUpdate();

            connection.commit();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Teacher updated!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Update teacher failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addNewObject(Teacher newObject) {
        String sqlUserInsert = "INSERT INTO _USER (user_id, first_name, last_name, email, password, user_type) VALUES(?, ?, ?, ?, ?, ?)";
        String sqlTeacherInsert = "INSERT INTO TEACHER (user_id, degree) VALUES (?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement userInsertStatement = connection.prepareStatement(sqlUserInsert);
             PreparedStatement teacherInsertStatement = connection.prepareStatement(sqlTeacherInsert)) {

            connection.setAutoCommit(false);

            userInsertStatement.setString(1, newObject.getId().toString()); //set user_id
            userInsertStatement.setString(2, newObject.getFirstName()); //set first_name
            userInsertStatement.setString(3, newObject.getLastName()); //set last_name
            userInsertStatement.setString(4, newObject.getEmail()); //set email
            userInsertStatement.setString(5, newObject.getPassword()); //set password
            userInsertStatement.setString(6, "Teacher"); //set user_type

            userInsertStatement.executeUpdate();

            teacherInsertStatement.setString(1, newObject.getId().toString()); //set user_id
            teacherInsertStatement.setString(2, newObject.getDegree()); //set degree

            teacherInsertStatement.executeUpdate();

            connection.commit();
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "1 teacher inserted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Insert teacher failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Teacher> getAll() {
        String sqlStatement = "SELECT * FROM _USER u LEFT JOIN TEACHER t ON u.user_id = t.user_id WHERE LOWER(u.user_type) LIKE ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "teacher");

            return userMapper.mapToTeacherList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void addAllFromGivenList(List<Teacher> objectList) {
        objectList.forEach(this::addNewObject);
    }

    @Override
    public List<Teacher> getAllTeachersByDegree(String degree) {
        String sqlStatement = "SELECT * FROM _USER u LEFT JOIN TEACHER t ON u.user_id = t.user_id WHERE LOWER(u.user_type) LIKE ? AND LOWER(t.degree) LIKE ? ";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "teacher"); //set user_type
            preparedStatement.setString(2, degree); //set degree

            return userMapper.mapToTeacherList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public Optional<Teacher> getUserByEmail(String userEmail) throws SQLException, UserNotFoundException {
        String sqlStatement = "SELECT * FROM _USER u LEFT JOIN TEACHER t ON u.user_id = t.user_id WHERE LOWER(email) LIKE ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setObject(1, userEmail.toLowerCase()); //set email
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<Teacher> teacher = Optional.ofNullable(userMapper.mapToTeacher(resultSet));

            if (teacher.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Select teacher by email failed!"));
                throw new UserNotFoundException("No teacher found with the email: " + userEmail);
            } else {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Selected from teacher!"));
            }
            return teacher;
        }
    }
}
