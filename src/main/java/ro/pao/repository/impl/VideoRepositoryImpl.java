package ro.pao.repository.impl;

import ro.pao.application.csv.CsvLogger;
import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.MaterialNotFoundException;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.mapper.MaterialMapper;
import ro.pao.model.Video;
import ro.pao.model.enums.Discipline;
import ro.pao.repository.MaterialRepository;
import ro.pao.service.impl.LogServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class VideoRepositoryImpl implements MaterialRepository<Video> {
    private static final MaterialMapper materialMapper = MaterialMapper.getInstance();
    @Override
    public Optional<Video> getObjectById(UUID id)  throws SQLException, ObjectNotFoundException {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN VIDEO v ON m.material_id = v.material_id WHERE m.material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, id.toString());
            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<Video> video = Optional.ofNullable(materialMapper.mapToVideo(resultSet));

            if (video.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Select video by id failed!"));
                throw new MaterialNotFoundException("No video found with the id: " + id);
            } else {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Selected from video!"));
            }

            return video;
        }
    }

    @Override
    public List<Video> getAllMaterialsByStudentId(UUID studentId) {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN COURSE c ON m.course_id = c.course_id WHERE LOWER(m.material_type) LIKE ? AND c.course_id IN (SELECT * FROM ENROLLED WHERE user_id = ?)";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "video");
            preparedStatement.setString(2, studentId.toString());

            return materialMapper.mapToVideoList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM MATERIAL WHERE material_id = ? AND LOWER(material_type) LIKE 'video'";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Video deleted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Delete video failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void updateObjectById(UUID id, Video newObject) {
        String sqlMaterialUpdate = "UPDATE MATERIAL SET creation_time = ?, title = ?, description = ?, course_id = ? WHERE material_id = ?";
        String sqlVideoUpdate = "UPDATE VIDEO SET duration = ? WHERE material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialUpdateStatement = connection.prepareStatement(sqlMaterialUpdate);
             PreparedStatement videoUpdateStatement = connection.prepareStatement(sqlVideoUpdate)) {

            connection.setAutoCommit(false);

            materialUpdateStatement.setTimestamp(1, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialUpdateStatement.setString(2, newObject.getTitle()); //set title
            materialUpdateStatement.setString(3, newObject.getDescription()); //set description
            materialUpdateStatement.setString(4, newObject.getCourseId().toString()); //set course_id
            materialUpdateStatement.setString(5, id.toString()); //set material_id

            materialUpdateStatement.executeUpdate();

            videoUpdateStatement.setTime(1, Time.valueOf(newObject.getDuration())); //set duration
            videoUpdateStatement.setString(2, id.toString()); //set material_id

            videoUpdateStatement.executeUpdate();

            connection.commit();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Video updated!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Update video failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addNewObject(Video newObject) {
        String sqlMaterialInsert = "INSERT INTO MATERIAL (material_id, creation_time, title, description, course_id, material_type) VALUES(?, ?, ?, ?, ?, ?)";
        String sqlVideoInsert = "INSERT INTO VIDEO (material_id, duration) VALUES (?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialInsertStatement = connection.prepareStatement(sqlMaterialInsert);
             PreparedStatement videoInsertStatement = connection.prepareStatement(sqlVideoInsert)) {
            connection.setAutoCommit(false);

            materialInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            materialInsertStatement.setTimestamp(2, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialInsertStatement.setString(3, newObject.getTitle()); //set title
            materialInsertStatement.setString(4, newObject.getDescription()); //set description
            materialInsertStatement.setString(5, newObject.getCourseId().toString()); //set course_id
            materialInsertStatement.setString(6, "Video"); //set material_type

            materialInsertStatement.executeUpdate();

            videoInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            videoInsertStatement.setTime(2, Time.valueOf(newObject.getDuration())); //set duration

            videoInsertStatement.executeUpdate();

            connection.commit();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "1 Video inserted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Insert video failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Video> getAll() {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN VIDEO v ON m.material_id = v.material_id WHERE LOWER(m.material_type) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, "video");

            return materialMapper.mapToVideoList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void addAllFromGivenList(List<Video> objectList) {
        objectList.forEach(this::addNewObject);
    }

    @Override
    public List<Video> getAllMaterialsByDiscipline(Discipline discipline) {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN COURSE c ON m.course_id = c.course_id WHERE LOWER(m.material_type) LIKE ? AND LOWER(c.discipline) LIKE ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "video"); //set material_type
            preparedStatement.setString(2, discipline.toString().toLowerCase()); //set discipline

            return materialMapper.mapToVideoList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public List<Video> getMaterialByTeacher(UUID teacherId) {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN COURSE c ON m.course_id = c.course_id WHERE LOWER(m.material_type) LIKE ? AND c.user_id LIKE ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "video"); //set material_type
            preparedStatement.setString(2, teacherId.toString()); //set user_id

            return materialMapper.mapToVideoList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }
}
