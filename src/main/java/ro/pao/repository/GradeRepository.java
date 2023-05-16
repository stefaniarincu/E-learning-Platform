package ro.pao.repository;

import ro.pao.model.Grade;
import ro.pao.model.enums.Discipline;

import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

public interface GradeRepository {
    List<Grade> getAllByStudentId(UUID studentId) throws SQLException;

    List<Grade> getAllByTestId(UUID testId) throws SQLException;

    List<Grade> getAllByDiscipline(Discipline discipline) throws SQLException;

}
