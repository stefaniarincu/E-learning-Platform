package ro.pao.service;

import ro.pao.model.enums.Discipline;
import ro.pao.model.Student;

import java.util.*;

public interface StudentService {
    Optional<Student> getById(UUID uuid);

    Optional<Student> getByEmail(String email);

    boolean emailExists(Student student);

    void addOnlyOne(Student student);

    void addMany(LinkedHashMap<UUID, Student> students);

    LinkedHashMap<UUID, Student> getAllItems();

    Map<UUID, Student> getUsersByMaterialId(UUID materialId);

    Map<UUID,Student> getUsersByDiscipline(Discipline discipline);

    List<Student> getStudentsWithLowerGrade(Double averageGrade);

    List<Student> getStudentsWithHigherGrade(Double averageGrade);

    void removeById(UUID id);

    void modifyById(UUID id, Student student);
}
