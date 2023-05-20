package ro.pao.repository.impl;

import ro.pao.application.csv.CsvLogger;
import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.CourseNotFoundException;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.mapper.CourseMapper;
import ro.pao.model.Course;
import ro.pao.repository.CourseRepository;
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

public class CourseRepositoryImpl implements CourseRepository {
    private static final CourseMapper courseMapper = CourseMapper.getInstance();

    @Override
    public Optional<Course> getObjectById(UUID id) throws SQLException, ObjectNotFoundException {
        String sqlStatement = "SELECT * FROM COURSE WHERE course_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<Course> course = Optional.ofNullable(courseMapper.mapToCourse(resultSet));

            if (course.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Select course by id failed!"));
                throw new CourseNotFoundException("No course found with the id: " + id);
            } else {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Selected from course!"));
            }

            return course;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM COURSE WHERE course_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Course deleted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Delete course failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void updateObjectById(UUID id, Course newObject) {
        String sqlCourseUpdate = "UPDATE COURSE SET title = ?, discipline = ?, user_id = ? WHERE course_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement courseUpdateStatement = connection.prepareStatement(sqlCourseUpdate)) {

            courseUpdateStatement.setString(1, newObject.getTitle()); //set title
            courseUpdateStatement.setString(2, newObject.getDiscipline().toString()); //set discipline
            courseUpdateStatement.setString(3, newObject.getTeacherId().toString()); //set user_id
            courseUpdateStatement.setString(4, newObject.getCourseId().toString()); //set course_id

            courseUpdateStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Course updated!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Update course failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addNewObject(Course newObject) {
        String sqlCourseInsert = "INSERT INTO COURSE (course_id, title, discipline, user_id) VALUES(?, ?, ?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement courseInsertStatement = connection.prepareStatement(sqlCourseInsert)) {

            courseInsertStatement.setString(1, newObject.getCourseId().toString()); //set course_id
            courseInsertStatement.setString(2, newObject.getTitle()); //set title
            courseInsertStatement.setString(3, newObject.getDiscipline().toString()); //set discipline
            courseInsertStatement.setString(4, newObject.getTeacherId().toString()); //set user_id

            courseInsertStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "1 Course inserted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Insert course failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Course> getAll() {
        String sqlStatement = "SELECT * FROM COURSE";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            return courseMapper.mapToCourseList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void addAllFromGivenList(List<Course> objectList) {
        objectList.forEach(this::addNewObject);
    }
}
