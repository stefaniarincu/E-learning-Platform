package ro.pao.service.users;

import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.Teacher;
import ro.pao.model.users.abstracts.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface TeacherService {
    Optional<Teacher> getById(UUID uuid);

    List<Teacher> getUsersByCourse(Material material);

    List<Teacher> getUsersByDiscipline(Discipline discipline);
}
