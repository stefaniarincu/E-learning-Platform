package ro.pao.service.impl;
import ro.pao.model.Grade;
import ro.pao.model.Student;
import ro.pao.model.abstracts.Material;
import ro.pao.repository.GradeRepository;
import ro.pao.repository.MaterialRepository;
import ro.pao.repository.StudentRepository;
import ro.pao.repository.impl.GradeRepositoryImpl;
import ro.pao.repository.impl.MaterialRepositoryImpl;
import ro.pao.repository.impl.StudentRepositoryImpl;
import ro.pao.service.StudentService;

import java.sql.SQLException;
import java.util.*;

public class StudentServiceImpl implements StudentService {
    private final static StudentRepository studentRepository = new StudentRepositoryImpl();
    private final static GradeRepository gradeRepository = new GradeRepositoryImpl();
    private final static MaterialRepository<Material> materialRepository = new MaterialRepositoryImpl();
    @Override
    public Optional<Student> getById(UUID id) throws SQLException {
        Optional<Student> studentOptional = studentRepository.getObjectById(id);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();
            List<Grade> grades = gradeRepository.getAllGradesByStudentId(id);
            List<Material> materials = materialRepository.getAllMaterialsByStudentId(id);

            student.setGrades(grades);
            student.setMaterials(materials);

            return Optional.of(student);
        }
        return Optional.empty();
    }

    @Override
    public List<Student> getAllItems() throws SQLException {
        return studentRepository.getAll();
    }

    @Override
    public void addOnlyOne(Student newObject) {
        studentRepository.addNewObject(newObject);
    }

    @Override
    public void addMany(List<Student> objectList) {
        studentRepository.addAllFromGivenList(objectList);
    }

    @Override
    public void removeById(UUID id) {
        studentRepository.deleteObjectById(id);
    }

    @Override
    public void updateById(UUID id, Student newObject) {
        studentRepository.updateObjectById(id, newObject);
    }

    @Override
    public List<Student> getStudentsByMaterialId(UUID materialId) {
        return null;
    }

    @Override
    public List<Student> getStudentsWithLowerGrade(Double averageGrade) {
        return null;
    }

    @Override
    public List<Student> getStudentsWithHigherGrade(Double averageGrade) {
        return null;
    }

    @Override
    public Optional<Student> getByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public boolean emailExists(Student user) {
        return false;
    }
}
