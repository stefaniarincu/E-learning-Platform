package ro.pao.service.impl;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Grade;
import ro.pao.model.sealed.Student;
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
import java.util.logging.Level;

public class StudentServiceImpl implements StudentService {
    private final static StudentRepository studentRepository = new StudentRepositoryImpl();
    private final static GradeRepository gradeRepository = new GradeRepositoryImpl();
    private final static MaterialRepository<Material> materialRepository = new MaterialRepositoryImpl();
    @Override
    public Optional<Student> getById(UUID id) {
        try {
            Optional<Student> studentOptional = studentRepository.getObjectById(id);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                List<Grade> grades = gradeRepository.getAllGradesByStudentId(id);
                List<Material> materials = materialRepository.getAllMaterialsByStudentId(id);

                student.setGradeList(grades);
                student.setMaterialList(materials);

                return Optional.of(student);
            }
            return Optional.empty();
        } catch (ObjectNotFoundException e) {
            LogServiceImpl.getInstance().log(Level.INFO, e.getMessage());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Student> getAllItems() {
        List<Student> studentList = studentRepository.getAll();

        Iterator<Student> studentIterator = studentList.iterator();
        while (studentIterator.hasNext()) {
            Student student = studentIterator.next();

            List<Grade> grades = gradeRepository.getAllGradesByStudentId(student.getId());
            List<Material> materials = materialRepository.getAllMaterialsByStudentId(student.getId());

            student.setGradeList(grades);
            student.setMaterialList(materials);
        }

        return studentList;
    }

    @Override
    public void addOnlyOne(Student newObject) {
        try {
            studentRepository.addNewObject(newObject);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addMany(List<Student> objectList) {
        try {
            studentRepository.addAllFromGivenList(objectList);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
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
    public List<Student> getStudentsWithLowerGrade(Double averageGrade) {
        List<Student> studentList = studentRepository.getAllStudentsWithLowerAvgGrade(averageGrade);

        Iterator<Student> studentIterator = studentList.iterator();
        while (studentIterator.hasNext()) {
            Student student = studentIterator.next();

            List<Grade> grades = gradeRepository.getAllGradesByStudentId(student.getId());
            List<Material> materials = materialRepository.getAllMaterialsByStudentId(student.getId());

            student.setGradeList(grades);
            student.setMaterialList(materials);
        }

        return studentList;
    }

    @Override
    public List<Student> getStudentsWithHigherGrade(Double averageGrade) {
        List<Student> studentList = studentRepository.getAllStudentsWithHigherAvgGrade(averageGrade);

        Iterator<Student> studentIterator = studentList.iterator();
        while (studentIterator.hasNext()) {
            Student student = studentIterator.next();

            List<Grade> grades = gradeRepository.getAllGradesByStudentId(student.getId());
            List<Material> materials = materialRepository.getAllMaterialsByStudentId(student.getId());

            student.setGradeList(grades);
            student.setMaterialList(materials);
        }

        return studentList;
    }

    @Override
    public void enrollStudent(UUID studentId, UUID courseId) {
        studentRepository.enrollStudentToCourse(studentId, courseId);
    }

    @Override
    public void updateStudentGradeList(UUID studentId) {
        Optional<Student> studentOptional = getById(studentId);

        if (studentOptional.isPresent()) {
            Student student = studentOptional.get();

            List<Grade> grades = student.getGradeList();

            student.setGradeList(grades);

            double averageGrade = student.calculateAverageGrade();
            student.setAverageGrade(averageGrade);

            studentRepository.updateStudentAverageGrade(studentId, averageGrade);
        }
    }

    @Override
    public Optional<Student> getByEmail(String email) {
        try {
            Optional<Student> studentOptional = studentRepository.getUserByEmail(email);
            if (studentOptional.isPresent()) {
                Student student = studentOptional.get();
                List<Grade> grades = gradeRepository.getAllGradesByStudentId(student.getId());
                List<Material> materials = materialRepository.getAllMaterialsByStudentId(student.getId());

                student.setGradeList(grades);
                student.setMaterialList(materials);

                return Optional.of(student);
            }
            return Optional.empty();
        } catch (ObjectNotFoundException e) {
            LogServiceImpl.getInstance().log(Level.INFO, e.getMessage());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
        return Optional.empty();
    }
}
