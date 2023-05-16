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
import java.util.UUID;

public class GradeRepositoryImpl implements GradeRepository {
    private static final GradeMapper gradeMapper = GradeMapper.getInstance();
    @Override
    public List<Grade> getAllByStudentId(UUID studentId) throws SQLException {
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
    public List<Grade> getAllByTestId(UUID testId) throws SQLException {
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
    public List<Grade> getAllByDiscipline(Discipline discipline) throws SQLException {
        String sqlStatement = "SELECT * FROM GRADE g, TEST t WHERE g.test_id = t.material_id AND LOWER(t.discipline) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, discipline.toString());

            return gradeMapper.mapToGradeList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }
}
