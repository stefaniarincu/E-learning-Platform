package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.mapper.MaterialMapper;
import ro.pao.model.Test;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.TestType;
import ro.pao.repository.TestRepository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class TestRepositoryImpl implements TestRepository {
    private static final MaterialMapper materialMapper = MaterialMapper.getInstance();
    @Override
    public Optional<Test> getObjectById(UUID id) throws SQLException {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN TEST t ON m.material_id = t.material_id WHERE m.material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            return Optional.ofNullable(materialMapper.mapToTest(preparedStatement.executeQuery()));
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM MATERIAL WHERE material_id = ? AND LOWER(material_type) LIKE 'test'";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Test> getAllMaterialsByStudentId(UUID studentId) throws SQLException {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN TEST t ON m.material_id = t.material_id WHERE LOWER(m.material_type) LIKE ? AND m.material_id IN (SELECT * FROM POSSESS WHERE user_id = ?)";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "test");
            preparedStatement.setString(2, studentId.toString());

            return materialMapper.mapToTestList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void updateObjectById(UUID id, Test newObject) {
        String sqlMaterialUpdate = "UPDATE MATERIAL SET creation_time = ?, discipline = ?, title = ?, description = ?, user_id = ? WHERE material_id = ?";
        String sqlTestUpdate = "UPDATE TEST SET test_type = ? WHERE material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialUpdateStatement = connection.prepareStatement(sqlMaterialUpdate);
             PreparedStatement testUpdateStatement = connection.prepareStatement(sqlTestUpdate)) {
            connection.setAutoCommit(false);

            materialUpdateStatement.setTimestamp(1, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialUpdateStatement.setString(2, newObject.getDiscipline().toString()); //set discipline
            materialUpdateStatement.setString(3, newObject.getTitle()); //set title
            materialUpdateStatement.setString(4, newObject.getDescription()); //set description
            materialUpdateStatement.setString(5, newObject.getTeacherId().toString()); //set user_id
            materialUpdateStatement.setString(6, id.toString()); //set material_id

            materialUpdateStatement.executeUpdate();

            testUpdateStatement.setString(1, newObject.getTestType().toString()); //set test_type
            testUpdateStatement.setString(2, id.toString()); //set material_id

            testUpdateStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewObject(Test newObject) {
        String sqlMaterialInsert = "INSERT INTO MATERIAL (material_id, creation_time, discipline, title, description, user_id, material_type) VALUES(?, ?, ?, ?, ?, ?, ?)";
        String sqlTestInsert = "INSERT INTO TEST (material_id, test_type) VALUES (?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialInsertStatement = connection.prepareStatement(sqlMaterialInsert);
             PreparedStatement testInsertStatement = connection.prepareStatement(sqlTestInsert)) {
            connection.setAutoCommit(false);

            materialInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            materialInsertStatement.setTimestamp(2, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialInsertStatement.setString(3, newObject.getDiscipline().toString()); //set discipline
            materialInsertStatement.setString(4, newObject.getTitle()); //set title
            materialInsertStatement.setString(5, newObject.getDescription()); //set description
            materialInsertStatement.setString(6, newObject.getTeacherId().toString()); //set user_id
            materialInsertStatement.setString(7, "Test"); //set material_type

            materialInsertStatement.executeUpdate();

            testInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            testInsertStatement.setString(2, newObject.getTestType().toString()); //set test_type

            testInsertStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Test> getAll() throws SQLException {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN TEST t ON m.material_id = t.material_id WHERE LOWER(m.material_type) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, "test");

            return materialMapper.mapToTestList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void addAllFromGivenList(List<Test> objectList) {
        objectList.forEach(this::addNewObject);
    }

    @Override
    public List<Test> getAllMaterialsByDiscipline(Discipline discipline) {
        return null;
    }

    @Override
    public List<Test> getMaterialByTeacher(UUID teacherId) {
        return null;
    }

    @Override
    public List<Test> getAllTestsByType(TestType testType) throws SQLException {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN TEST t ON m.material_id = t.material_id WHERE LOWER(t.test_type) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, testType.getTypeString());

            return materialMapper.mapToTestList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }
}
