package ro.pao.repository.impl;

import ro.pao.application.csv.CsvLogger;
import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.MaterialNotFoundException;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.mapper.MaterialMapper;
import ro.pao.model.Test;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.TestType;
import ro.pao.repository.TestRepository;
import ro.pao.service.impl.LogServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class TestRepositoryImpl implements TestRepository {
    private static final MaterialMapper materialMapper = MaterialMapper.getInstance();
    @Override
    public Optional<Test> getObjectById(UUID id) throws SQLException, ObjectNotFoundException {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN TEST t ON m.material_id = t.material_id WHERE m.material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<Test> test = Optional.ofNullable(materialMapper.mapToTest(resultSet));

            if (test.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Select test by id failed!"));
                throw new MaterialNotFoundException("No test found with the id: " + id);
            } else {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Selected from test!"));
            }

            return test;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM MATERIAL WHERE material_id = ? AND LOWER(material_type) LIKE 'test'";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Test deleted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Delete test failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Test> getAllMaterialsByStudentId(UUID studentId) {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN COURSE c ON m.course_id = c.course_id WHERE LOWER(m.material_type) LIKE ? AND c.course_id IN (SELECT * FROM ENROLLED WHERE user_id = ?)";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "test");
            preparedStatement.setString(2, studentId.toString());

            return materialMapper.mapToTestList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void updateObjectById(UUID id, Test newObject) {
        String sqlMaterialUpdate = "UPDATE MATERIAL SET creation_time = ?, title = ?, description = ?, course_id = ? WHERE material_id = ?";
        String sqlTestUpdate = "UPDATE TEST SET test_type = ? WHERE material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialUpdateStatement = connection.prepareStatement(sqlMaterialUpdate);
             PreparedStatement testUpdateStatement = connection.prepareStatement(sqlTestUpdate)) {

            connection.setAutoCommit(false);

            materialUpdateStatement.setTimestamp(1, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialUpdateStatement.setString(2, newObject.getTitle()); //set title
            materialUpdateStatement.setString(3, newObject.getDescription()); //set description
            materialUpdateStatement.setString(4, newObject.getCourseId().toString()); //set course_id
            materialUpdateStatement.setString(5, id.toString()); //set material_id

            materialUpdateStatement.executeUpdate();

            testUpdateStatement.setString(1, newObject.getTestType().toString()); //set test_type
            testUpdateStatement.setString(2, id.toString()); //set material_id

            testUpdateStatement.executeUpdate();

            connection.commit();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Test updated!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Update test failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addNewObject(Test newObject) {
        String sqlMaterialInsert = "INSERT INTO MATERIAL (material_id, creation_time, title, description, course_id, material_type) VALUES(?, ?, ?, ?, ?, ?)";
        String sqlTestInsert = "INSERT INTO TEST (material_id, test_type) VALUES (?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialInsertStatement = connection.prepareStatement(sqlMaterialInsert);
             PreparedStatement testInsertStatement = connection.prepareStatement(sqlTestInsert)) {

            connection.setAutoCommit(false);

            materialInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            materialInsertStatement.setTimestamp(2, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialInsertStatement.setString(3, newObject.getTitle()); //set title
            materialInsertStatement.setString(4, newObject.getDescription()); //set description
            materialInsertStatement.setString(5, newObject.getCourseId().toString()); //set course_id
            materialInsertStatement.setString(6, "Test"); //set material_type

            materialInsertStatement.executeUpdate();

            testInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            testInsertStatement.setString(2, newObject.getTestType().toString()); //set test_type

            testInsertStatement.executeUpdate();

            connection.commit();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "1 Test inserted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Insert test failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Test> getAll() {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN TEST t ON m.material_id = t.material_id WHERE LOWER(m.material_type) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, "test");

            return materialMapper.mapToTestList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void addAllFromGivenList(List<Test> objectList) {
        objectList.forEach(this::addNewObject);
    }

    @Override
    public List<Test> getAllMaterialsByDiscipline(Discipline discipline) {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN COURSE c ON m.course_id = c.course_id WHERE LOWER(m.material_type) LIKE ? AND LOWER(c.discipline) LIKE ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "test"); //set material_type
            preparedStatement.setString(2, discipline.toString().toLowerCase()); //set discipline

            return materialMapper.mapToTestList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public List<Test> getMaterialByTeacher(UUID teacherId) {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN COURSE c ON m.course_id = c.course_id WHERE LOWER(m.material_type) LIKE ? AND c.user_id LIKE ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "test"); //set material_type
            preparedStatement.setString(2, teacherId.toString()); //set user_id

            return materialMapper.mapToTestList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public List<Test> getAllTestsByType(TestType testType) {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN TEST t ON m.material_id = t.material_id WHERE LOWER(t.test_type) LIKE ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, testType.getTypeString());

            return materialMapper.mapToTestList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }
}
