package ro.pao.service;

import ro.pao.model.abstracts.User;

import java.util.Optional;

public interface UserService<T extends User> extends ServiceGeneric<T> {
    Optional<T> getByEmail(String email);

    boolean emailExists(T user);
}