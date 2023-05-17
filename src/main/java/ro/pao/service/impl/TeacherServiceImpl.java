package ro.pao.service.impl;

import ro.pao.model.enums.Discipline;
import ro.pao.model.Teacher;
import ro.pao.repository.TeacherRepository;
import ro.pao.repository.impl.TeacherRepositoryImpl;
import ro.pao.service.TeacherService;

import java.sql.SQLException;
import java.util.*;

public class TeacherServiceImpl implements TeacherService {
    private final static TeacherRepository teacherRepository = new TeacherRepositoryImpl();
    @Override
    public Optional<Teacher> getById(UUID id) throws SQLException {
        return teacherRepository.getObjectById(id);
    }

    @Override
    public List<Teacher> getAllItems() throws SQLException {
        return teacherRepository.getAll();
    }

    @Override
    public void addOnlyOne(Teacher newObject) {
        teacherRepository.addNewObject(newObject);
    }

    @Override
    public void addMany(List<Teacher> objectList) {
        teacherRepository.addAllFromGivenList(objectList);
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
