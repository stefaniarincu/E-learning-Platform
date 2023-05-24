package ro.pao.repository.impl;

import ro.pao.application.csv.CsvLogger;
import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.MaterialNotFoundException;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.mapper.MaterialMapper;
import ro.pao.model.Document;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.DocumentType;
import ro.pao.repository.DocumentRepository;
import ro.pao.service.impl.LogServiceImpl;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class DocumentRepositoryImpl implements DocumentRepository {
    private static final MaterialMapper materialMapper = MaterialMapper.getInstance();
    @Override
    public Optional<Document> getObjectById(UUID id) throws SQLException, ObjectNotFoundException {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN DOCUMENT d on m.material_id = d.material_id WHERE m.material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, id.toString());

            ResultSet resultSet = preparedStatement.executeQuery();

            Optional<Document> document = Optional.ofNullable(materialMapper.mapToDocument(resultSet));

            if (document.isEmpty()) {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Select document by id failed!"));
                throw new MaterialNotFoundException("No document found with the id: " + id);
            } else {
                CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Selected from Document!"));
            }

            return document;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM MATERIAL WHERE material_id = ? AND LOWER(material_type) LIKE 'document'";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Document deleted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Delete document failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void updateObjectById(UUID id, Document newObject) {
        String sqlMaterialUpdate = "UPDATE MATERIAL SET creation_time = ?, title = ?, description = ? WHERE material_id = ?";
        String sqlDocumentUpdate = "UPDATE DOCUMENT SET document_type = ? WHERE material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialUpdateStatement = connection.prepareStatement(sqlMaterialUpdate);
             PreparedStatement documentUpdateStatement = connection.prepareStatement(sqlDocumentUpdate)) {

            connection.setAutoCommit(false);

            materialUpdateStatement.setTimestamp(1, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialUpdateStatement.setString(2, newObject.getTitle()); //set title
            materialUpdateStatement.setString(3, newObject.getDescription()); //set description
            materialUpdateStatement.setString(4, id.toString()); //set material_id

            materialUpdateStatement.executeUpdate();

            documentUpdateStatement.setString(1, newObject.getDocumentType().toString()); //set document_type
            documentUpdateStatement.setString(2, id.toString()); //set material_id

            documentUpdateStatement.executeUpdate();

            connection.commit();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "Document deleted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Update document failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addNewObject(Document newObject) {
        String sqlMaterialInsert = "INSERT INTO MATERIAL (material_id, creation_time, title, description, course_id, material_type) VALUES(?, ?, ?, ?, ?, ?)";
        String sqlDocumentInsert = "INSERT INTO DOCUMENT (material_id, document_type) VALUES (?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialInsertStatement = connection.prepareStatement(sqlMaterialInsert);
             PreparedStatement documentInsertStatement = connection.prepareStatement(sqlDocumentInsert)) {
            connection.setAutoCommit(false);

            materialInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            materialInsertStatement.setTimestamp(2, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialInsertStatement.setString(3, newObject.getTitle()); //set title
            materialInsertStatement.setString(4, newObject.getDescription()); //set description
            materialInsertStatement.setString(5, newObject.getCourseId().toString()); //set course_id
            materialInsertStatement.setString(6, "Document"); //set material_type

            materialInsertStatement.executeUpdate();

            documentInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            documentInsertStatement.setString(2, newObject.getDocumentType().toString()); //set document_type

            documentInsertStatement.executeUpdate();

            connection.commit();

            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.INFO, "1 Document inserted!"));
        } catch (SQLException e) {
            CsvLogger.getInstance().logAction(LogServiceImpl.getInstance().logIntoCsv(Level.SEVERE, "Error: Insert document failed!"));
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public List<Document> getAll() {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN DOCUMENT d ON m.material_id = d.material_id WHERE LOWER(m.material_type) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, "document");

            return materialMapper.mapToDocumentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public void addAllFromGivenList(List<Document> objectList) {
        objectList.forEach(this::addNewObject);
    }

    @Override
    public List<Document> getAllMaterialsByStudentId(UUID studentId) {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN DOCUMENT d ON m.material_id = d.material_id WHERE LOWER(m.material_type) LIKE ? AND m.material_id IN (SELECT course_id FROM ENROLLED WHERE user_id = ?)";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "document");
            preparedStatement.setString(2, studentId.toString());

            return materialMapper.mapToDocumentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public List<Document> getAllMaterialsByDiscipline(Discipline discipline) {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN DOCUMENT d ON m.material_id = d.material_id WHERE LOWER(m.material_type) LIKE ? AND m.course_id IN (SELECT course_id FROM COURSE WHERE LOWER(c.discipline) LIKE ?)";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, "document"); //set material_type
            preparedStatement.setString(2, discipline.toString().toLowerCase()); //set discipline

            return materialMapper.mapToDocumentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public List<Document> getAllDocumentsByType(DocumentType documentType) {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN DOCUMENT d on m.material_id = d.material_id WHERE LOWER(d.document_type) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, documentType.getTypeString());

            return materialMapper.mapToDocumentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }

    @Override
    public List<Document> getAllMaterialsByCourseId(UUID courseId) {
        String sqlStatement = "SELECT * FROM MATERIAL m LEFT JOIN DOCUMENT d ON m.material_id = d.material_id WHERE m.course_id = ? AND LOWER(m.material_type) LIKE ?";

        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {

            preparedStatement.setString(1, courseId.toString());
            preparedStatement.setString(2, "document");

            return materialMapper.mapToDocumentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return new ArrayList<>();
    }
}
