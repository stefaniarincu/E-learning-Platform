package ro.pao.service.impl;

import org.postgresql.util.PSQLException;
import ro.pao.exceptions.UserNotFoundException;
import ro.pao.model.sealed.User;
import ro.pao.repository.UserRepository;
import ro.pao.repository.impl.UserRepositoryImpl;
import ro.pao.service.UserService;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class UserServiceImpl implements UserService<User> {
    private final UserRepository<User> userRepository = new UserRepositoryImpl();
    @Override
    public Optional<User> getById(UUID id) {
        try {
            return userRepository.getObjectById(id);
        } catch (UserNotFoundException e){
            LogServiceImpl.getInstance().log(Level.WARNING, e.getMessage());
        } catch (Exception e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
        return Optional.empty();
    }

    @Override
    public List<User> getAllItems() {
        return userRepository.getAll();
    }

    @Override
    public void addOnlyOne(User newObject) {
       try {
           userRepository.addNewObject(newObject);
       } catch (SQLException e) {
           LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
       }
    }

    @Override
    public void addMany(List<User> objectList) {
        try {
            userRepository.addAllFromGivenList(objectList);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void removeById(UUID id) {
        userRepository.deleteObjectById(id);
    }

    @Override
    public void updateById(UUID id, User newObject) {
        userRepository.updateObjectById(id, newObject);
    }

    @Override
    public Optional<User> getByEmail(String email) {
        return Optional.empty();
    }

    @Override
    public boolean emailExists(User user) {
        return false;
    }
}
