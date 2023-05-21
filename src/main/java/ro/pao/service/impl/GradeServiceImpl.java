package ro.pao.service.impl;

import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Grade;
import ro.pao.repository.GradeRepository;
import ro.pao.repository.impl.GradeRepositoryImpl;
import ro.pao.service.GradeService;
import ro.pao.service.StudentService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class GradeServiceImpl implements GradeService {
    private static final GradeRepository gradeRepository = new GradeRepositoryImpl();
    private static final StudentService studentService = new StudentServiceImpl();
    @Override
    public Optional<Grade> getById(UUID id) {
        try {
            return gradeRepository.getObjectById(id);
        } catch (ObjectNotFoundException e) {
            LogServiceImpl.getInstance().log(Level.WARNING, e.getMessage());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Grade> getAllItems() {
        return gradeRepository.getAll();
    }

    @Override
    public void addOnlyOne(Grade newObject) {
        try {
            gradeRepository.addNewObject(newObject);

            studentService.updateStudentGradeList(newObject.getStudentId());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addMany(List<Grade> objectList) {
        for (Grade grade : objectList) {
            addOnlyOne(grade);
        }
    }

    @Override
    public void removeById(UUID id) {
        Optional<Grade> gradeOptional = getById(id);

        if (gradeOptional.isPresent()) {
            Grade grade = gradeOptional.get();

            gradeRepository.deleteObjectById(id);

            studentService.updateStudentGradeList(grade.getStudentId());
        }
    }

    @Override
    public List<Grade> getGradesByStudentId(UUID studentId) {
        return gradeRepository.getAllGradesByStudentId(studentId);
    }

    @Override
    public void updateById(UUID id, Grade newObject) {
        Optional<Grade> gradeOptional = getById(id);

        if (gradeOptional.isPresent()) {
            Grade grade = gradeOptional.get();

            gradeRepository.updateObjectById(id, newObject);

            if (grade.getStudentId().toString().equals(newObject.getStudentId().toString())) {
                studentService.updateStudentGradeList(grade.getStudentId());
            } else {
                studentService.updateStudentGradeList(grade.getStudentId());

                studentService.updateStudentGradeList(newObject.getStudentId());
            }
        }
    }
}
