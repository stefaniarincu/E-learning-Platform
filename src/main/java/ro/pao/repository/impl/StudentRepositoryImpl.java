package ro.pao.repository.impl;

import ro.pao.application.csv.CsvLogger;
import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.exceptions.UserNotFoundException;
import ro.pao.mapper.UserMapper;
import ro.pao.model.sealed.Student;
import ro.pao.repository.StudentRepository;
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

public class StudentRepositoryImpl implements StudentRepository {
    private static final UserMapper userMapper = UserMapper.getInstance();
    @Override
    public Optional<Student> getObjectById(UUID id) throws SQLException, ObjectNotFoundException {
        String sqlStatement = "SELECT * FROM _USER u LEFT JOIN STUDENT s on u.user_id = s.user_id WHERE u.user_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<Student> student = Optional.ofNullable(userMapper.mapToStudent(resultSet));

            if (student.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Select student by id failed!"));

                throw new UserNotFoundException("No student found with the id: " + id);
            } else {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Selected from Student!"));
            }

            return student;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM _USER WHERE user_id = ? AND LOWER(user_type) LIKE 'student'";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, id.toString());
            preparedStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Student deleted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Delete student failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void updateObjectById(UUID id, Student newObject) {
        String sqlUserUpdate = "UPDATE _USER SET first_name = ?, last_name = ?, email = ?, password = ? WHERE user_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement userUpdateStatement = connection.prepareStatement(sqlUserUpdate)) {

            userUpdateStatement.setString(1, newObject.getFirstName()); //set first_name
            userUpdateStatement.setString(2, newObject.getLastName()); //set last_name
            userUpdateStatement.setString(3, newObject.getEmail()); //set email
            userUpdateStatement.setString(4, newObject.getPassword()); //set password
            userUpdateStatement.setString(5, id.toString()); //set user_id

            userUpdateStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Student updated!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Update student failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void updateStudentAverageGrade(UUID studentId, Double averageGrade) {
        String sqlStudentUpdate = "UPDATE STUDENT SET average_grade = ? WHERE user_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement studentUpdateStatement = connection.prepareStatement(sqlStudentUpdate)) {

            studentUpdateStatement.setDouble(1, averageGrade); //set average_grade
            studentUpdateStatement.setString(2, studentId.toString()); //set user_id

            studentUpdateStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Student average grade updated!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Update average grade for student failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addNewObject(Student newObject) {
        String sqlUserInsert = "INSERT INTO _USER (user_id, first_name, last_name, email, password, user_type) VALUES(?, ?, ?, ?, ?, ?)";
        String sqlStudentInsert = "INSERT INTO STUDENT (user_id, average_grade) VALUES (?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement userInsertStatement = connection.prepareStatement(sqlUserInsert);
             PreparedStatement studentInsertStatement = connection.prepareStatement(sqlStudentInsert)) {

            connection.setAutoCommit(false);

            userInsertStatement.setString(1, newObject.getId().toString()); //set user_id
            userInsertStatement.setString(2, newObject.getFirstName()); //set first_name
            userInsertStatement.setString(3, newObject.getLastName()); //set last_name
            userInsertStatement.setString(4, newObject.getEmail()); //set email
            userInsertStatement.setString(5, newObject.getPassword()); //set password
            userInsertStatement.setString(6, "Student"); //set user_type

            userInsertStatement.executeUpdate();

            studentInsertStatement.setString(1, newObject.getId().toString()); //set user_id
            studentInsertStatement.setDouble(2, 0.0); //set average_grade

            studentInsertStatement.executeUpdate();

            connection.commit();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "1 new Student inserted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Insert student failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Student> getAll(){
        String sqlStatement = "SELECT * FROM _USER u LEFT JOIN STUDENT s on u.user_id = s.user_id WHERE LOWER(u.user_type) LIKE ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "student");

            return userMapper.mapToStudentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void addAllFromGivenList(List<Student> objectList) {
        objectList.forEach(this::addNewObject);
    }

    @Override
    public void enrollStudentToCourse(UUID studentId, UUID courseId) {
        String sqlStudentCourseInsert = "INSERT INTO ENROLLED (user_id, course_id) VALUES(?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement studentCourseInsertStatement = connection.prepareStatement(sqlStudentCourseInsert)) {

            studentCourseInsertStatement.setString(1, studentId.toString()); //set user_id
            studentCourseInsertStatement.setString(2, courseId.toString()); //set course_id

            studentCourseInsertStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "1 Student was enrolled to a course!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Student enrollment to course failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Student> getAllStudentsWithHigherAvgGrade(Double averageGrade) {
        String sqlStatement = "SELECT * FROM _USER u LEFT JOIN STUDENT s on u.user_id = s.user_id WHERE LOWER(u.user_type) LIKE ? AND s.average_grade >= ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "student");
            preparedStatement.setDouble(2, averageGrade);

            return userMapper.mapToStudentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public List<Student> getAllStudentsWithLowerAvgGrade(Double averageGrade) {
        String sqlStatement = "SELECT * FROM _USER u LEFT JOIN STUDENT s on u.user_id = s.user_id WHERE LOWER(u.user_type) LIKE ? AND s.average_grade <= ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "student");
            preparedStatement.setDouble(2, averageGrade);

            return userMapper.mapToStudentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public Optional<Student> getUserByEmail(String userEmail) throws SQLException, UserNotFoundException {
        String sqlStatement = "SELECT * FROM _USER u LEFT JOIN STUDENT s ON u.user_id = s.user_id WHERE LOWER(email) LIKE ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setObject(1, userEmail.toLowerCase()); //set email
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<Student> student = Optional.ofNullable(userMapper.mapToStudent(resultSet));

            if (student.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Select student by email failed!"));
                throw new UserNotFoundException("No student found with the email: " + userEmail);
            } else {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Selected from student!"));
            }

            return student;
        }
    }

    @Override
    public List<Student> getAllStudentsByCourseId(UUID courseId) {
        String sqlStatement = "SELECT * FROM _USER u LEFT JOIN STUDENT s ON s.user_id = u.user_id WHERE LOWER(u.user_type) LIKE ? AND s.user_id IN (SELECT user_id FROM ENROLLED WHERE course_id = ?)";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "student");
            preparedStatement.setString(2, courseId.toString());

            return userMapper.mapToStudentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }
}
