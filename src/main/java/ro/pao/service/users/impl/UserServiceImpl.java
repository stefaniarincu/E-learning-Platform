package ro.pao.service.users.impl;

import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.users.abstracts.User;
import ro.pao.service.users.UserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    private static List<User> userList = new ArrayList<>();

    @Override
    public Optional<User> getById(UUID uuid) {
        return Optional.empty();
    }

    @Override
    public List<User> getUsersByCourse(Material material) {
        return userList.stream()
                .filter(singleUser -> singleUser.getMaterials().contains(material))
                .toList();
    }

    @Override
    public List<User> getUsersByDiscipline(Discipline discipline) {
        return userList.stream()
                .filter(singleUser -> singleUser.getMaterials().removeIf(material -> !material.getDiscipline().equals(discipline)))
                .toList();
    }
}
