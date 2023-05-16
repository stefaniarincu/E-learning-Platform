package ro.pao.service;

import ro.pao.model.abstracts.User;

import java.util.Map;
import java.util.UUID;

public interface UserService {
    Map<UUID, User> addAllKindOfUsers();

    Map<UUID, User> getAllItems();
}
