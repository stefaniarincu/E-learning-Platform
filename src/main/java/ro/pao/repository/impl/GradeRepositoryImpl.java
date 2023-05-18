package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.mapper.GradeMapper;
import ro.pao.model.Grade;
import ro.pao.model.enums.Discipline;
import ro.pao.repository.GradeRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class GradeRepositoryImpl implements GradeRepository {
    private static final GradeMapper gradeMapper = GradeMapper.getInstance();

    @Override
    public List<Grade> getAllGradesByStudentId(UUID studentId) throws SQLException {
        String sqlStatement = "SELECT * FROM GRADE WHERE student_id LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, studentId.toString());

            return gradeMapper.mapToGradeList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<Grade> getAllGradesByTestId(UUID testId) throws SQLException {
        String sqlStatement = "SELECT * FROM GRADE WHERE test_id LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, testId.toString());

            return gradeMapper.mapToGradeList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<Grade> getAllGradesByDiscipline(Discipline discipline) throws SQLException {
        String sqlStatement = "SELECT * FROM GRADE g, TEST t WHERE g.test_id = t.material_id AND LOWER(t.discipline) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, discipline.toString());

            return gradeMapper.mapToGradeList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public Optional<Grade> getObjectById(UUID id) throws SQLException {
        String sqlStatement = "SELECT * FROM GRADE WHERE grade_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            return Optional.ofNullable(gradeMapper.mapToGrade(preparedStatement.executeQuery()));
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM GRADE WHERE grade_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateObjectById(UUID id, Grade newObject) {
        String sqlGradeUpdate = "UPDATE GRADE SET student_id = ?, test_id = ?, grade = ? WHERE grade_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement gradeUpdateStatement = connection.prepareStatement(sqlGradeUpdate)) {

            gradeUpdateStatement.setString(1, newObject.getStudentId().toString()); //set student_id
            gradeUpdateStatement.setString(2, newObject.getTestId().toString()); //set test_id
            gradeUpdateStatement.setDouble(3, newObject.getGrade()); //set grade
            gradeUpdateStatement.setString(4, newObject.getGradeId().toString()); //set grade_id

            gradeUpdateStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewObject(Grade newObject) {
        String sqlGradeInsert = "INSERT INTO GRADE (grade_id, student_id, test_id, grade) VALUES(?, ?, ?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement gradeInsertStatement = connection.prepareStatement(sqlGradeInsert)) {

            gradeInsertStatement.setString(1, newObject.getGradeId().toString()); //set grade_id
            gradeInsertStatement.setString(2, newObject.getStudentId().toString()); //set student_id
            gradeInsertStatement.setString(3, newObject.getTestId().toString()); //set test_id
            gradeInsertStatement.setDouble(4, newObject.getGrade()); //set grade

            gradeInsertStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Grade> getAll() throws SQLException {
        String sqlStatement = "SELECT * FROM GRADE";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            return gradeMapper.mapToGradeList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void addAllFromGivenList(List<Grade> objectList) {
        objectList.forEach(this::addNewObject);
    }
}
