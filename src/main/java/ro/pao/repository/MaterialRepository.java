package ro.pao.repository;

import ro.pao.model.abstracts.Material;
import ro.pao.model.enums.Discipline;

import java.util.List;
import java.util.UUID;

public interface MaterialRepository<T extends Material> extends RepositoryGeneric<T> {
    List<T> getAllMaterialsByDiscipline(Discipline discipline);

    List<T> getMaterialByTeacher(UUID teacherId);
}
