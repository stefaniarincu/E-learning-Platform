package ro.pao.service.users;

import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.abstracts.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserService {
    Optional<User> getById(UUID uuid);

    List<User> getUsersByCourse(Material material);

    List<User> getUsersByDiscipline(Discipline discipline);
}
