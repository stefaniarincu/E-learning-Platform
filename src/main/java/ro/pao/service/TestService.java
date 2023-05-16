package ro.pao.service;

import ro.pao.model.Test;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.TestType;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface TestService {

    Optional<Test> getById(UUID id);

    Map<UUID, Test> getAllItems();

    Map<UUID, Test> getTestsByType(TestType testType);

    Map<UUID, Test> getMaterialsByDiscipline(Discipline discipline);

    void addOnlyOne(Test test);

    void addMany(Map<UUID, Test> tests);

    void removeById(UUID id);

    void modifyById(UUID id, Test test);
}
