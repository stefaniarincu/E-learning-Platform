package ro.pao.service.impl;

import ro.pao.model.Test;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.TestType;
import ro.pao.repository.TestRepository;
import ro.pao.repository.impl.TestRepositoryImpl;
import ro.pao.service.TestService;

import java.sql.SQLException;
import java.util.*;

public class TestServiceImpl implements TestService {
    private static final TestRepository testRepository = new TestRepositoryImpl();

    @Override
    public Optional<Test> getById(UUID id) throws SQLException {
        return testRepository.getObjectById(id);
    }

    @Override
    public List<Test> getAllItems() throws SQLException {
        return testRepository.getAll();
    }

    @Override
    public void addOnlyOne(Test newObject) {
        testRepository.addNewObject(newObject);
    }

    @Override
    public void addMany(List<Test> objectList) {
        testRepository.addAllFromGivenList(objectList);
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

