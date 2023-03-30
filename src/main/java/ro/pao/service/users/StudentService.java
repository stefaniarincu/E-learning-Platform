package ro.pao.service.users;

import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.Student;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface StudentService {
    Optional<Student> getById(UUID uuid);

    List<Student> getUsersByCourse(Material material);

    List<Student> getUsersByDiscipline(Discipline discipline);
}
