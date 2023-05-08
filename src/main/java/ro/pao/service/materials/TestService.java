package ro.pao.service.materials;

import ro.pao.model.materials.Document;
import ro.pao.model.materials.Test;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.materials.enums.DocumentType;
import ro.pao.model.materials.enums.TestType;

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
