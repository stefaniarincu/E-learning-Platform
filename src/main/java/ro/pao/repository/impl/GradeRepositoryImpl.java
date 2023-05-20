package ro.pao.repository.impl;

import ro.pao.application.csv.CsvLogger;
import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.GradeNotFoundException;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.mapper.GradeMapper;
import ro.pao.model.Grade;
import ro.pao.repository.GradeRepository;
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

public class GradeRepositoryImpl implements GradeRepository {
    private static final GradeMapper gradeMapper = GradeMapper.getInstance();

    @Override
    public List<Grade> getAllGradesByStudentId(UUID studentId) {
        String sqlStatement = "SELECT * FROM GRADE WHERE user_id LIKE ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, studentId.toString());

            return gradeMapper.mapToGradeList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public List<Grade> getAllGradesByTestId(UUID testId) {
        String sqlStatement = "SELECT * FROM GRADE WHERE material_id LIKE ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, testId.toString());

            return gradeMapper.mapToGradeList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public Optional<Grade> getObjectById(UUID id) throws SQLException, ObjectNotFoundException {
        String sqlStatement = "SELECT * FROM GRADE WHERE grade_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<Grade> grade = Optional.ofNullable(gradeMapper.mapToGrade(resultSet));

            if (grade.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Select grade by id failed!"));
                throw new GradeNotFoundException("No grade found with the id: " + id);
            } else {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Selected from Grade!"));
            }

            return grade;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM GRADE WHERE grade_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Grade deleted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Delete grade failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void updateObjectById(UUID id, Grade newObject) {
        String sqlGradeUpdate = "UPDATE GRADE SET user_id = ?, material_id = ?, grade = ?, weight = ? WHERE grade_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement gradeUpdateStatement = connection.prepareStatement(sqlGradeUpdate)) {

            gradeUpdateStatement.setString(1, newObject.getStudentId().toString()); //set student_id
            gradeUpdateStatement.setString(2, newObject.getTestId().toString()); //set test_id
            gradeUpdateStatement.setDouble(3, newObject.getGrade()); //set grade
            gradeUpdateStatement.setDouble(4, newObject.getWeight()); //set weight
            gradeUpdateStatement.setString(5, newObject.getGradeId().toString()); //set grade_id

            gradeUpdateStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Grade updated!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Update grade failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addNewObject(Grade newObject) {
        String sqlGradeInsert = "INSERT INTO GRADE (grade_id, user_id, material_id, grade, weight) VALUES(?, ?, ?, ?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement gradeInsertStatement = connection.prepareStatement(sqlGradeInsert)) {

            gradeInsertStatement.setString(1, newObject.getGradeId().toString()); //set grade_id
            gradeInsertStatement.setString(2, newObject.getStudentId().toString()); //set student_id
            gradeInsertStatement.setString(3, newObject.getTestId().toString()); //set test_id
            gradeInsertStatement.setDouble(4, newObject.getGrade()); //set grade
            gradeInsertStatement.setDouble(5, newObject.getWeight()); //set weight

            gradeInsertStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "1 Grade inserted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Insert grade failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Grade> getAll() {
        String sqlStatement = "SELECT * FROM GRADE";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            return gradeMapper.mapToGradeList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void addAllFromGivenList(List<Grade> objectList) {
        objectList.forEach(this::addNewObject);
    }
}
