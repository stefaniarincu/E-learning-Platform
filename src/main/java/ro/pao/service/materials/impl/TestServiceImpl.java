package ro.pao.service.materials.impl;

import ro.pao.model.materials.Document;
import ro.pao.model.materials.Test;
import ro.pao.model.materials.Video;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.materials.enums.TestType;
import ro.pao.service.materials.TestService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

public class TestServiceImpl implements TestService {
    private static Map<UUID, Test> testMap = new HashMap<>();

    @Override
    public Optional<Test> getById(UUID id) {
        return testMap.values().stream()
                .filter(test -> id.equals(test.getId()))
                .findAny();
    }

    @Override
    public Map<UUID, Test> getAllItems() {
        return testMap;
    }

    @Override
    public Map<UUID, Test> getTestsByType(TestType testType) {
        return testMap.values().stream()
                .filter(test -> testType.equals(test.getTestType()))
                .collect(Collectors.toMap(Test::getId, Function.identity()));
    }

    @Override
    public Map<UUID, Test> getMaterialsByDiscipline(Discipline discipline) {
        return testMap.values().stream()
                .filter(test -> discipline.equals(test.getDiscipline()))
                .collect(Collectors.toMap(Test::getId, Function.identity()));
    }

    @Override
    public void addOnlyOne(Test test) {
        testMap.put(test.getId(), test);
    }

    @Override
    public void addMany(Map<UUID, Test> tests) {
        testMap.putAll(tests);
    }

    @Override
    public void removeById(UUID id) {
        testMap = testMap.entrySet().stream()
                .filter(test -> !id.equals(test.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void modifyById(UUID id, Test test) {
        removeById(id);
        addOnlyOne(test);
    }
}
