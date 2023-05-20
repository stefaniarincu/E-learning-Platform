package ro.pao.service.impl;

import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.enums.Discipline;
import ro.pao.model.sealed.Teacher;
import ro.pao.repository.TeacherRepository;
import ro.pao.repository.impl.TeacherRepositoryImpl;
import ro.pao.service.TeacherService;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class TeacherServiceImpl implements TeacherService {
    private final static TeacherRepository teacherRepository = new TeacherRepositoryImpl();
    @Override
    public Optional<Teacher> getById(UUID id) {
        try {
            return teacherRepository.getObjectById(id);
        } catch (ObjectNotFoundException e) {
            LogServiceImpl.getInstance().log(Level.INFO, e.getMessage());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<Teacher> getAllItems() {
        return teacherRepository.getAll();
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
    public List<Teacher> getTeachersByDiscipline(Discipline discipline) {
        return null;
    }

    @Override
    public Optional<Teacher> getByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public boolean emailExists(Teacher user) {
        return false;
    }
}
