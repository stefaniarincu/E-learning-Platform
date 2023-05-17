package ro.pao.service.impl;
import ro.pao.model.Student;
import ro.pao.repository.StudentRepository;
import ro.pao.repository.impl.StudentRepositoryImpl;
import ro.pao.service.StudentService;

import java.sql.SQLException;
import java.util.*;

public class StudentServiceImpl implements StudentService {
    private final static StudentRepository studentRepository = new StudentRepositoryImpl();
    @Override
    public Optional<Student> getById(UUID id) throws SQLException {
        return studentRepository.getObjectById(id);
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
