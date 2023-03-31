package ro.pao.service.users.impl;

import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.Student;
import ro.pao.model.users.abstracts.User;
import ro.pao.service.users.StudentService;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class StudentServiceImpl implements StudentService {
    private static Map<UUID, Student> studentMap = new LinkedHashMap<>();

    @Override
    public Optional<Student> getById(UUID id) {
        return Optional.ofNullable(studentMap.get(id));
    }

    @Override
    public Optional<Student> getByEmail(String email) {
        return studentMap.values().stream()
                .filter(singleStudent -> singleStudent.getEmail().equals(email))
                .findAny();
    }

    @Override
    public boolean emailExists(Student student) {
        return studentMap.values().stream()
                .anyMatch(singleStudent -> singleStudent.getEmail().equals(student.getEmail())
                            && !singleStudent.getId().equals(student.getId()));
    }

    @Override
    public void addOnlyOne(Student student) {
        if(!emailExists(student)) {
            studentMap.put(student.getId(), student);
        }
        else {
            System.out.println("Email: " + student.getEmail() + " already used!");
        }
    }

    @Override
    public void addMany(LinkedHashMap<UUID, Student> students) {
        for(UUID id: students.keySet()) {
            if(!emailExists(students.get(id))) {
                studentMap.put(id, students.get(id));
            }
            else {
                System.out.println("Email: " + students.get(id).getEmail() + " already used!");
            }
        }
    }

    @Override
    public Map<UUID, Student> getUsersByCourse(Material material) {
        return studentMap.values().stream()
                .filter(singleStudent -> singleStudent.getMaterials().contains(material))
                .collect(Collectors.toMap(Student::getId, Function.identity()));
    }

    @Override
    public Map<UUID, Student> getUsersByDiscipline(Discipline discipline) {
        return studentMap.values().stream()
                .filter(singleStudent -> singleStudent.getMaterials().removeIf(material -> !material.getDiscipline().equals(discipline)))
                .collect(Collectors.toMap(Student::getId, Function.identity()));
    }

    @Override
    public Map<UUID, Student> getStudentsWithLowerGrade(Double averageGrade) {
        return studentMap.values().stream()
                .filter(singleStudent -> singleStudent.getAverageGrade() <= averageGrade)
                .collect(Collectors.toMap(Student::getId, Function.identity()));
    }

    @Override
    public Map<UUID, Student> getStudentsWithHigherGrade(Double averageGrade) {
        return studentMap.values().stream()
                .filter(singleStudent -> singleStudent.getAverageGrade() >= averageGrade)
                .collect(Collectors.toMap(Student::getId, Function.identity()));
    }


}
