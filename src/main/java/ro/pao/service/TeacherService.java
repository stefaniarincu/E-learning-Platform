package ro.pao.service;

import ro.pao.model.enums.Discipline;
import ro.pao.model.Teacher;

import java.util.*;

public interface TeacherService {

    Optional<Teacher> getById(UUID uuid);

    Optional<Teacher> getByEmail(String email);

    boolean emailExists(Teacher teacher);

    void addOnlyOne(Teacher teacher);

    void addMany(LinkedHashMap<UUID, Teacher> teachers);

    LinkedHashMap<UUID, Teacher> getAllItems();

    Map<UUID, Teacher> getUsersByDiscipline(Discipline discipline);

    void removeById(UUID id);

    void modifyById(UUID id, Teacher teacher);
}
