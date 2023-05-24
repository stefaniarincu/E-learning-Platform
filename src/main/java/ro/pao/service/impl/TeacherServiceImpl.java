package ro.pao.service.impl;

import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Course;
import ro.pao.model.sealed.Teacher;
import ro.pao.repository.CourseRepository;
import ro.pao.repository.TeacherRepository;
import ro.pao.repository.impl.CourseRepositoryImpl;
import ro.pao.repository.impl.TeacherRepositoryImpl;
import ro.pao.service.TeacherService;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class TeacherServiceImpl implements TeacherService {
    private final static TeacherRepository teacherRepository = new TeacherRepositoryImpl();
    private final static CourseRepository courseRepository = new CourseRepositoryImpl();
    @Override
    public Optional<Teacher> getById(UUID id) {
        try {
            Optional<Teacher> teacherOptional = teacherRepository.getObjectById(id);

            if (teacherOptional.isPresent()) {
                Teacher teacher = teacherOptional.get();
                List<Course> courses = courseRepository.getAllCoursesByTeacherId(id);

                teacher.setTeachCourses(courses);

                return Optional.of(teacher);
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
    public List<Teacher> getAllItems() {
        List<Teacher> teacherList = teacherRepository.getAll();

        Iterator<Teacher> teacherIterator = teacherList.iterator();
        while (teacherIterator.hasNext()) {
            Teacher teacher = teacherIterator.next();

            List<Course> courses = courseRepository.getAllCoursesByTeacherId(teacher.getId());

            teacher.setTeachCourses(courses);
        }

        return teacherList;
    }

    @Override
    public void addOnlyOne(Teacher newObject) {
        try {
            teacherRepository.addNewObject(newObject);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addMany(List<Teacher> objectList) {
        try {
            teacherRepository.addAllFromGivenList(objectList);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void removeById(UUID id) {
        teacherRepository.deleteObjectById(id);
    }

    @Override
    public void updateById(UUID id, Teacher newObject) {
        teacherRepository.updateObjectById(id, newObject);
    }

    @Override
    public List<Teacher> getTeachersByDegree(String degree) {
        List<Teacher> teacherList = teacherRepository.getAllTeachersByDegree(degree);

        Iterator<Teacher> teacherIterator = teacherList.iterator();
        while (teacherIterator.hasNext()) {
            Teacher teacher = teacherIterator.next();

            List<Course> courses = courseRepository.getAllCoursesByTeacherId(teacher.getId());

            teacher.setTeachCourses(courses);
        }

        return teacherList;
    }

    @Override
    public Optional<Teacher> getByEmail(String email) {
        try {
            Optional<Teacher> teacherOptional = teacherRepository.getUserByEmail(email);

            if (teacherOptional.isPresent()) {
                Teacher teacher = teacherOptional.get();
                List<Course> courses = courseRepository.getAllCoursesByTeacherId(teacher.getId());

                teacher.setTeachCourses(courses);

                return Optional.of(teacher);
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
