package ro.pao.service.impl;

import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Course;
import ro.pao.repository.CourseRepository;
import ro.pao.repository.impl.CourseRepositoryImpl;
import ro.pao.service.CourseService;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class CourseServiceImpl implements CourseService {

    private static final CourseRepository courseRepository = new CourseRepositoryImpl();

    @Override
    public Optional<Course> getById(UUID id) {
        try {
            return courseRepository.getObjectById(id);
        } catch (ObjectNotFoundException e) {
            LogServiceImpl.getInstance().log(Level.WARNING, e.getMessage());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Course> getAllItems() {
        return courseRepository.getAll();
    }

    @Override
    public void addOnlyOne(Course newObject) {
        try {
            courseRepository.addNewObject(newObject);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addMany(List<Course> objectList) {
        try {
            courseRepository.addAllFromGivenList(objectList);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void removeById(UUID id) {
        courseRepository.deleteObjectById(id);
    }

    @Override
    public void updateById(UUID id, Course newObject) {
        courseRepository.updateObjectById(id, newObject);
    }
}
