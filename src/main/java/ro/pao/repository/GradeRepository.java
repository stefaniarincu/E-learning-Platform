package ro.pao.repository;

import ro.pao.model.Grade;

import java.util.List;
import java.util.UUID;

public interface GradeRepository extends RepositoryGeneric<Grade>{
    List<Grade> getAllGradesByStudentId(UUID studentId);
}
