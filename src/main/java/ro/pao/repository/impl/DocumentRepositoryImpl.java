package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.mapper.MaterialMapper;
import ro.pao.model.Document;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.DocumentType;
import ro.pao.repository.DocumentRepository;

import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class DocumentRepositoryImpl implements DocumentRepository {
    private static final MaterialMapper materialMapper = MaterialMapper.getInstance();
    @Override
    public Optional<Document> getObjectById(UUID id) throws SQLException {
        String sqlStatement = "SELECT * FROM MATERIAL m, DOCUMENT d ON m.material_id = d.material_id(+) WHERE m.material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            return Optional.ofNullable(materialMapper.mapToDocument(preparedStatement.executeQuery()));
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM MATERIAL WHERE m.material_id = ? AND LOWER(material_type) LIKE 'document'";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void updateObjectById(UUID id, Document newObject) {
        String sqlMaterialUpdate = "UPDATE MATERIAL SET creation_time = ?, discipline = ?, title = ?, description = ? WHERE material_id = ?";
        String sqlDocumentUpdate = "UPDATE DOCUMENT SET document_type = ? WHERE material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialUpdateStatement = connection.prepareStatement(sqlMaterialUpdate);
             PreparedStatement documentUpdateStatement = connection.prepareStatement(sqlDocumentUpdate)) {
            connection.setAutoCommit(false);

            materialUpdateStatement.setTimestamp(1, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialUpdateStatement.setString(2, newObject.getDiscipline().toString()); //set discipline
            materialUpdateStatement.setString(3, newObject.getTitle()); //set title
            materialUpdateStatement.setString(4, newObject.getDescription()); //set description
            materialUpdateStatement.setString(5, id.toString()); //set material_id

            materialUpdateStatement.executeUpdate();

            documentUpdateStatement.setString(1, newObject.getDocumentType().toString()); //set document_type
            documentUpdateStatement.setString(2, id.toString()); //set material_id

            documentUpdateStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void addNewObject(Document newObject) {
        String sqlMaterialInsert = "INSERT INTO MATERIAL (material_id, creation_time, discipline, title, description) VALUES(?, ?, ?, ?, ?)";
        String sqlDocumentInsert = "INSERT INTO DOCUMENT (material_id, document_type) VALUES (?, ?)";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement materialInsertStatement = connection.prepareStatement(sqlMaterialInsert);
             PreparedStatement documentInsertStatement = connection.prepareStatement(sqlDocumentInsert)) {
            connection.setAutoCommit(false);

            materialInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            materialInsertStatement.setTimestamp(2, Timestamp.valueOf(newObject.getCreationTime())); //set creation_time
            materialInsertStatement.setString(3, newObject.getDiscipline().toString()); //set discipline
            materialInsertStatement.setString(4, newObject.getTitle()); //set title
            materialInsertStatement.setString(5, newObject.getDescription()); //set description

            materialInsertStatement.executeUpdate();

            documentInsertStatement.setString(1, newObject.getId().toString()); //set material_id
            documentInsertStatement.setString(2, newObject.getDocumentType().toString()); //set document_type

            documentInsertStatement.executeUpdate();

            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Document> getAll() throws SQLException {
        String sqlStatement = "SELECT * FROM MATERIAL m, DOCUMENT d ON m.material_id = d.material_id WHERE LOWER(m.material_type) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, "document");

            return materialMapper.mapToDocumentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }

    @Override
    public void addAllFromGivenList(List<Document> objectList) {
        objectList.forEach(this::addNewObject);
    }

    @Override
    public List<Document> getAllMaterialsByDiscipline(Discipline discipline) {
        return null;
    }

    @Override
    public List<Document> getMaterialByTeacher(UUID teacherId) {
        return null;
    }

    @Override
    public List<Document> getAllDocumentsByType(DocumentType documentType) throws SQLException {
        String sqlStatement = "SELECT * FROM MATERIAL m, DOCUMENT d ON m.material_id = d.material_id WHERE LOWER(d.document_type) LIKE ?";
        try(Connection connection = DatabaseConfiguration.getDatabaseConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, documentType.getTypeString());

            return materialMapper.mapToDocumentList(preparedStatement.executeQuery());
        } catch (SQLException e) {
            throw e;
        }
    }
}
