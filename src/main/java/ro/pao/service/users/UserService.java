package ro.pao.service.users;

import ro.pao.model.users.abstracts.User;

import java.util.Map;
import java.util.UUID;

public interface UserService {
    Map<UUID, User> addAllKindOfUsers();

    Map<UUID, User> getAllItems();
}
