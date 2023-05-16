package ro.pao.service.impl;

import ro.pao.model.Test;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.TestType;
import ro.pao.service.TestService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestServiceImpl implements TestService {
    private static final Map<UUID, Test> testMap = new HashMap<>();

    @Override
    public Optional<Test> getById(UUID id) {
        return Optional.ofNullable(testMap.getOrDefault(id, null));
    }

    @Override
    public Map<UUID, Test> getAllItems() {
        return testMap;
    }

    @Override
    public Map<UUID, Test> getTestsByType(TestType testType) {
        if (testType == null) {
            return Collections.emptyMap();
        }

        return testMap.values().stream()
                .filter(test -> testType.equals(test.getTestType()))
                .collect(Collectors.toMap(Test::getId, Function.identity()));
    }

    @Override
    public Map<UUID, Test> getMaterialsByDiscipline(Discipline discipline) {
        if (discipline == null) {
            return Collections.emptyMap();
        }

        return testMap.values().stream()
                .filter(test -> discipline.equals(test.getDiscipline()))
                .collect(Collectors.toMap(Test::getId, Function.identity()));
    }

    @Override
    public void addOnlyOne(Test test) {
        if(test == null) {
            System.out.println("The test that you wanted to add is invalid!");
        } else if(testMap.containsKey(test.getId())) {
            System.out.println("The test: --- " + test.getTitle() + " --- that you wanted to add is already in the map!");
        } else {
            testMap.put(test.getId(), test);
        }
    }

    @Override
    public void addMany(Map<UUID, Test> tests) {
        for(UUID id: tests.keySet()) {
            if(tests.get(id) == null) {
                System.out.println("The test that you wanted to add is invalid!");
            } else if(!testMap.containsKey(id)) {
                testMap.put(id, tests.get(id));
            } else {
                System.out.println("The test: --- " + tests.get(id).getTitle() + " --- that you wanted to add is already in the map!");
            }
        }
    }

    @Override
    public void removeById(UUID id) {
        Test test = testMap.get(id);

        if(test == null) {
            System.out.println("The test that you wanted to remove is invalid!");
        } else {
            testMap.remove(id);
        }
    }

    @Override
    public void modifyById(UUID id, Test test) {
        if (test == null) {
            System.out.println("The test you wanted to modify is null!");
        } else if (testMap.containsKey(id)) {
            removeById(id);
            addOnlyOne(test);
        } else {
            System.out.println("The test: --- " + test.getId() + " --- that you wanted to modify does not exist in the map!");
        }
    }
}
