package ro.pao.repository;

import ro.pao.model.abstracts.User;

import java.util.Optional;

public interface UserRepository<T extends User> extends RepositoryGeneric<T>{
    Optional<T> getUserByEmail(String userEmail);
}
