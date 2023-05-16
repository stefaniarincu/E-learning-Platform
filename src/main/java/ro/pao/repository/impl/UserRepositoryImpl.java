package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.model.Student;
import ro.pao.model.Teacher;
import ro.pao.model.abstracts.User;
import ro.pao.repository.UserRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class UserRepositoryImpl implements UserRepository<User> {
    private final UserRepository<Student> studentRepository = new StudentRepositoryImpl();
    private final UserRepository<Teacher> teacherRepository = new TeacherRepositoryImpl();
    @Override
    public Optional<User> getObjectById(UUID id) throws SQLException {
        Optional<? extends User> user = studentRepository.getObjectById(id);
        if(user.isPresent())
            return user.map(u -> (User) u);
        return teacherRepository.getObjectById(id).map(u -> u);
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM USER WHERE user_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
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
    public void addNewObject(User newObject) {
        if(newObject instanceof Student student) {
            studentRepository.addNewObject(student);
        } else {
            teacherRepository.addNewObject((Teacher) newObject);
        }
    }

    @Override
    public List<User> getAll() throws SQLException {
        List<User> userList = new ArrayList<>();

        userList.addAll(studentRepository.getAll());
        userList.addAll(teacherRepository.getAll());

        return userList;
    }

    @Override
    public void addAllFromGivenList(List<User> objectList) {
        for(User user : objectList) {
            if(user instanceof Student student) {
                studentRepository.addNewObject(student);
            } else {
                teacherRepository.addNewObject((Teacher) user);
            }
        }
    }

    @Override
    public Optional<User> getUserByEmail(String userEmail) {
        return Optional.empty();
    }
}
