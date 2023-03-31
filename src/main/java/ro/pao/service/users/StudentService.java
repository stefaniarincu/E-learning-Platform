package ro.pao.service.users;

import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.Student;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface StudentService {
    Optional<Student> getById(UUID uuid);

    Optional<Student> getByEmail(String email);

    boolean emailExists(Student student);

    void addOnlyOne(Student student);

    void addMany(LinkedHashMap<UUID, Student> students);

    Map<UUID, Student> getUsersByCourse(Material material);

    Map<UUID,Student> getUsersByDiscipline(Discipline discipline);

    Map<UUID,Student> getStudentsWithLowerGrade(Double averageGrade);

    Map<UUID,Student> getStudentsWithHigherGrade(Double averageGrade);
}
