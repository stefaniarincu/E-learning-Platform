package ro.pao.service;

import ro.pao.model.Grade;

import java.util.List;
import java.util.UUID;

public interface GradeService extends ServiceGeneric<Grade> {
    List<Grade> getGradesByStudentId(UUID studentId);
}
