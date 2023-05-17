package ro.pao.service.impl;

import ro.pao.model.*;
import ro.pao.model.abstracts.User;
import ro.pao.service.UserService;

import java.sql.SQLException;
import java.util.*;

public class UserServiceImpl implements UserService<User> {
    private final UserService<Student> studentService = new StudentServiceImpl();
    private final UserService<Teacher> teacherService = new TeacherServiceImpl();
    @Override
    public Optional<User> getById(UUID id) throws SQLException {
        Optional<? extends User> user = studentService.getById(id);
        if(user.isPresent())
            return user.map(u -> (User) u);
        return teacherService.getById(id).map(u -> u);
    }

    @Override
    public List<User> getAllItems() throws SQLException {
        List<User> userList = new ArrayList<>();

        userList.addAll(studentService.getAllItems());
        userList.addAll(teacherService.getAllItems());

        return userList;
    }

    @Override
    public void addOnlyOne(User newObject) {
        if (newObject instanceof Student student) {
            studentService.addOnlyOne(student);
        } else {
            teacherService.addOnlyOne((Teacher) newObject);
        }
    }

    @Override
    public void addMany(List<User> objectList) {
        for (User user: objectList) {
            if (user instanceof Student student) {
                studentService.addOnlyOne(student);
            } else {
                teacherService.addOnlyOne((Teacher) user);
            }
        }
    }

    @Override
    public void removeById(UUID id) {
        studentService.removeById(id);
        teacherService.removeById(id);
    }

    @Override
    public void updateById(UUID id, User newObject) {
        if (newObject instanceof Student student) {
            studentService.updateById(id, student);
        } else {
            teacherService.updateById(id, (Teacher) newObject);
        }
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public boolean emailExists(User user) {
        return false;
    }
}
