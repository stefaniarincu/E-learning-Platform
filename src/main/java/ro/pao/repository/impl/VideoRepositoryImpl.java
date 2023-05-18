package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.mapper.MaterialMapper;
import ro.pao.model.Video;
import ro.pao.model.enums.Discipline;
import ro.pao.repository.MaterialRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class VideoRepositoryImpl implements MaterialRepository<Video> {
    private static final MaterialMapper materialMapper = MaterialMapper.getInstance();
    @Override
    public Optional<Video> getObjectById(UUID id)  throws SQLException {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN VIDEO v ON m.material_id = v.material_id WHERE m.material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            return Optional.ofNullable(materialMapper.mapToVideo(preparedStatement.executeQuery()));
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public List<Video> getAllMaterialsByStudentId(UUID studentId) throws SQLException {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN VIDEO v ON m.material_id = v.material_id WHERE LOWER(m.material_type) LIKE ? AND m.material_id IN (SELECT * FROM POSSESS WHERE user_id = ?)";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "document");
            preparedStatement.setString(2, studentId.toString());

            return materialMapper.mapToVideoList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM MATERIAL WHERE material_id = ? AND LOWER(material_type) LIKE 'video'";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateObjectById(UUID id, Video newObject) {
        String sqlMaterialUpdate = "UPDATE MATERIAL SET creation_time = ?, discipline = ?, title = ?, description = ?, user_id = ? WHERE material_id = ?";
        String sqlVideoUpdate = "UPDATE VIDEO SET duration = ? WHERE material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialUpdateStatement = connection.prepareStatement(sqlMaterialUpdate);
             PreparedStatement videoUpdateStatement = connection.prepareStatement(sqlVideoUpdate)) {
            connection.setAutoCommit(false);

            materialUpdateStatement.setTimestamp(1, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialUpdateStatement.setString(2, newObject.getDiscipline().toString()); //set discipline
            materialUpdateStatement.setString(3, newObject.getTitle()); //set title
            materialUpdateStatement.setString(4, newObject.getDescription()); //set description
            materialUpdateStatement.setString(5, newObject.getTeacherId().toString()); //set user_id
            materialUpdateStatement.setString(6, id.toString()); //set material_id

            materialUpdateStatement.executeUpdate();

            videoUpdateStatement.setTime(1, Time.valueOf(newObject.getDuration())); //set duration
            videoUpdateStatement.setString(2, id.toString()); //set material_id

            videoUpdateStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewObject(Video newObject) {
        String sqlMaterialInsert = "INSERT INTO MATERIAL (material_id, creation_time, discipline, title, description, user_id, material_type) VALUES(?, ?, ?, ?, ?, ?, ?)";
        String sqlVideoInsert = "INSERT INTO VIDEO (material_id, duration) VALUES (?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialInsertStatement = connection.prepareStatement(sqlMaterialInsert);
             PreparedStatement videoInsertStatement = connection.prepareStatement(sqlVideoInsert)) {
            connection.setAutoCommit(false);

            materialInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            materialInsertStatement.setTimestamp(2, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialInsertStatement.setString(3, newObject.getDiscipline().toString()); //set discipline
            materialInsertStatement.setString(4, newObject.getTitle()); //set title
            materialInsertStatement.setString(5, newObject.getDescription()); //set description
            materialInsertStatement.setString(6, newObject.getTeacherId().toString()); //set user_id
            materialInsertStatement.setString(7, "Video"); //set material_type

            materialInsertStatement.executeUpdate();

            videoInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            videoInsertStatement.setTime(2, Time.valueOf(newObject.getDuration())); //set duration

            videoInsertStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Video> getAll() throws SQLException {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN VIDEO v ON m.material_id = v.material_id WHERE LOWER(m.material_type) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, "video");

            return materialMapper.mapToVideoList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void addAllFromGivenList(List<Video> objectList) {
        objectList.forEach(this::addNewObject);
    }

    @Override
    public List<Video> getAllMaterialsByDiscipline(Discipline discipline) {
        return null;
    }

    @Override
    public List<Video> getMaterialByTeacher(UUID teacherId) {
        return null;
    }
}
