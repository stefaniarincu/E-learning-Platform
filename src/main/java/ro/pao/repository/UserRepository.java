package ro.pao.repository;

import ro.pao.model.abstracts.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository<T extends User> extends RepositoryGeneric<T>{
    Optional<T> getUserByEmail(String userEmail) throws SQLException;
}
