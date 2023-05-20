package ro.pao.repository;

import ro.pao.exceptions.UserNotFoundException;
import ro.pao.model.sealed.User;

import java.sql.SQLException;
import java.util.Optional;

public interface UserRepository<T extends User> extends RepositoryGeneric<T>{
    Optional<T> getUserByEmail(String userEmail) throws SQLException, UserNotFoundException;
}
