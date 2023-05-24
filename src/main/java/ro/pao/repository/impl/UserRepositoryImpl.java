package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.exceptions.UserNotFoundException;
import ro.pao.model.sealed.Student;
import ro.pao.model.sealed.Teacher;
import ro.pao.model.sealed.User;
import ro.pao.repository.UserRepository;
import ro.pao.service.impl.LogServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class UserRepositoryImpl implements UserRepository<User> {
    private final UserRepository<Student> studentRepository = new StudentRepositoryImpl();
    private final UserRepository<Teacher> teacherRepository = new TeacherRepositoryImpl();
    @Override
    public Optional<User> getObjectById(UUID id) throws SQLException, ObjectNotFoundException {
        try {
            Optional<? extends User> user = studentRepository.getObjectById(id);

            if (user.isPresent())
                return user.map(u -> (User) u);
        } catch (UserNotFoundException e1) {
            try {
                return teacherRepository.getObjectById(id).map(u -> u);
            } catch (UserNotFoundException e2) {
                throw new UserNotFoundException("No user found with the id: " + id);
            }
        }

        return Optional.empty();
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM _USER WHERE user_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void updateObjectById(UUID id, User newObject) {
        if(newObject instanceof Student student) {
            studentRepository.updateObjectById(id, student);
        } else {
            teacherRepository.updateObjectById(id, (Teacher) newObject);
        }
    }

    @Override
    public void addNewObject(User newObject) throws SQLException {
        if(newObject instanceof Student student) {
            studentRepository.addNewObject(student);
        } else {
            teacherRepository.addNewObject((Teacher) newObject);
        }
    }

    @Override
    public List<User> getAll() {
        List<User> userList = new ArrayList<>();

        userList.addAll(studentRepository.getAll());
        userList.addAll(teacherRepository.getAll());

        return userList;
    }

    @Override
    public void addAllFromGivenList(List<User> objectList) throws SQLException {
        for(User user : objectList) {
            if(user instanceof Student student) {
                studentRepository.addNewObject(student);
            } else {
                teacherRepository.addNewObject((Teacher) user);
            }
        }
    }

    @Override
    public Optional<User> getUserByEmail(String userEmail) throws SQLException, UserNotFoundException {
        Optional<? extends User> user = studentRepository.getUserByEmail(userEmail);

        if (user.isPresent())
            return user.map(u -> (User) u);

        return teacherRepository.getUserByEmail(userEmail).map(u -> u);
    }
}
