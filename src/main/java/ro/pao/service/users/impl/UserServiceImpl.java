package ro.pao.service.users.impl;

import ro.pao.model.users.Student;
import ro.pao.model.users.Teacher;
import ro.pao.model.users.abstracts.User;
import ro.pao.service.users.UserService;

import java.util.*;

public class UserServiceImpl implements UserService {
    private static final Map<UUID, User> userMap = new HashMap<>();

    @Override
    public Map<UUID, User> addAllKindOfUsers() {
        StudentServiceImpl studentService = new StudentServiceImpl();
        Map<UUID, Student> students = studentService.getAllItems();
        userMap.putAll(students);

        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        Map<UUID, Teacher> teachers = teacherService.getAllItems();
        userMap.putAll(teachers);

        return userMap;
    }

    @Override
    public Map<UUID, User> getAllItems() {
        return userMap;
    }
}
