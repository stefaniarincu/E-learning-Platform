package ro.pao.service.users.impl;

import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.Student;
import ro.pao.model.users.abstracts.User;
import ro.pao.service.users.StudentService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class StudentServiceImpl implements StudentService {
    private static List<Student> studentList = new ArrayList<>();
    @Override
    public Optional<Student> getById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public List<Student> getUsersByCourse(Material material) {
        return studentList.stream()
                .filter(singleStudent -> singleStudent.getMaterials().contains(material))
                .toList();
    }

    @Override
    public List<Student> getUsersByDiscipline(Discipline discipline) {
        return studentList.stream()
                .filter(singleStudent -> singleStudent.getMaterials().removeIf(material -> !material.getDiscipline().equals(discipline)))
                .toList();
    }
}
