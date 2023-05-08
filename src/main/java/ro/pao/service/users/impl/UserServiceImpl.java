package ro.pao.service.users.impl;

import ro.pao.model.users.Student;
import ro.pao.model.users.Teacher;
import ro.pao.model.users.abstracts.User;
import ro.pao.service.users.UserService;

import java.util.*;

public class UserServiceImpl implements UserService {
    private static final Map<UUID, User> userMap = new LinkedHashMap<>();

    @Override
    public Map<UUID, User> addAllKindOfUsers() {
        StudentServiceImpl studentService = new StudentServiceImpl();
        Map<UUID, Student> students = studentService.getAllItems();
        userMap.putAll(students);

        TeacherServiceImpl teacherService = new TeacherServiceImpl();
        Map<UUID, Teacher> teachers = teacherService.getAllItems();
        userMap.putAll(teachers);

        List<Map.Entry<UUID, User>> entries = new ArrayList<>(userMap.entrySet());

        Collections.sort(entries, new Comparator<Map.Entry<UUID, User>>() {
            public int compare(Map.Entry<UUID, User> e1, Map.Entry<UUID, User> e2) {

                if (e1.getValue().getFirstName().compareTo(e2.getValue().getFirstName()) == 0) {
                    return e1.getValue().getLastName().compareTo(e2.getValue().getLastName());
                }

                return e1.getValue().getFirstName().compareTo(e2.getValue().getFirstName());
            }
        });

        userMap.clear();

        for (Map.Entry<UUID, User> entry : entries) {
            userMap.put(entry.getKey(), entry.getValue());
        }

        return userMap;
    }

    @Override
    public Map<UUID, User> getAllItems() {
        return userMap;
    }
}
