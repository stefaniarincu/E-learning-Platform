package ro.pao.service.users.impl;

import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.Teacher;
import ro.pao.service.users.TeacherService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TeacherServiceImpl implements TeacherService {
    private static final LinkedHashMap<UUID, Teacher> teacherMap = new LinkedHashMap<>();
    private static final Map<String, Teacher> teacherEmailMap = new HashMap<>();

    @Override
    public Optional<Teacher> getById(UUID id) {
        if (id == null) {
            return Optional.empty();
        }
        return Optional.ofNullable(teacherMap.get(id));
    }

    @Override
    public Optional<Teacher> getByEmail(String email) {
        if (email == null) {
            return Optional.empty();
        }

        return teacherEmailMap.values().stream()
                .filter(singleStudent -> email.equals(singleStudent.getEmail()))
                .findAny();
    }

    @Override
    public boolean emailExists(Teacher teacher) {
        if (teacher == null) {
            return false;
        }

        return teacherEmailMap.values().stream()
                .anyMatch(singleStudent -> singleStudent.getEmail().equals(teacher.getEmail()));
    }

    @Override
    public void addOnlyOne(Teacher teacher) {
        if (!emailExists(teacher)) {
            teacherMap.put(teacher.getId(), teacher);
            teacherEmailMap.put(teacher.getEmail(), teacher);
        } else {
            System.out.println("\nEmail: " + teacher.getEmail() + " already used!");
        }
    }

    @Override
    public void addMany(LinkedHashMap<UUID, Teacher> teachers) {
        for(Map.Entry<UUID, Teacher> entry : teachers.entrySet()) {
            Teacher teacher = entry.getValue();

            if (!emailExists(teacher)) {
                teacherMap.put(entry.getKey(), teacher);
                teacherEmailMap.put(teacher.getEmail(), teacher);
            } else {
                System.out.println("\nEmail: " + teacher.getEmail() + " already used!");
            }
        }
    }

    @Override
    public LinkedHashMap<UUID, Teacher> getAllItems() {
        return teacherMap;
    }

    @Override
    public Map<UUID, Teacher> getUsersByDiscipline(Discipline discipline) {
        if(discipline == null) {
            return Collections.emptyMap();
        }

        return teacherMap.values().stream()
                .filter(singleTeacher -> singleTeacher.getTeachCourses().contains(discipline))
                .collect(Collectors.toMap(Teacher::getId, Function.identity()));
    }

    @Override
    public void removeById(UUID id) {
        Teacher teacher = teacherMap.get(id);

        if(teacher == null) {
            System.out.println("\nThe teacher account that you wanted to delete is invalid!");
        } else {
            teacherMap.remove(id);
        }
    }

    @Override
    public void modifyById(UUID id, Teacher teacher) {
        if (teacher == null) {
            System.out.println("\nThe teacher account you wanted to modify is null!");
        } else if (teacherMap.containsKey(id)) {
            removeById(id);
            addOnlyOne(teacher);
        } else {
            System.out.println("\nThe teacher account: --- " + teacher.getId() + " --- that you wanted to modify does not exist in the map!");
        }
    }
}
