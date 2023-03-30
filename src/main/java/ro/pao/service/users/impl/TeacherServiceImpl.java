package ro.pao.service.users.impl;

import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.Student;
import ro.pao.model.users.Teacher;
import ro.pao.service.users.TeacherService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TeacherServiceImpl implements TeacherService {
    private static List<Teacher> teacherList = new ArrayList<>();
    @Override
    public Optional<Teacher> getById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public List<Teacher> getUsersByCourse(Material material) {
        return teacherList.stream()
                .filter(singleTeacher -> singleTeacher.getMaterials().contains(material))
                .toList();
    }

    @Override
    public List<Teacher> getUsersByDiscipline(Discipline discipline) {
        return teacherList.stream()
                .filter(singleTeacher -> singleTeacher.getMaterials().removeIf(material -> !material.getDiscipline().equals(discipline)))
                .toList();
    }
}
