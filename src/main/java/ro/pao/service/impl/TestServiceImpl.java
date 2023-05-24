package ro.pao.service.impl;

import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Test;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.TestType;
import ro.pao.repository.TestRepository;
import ro.pao.repository.impl.TestRepositoryImpl;
import ro.pao.service.TestService;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class TestServiceImpl implements TestService {
    private static final TestRepository testRepository = new TestRepositoryImpl();

    @Override
    public Optional<Test> getById(UUID id) {
        try {
            return testRepository.getObjectById(id);
        } catch (ObjectNotFoundException e) {
            LogServiceImpl.getInstance().log(Level.WARNING, e.getMessage());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Test> getAllItems() {
        return testRepository.getAll();
    }

    @Override
    public void addOnlyOne(Test newObject) {
        try {
            testRepository.addNewObject(newObject);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addMany(List<Test> objectList) {
        try {
            testRepository.addAllFromGivenList(objectList);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void removeById(UUID id) {
        testRepository.deleteObjectById(id);
    }

    @Override
    public void updateById(UUID id, Test newObject) {
        testRepository.updateObjectById(id, newObject);
    }

    @Override
    public List<Test> getAllTestsByType(TestType testType) throws SQLException {
        return testRepository.getAllTestsByType(testType);
    }

    @Override
    public List<Test> getAllMaterialsByDiscipline(Discipline discipline) {
        return testRepository.getAllMaterialsByDiscipline(discipline);
    }
}

