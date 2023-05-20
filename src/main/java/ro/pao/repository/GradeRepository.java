package ro.pao.repository;

import ro.pao.model.Grade;
import ro.pao.model.enums.Discipline;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface GradeRepository extends RepositoryGeneric<Grade>{
    List<Grade> getAllGradesByStudentId(UUID studentId) throws SQLException;

    List<Grade> getAllGradesByTestId(UUID testId) throws SQLException;
}
