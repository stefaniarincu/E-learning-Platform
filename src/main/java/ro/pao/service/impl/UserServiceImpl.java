package ro.pao.service.impl;

import ro.pao.exceptions.UserNotFoundException;
import ro.pao.model.Document;
import ro.pao.model.Test;
import ro.pao.model.Video;
import ro.pao.model.abstracts.Material;
import ro.pao.model.sealed.Student;
import ro.pao.model.sealed.Teacher;
import ro.pao.model.sealed.User;
import ro.pao.repository.UserRepository;
import ro.pao.repository.impl.UserRepositoryImpl;
import ro.pao.service.StudentService;
import ro.pao.service.UserService;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class UserServiceImpl implements UserService<User> {
    private final UserService<Teacher> teacherService = new TeacherServiceImpl();
    private final UserService<Student> studentService = new StudentServiceImpl();
    @Override
    public Optional<User> getById(UUID id) {
        Optional<? extends User> user = studentService.getById(id);
        if (user.isPresent())
            return user.map(u -> (User) u);

        return teacherService.getById(id).map(u -> u);
    }

    @Override
    public List<User> getAllItems() {
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
        Optional<? extends User> user = studentService.getByEmail(email);
        if (user.isPresent())
            return user.map(u -> (User) u);

        return teacherService.getByEmail(email).map(u -> u);
    }
}
