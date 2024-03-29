package ro.pao.repository.impl;

import ro.pao.config.DatabaseConfiguration;
import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Document;
import ro.pao.model.Test;
import ro.pao.model.Video;
import ro.pao.model.abstracts.Material;
import ro.pao.model.enums.Discipline;
import ro.pao.repository.MaterialRepository;
import ro.pao.service.impl.LogServiceImpl;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Level;

public class MaterialRepositoryImpl implements MaterialRepository<Material> {
    private final MaterialRepository<Document> documentRepository = new DocumentRepositoryImpl();
    private final MaterialRepository<Test> testRepository = new TestRepositoryImpl();
    private final MaterialRepository<Video> videoRepository = new VideoRepositoryImpl();

    @Override
    public Optional<Material> getObjectById(UUID id) throws SQLException, ObjectNotFoundException {
        Optional<? extends Material> material = documentRepository.getObjectById(id);
        if(material.isPresent())
            return material.map(m -> (Material) m);
        material = testRepository.getObjectById(id);
        if(material.isPresent())
            return material.map(m -> (Material) m);
        return videoRepository.getObjectById(id).map(m -> m);
    }

    @Override
    public void deleteObjectById(UUID id) {
        String sqlStatement = "DELETE FROM MATERIAL WHERE material_id = ?";

        try (Connection connection = DatabaseConfiguration.getDatabaseConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sqlStatement)) {
            preparedStatement.setString(1, id.toString());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void updateObjectById(UUID id, Material newObject) {
        if(newObject instanceof Document document) {
            documentRepository.updateObjectById(id, document);
        }
        else if(newObject instanceof Test test) {
            testRepository.updateObjectById(id, test);
        }
        else {
            videoRepository.updateObjectById(id, (Video) newObject);
        }
    }

    @Override
    public void addNewObject(Material newObject) throws SQLException {
        if(newObject instanceof Document document) {
            documentRepository.addNewObject(document);
        }
        else if(newObject instanceof Test test) {
            testRepository.addNewObject(test);
        }
        else {
            videoRepository.addNewObject((Video) newObject);
        }
    }

    @Override
    public List<Material> getAll() {
        List<Material> materialList = new ArrayList<>();

        materialList.addAll(documentRepository.getAll());
        materialList.addAll(testRepository.getAll());
        materialList.addAll(videoRepository.getAll());

        return materialList;
    }

    @Override
    public void addAllFromGivenList(List<Material> objectList) throws SQLException {
        for(Material material : objectList) {
            if(material instanceof Document document) {
                documentRepository.addNewObject(document);
            }
            else if(material instanceof Test test) {
                testRepository.addNewObject(test);
            }
            else {
                videoRepository.addNewObject((Video) material);
            }
        }
    }

    @Override
    public List<Material> getAllMaterialsByDiscipline(Discipline discipline) {
        List<Material> materialList = new ArrayList<>();

        materialList.addAll(documentRepository.getAllMaterialsByDiscipline(discipline));
        materialList.addAll(testRepository.getAllMaterialsByDiscipline(discipline));
        materialList.addAll(videoRepository.getAllMaterialsByDiscipline(discipline));

        return materialList;
    }

    @Override
    public List<Material> getAllMaterialsByStudentId(UUID studentId) {
        List<Material> materialList = new ArrayList<>();

        materialList.addAll(documentRepository.getAllMaterialsByStudentId(studentId));
        materialList.addAll(testRepository.getAllMaterialsByStudentId(studentId));
        materialList.addAll(videoRepository.getAllMaterialsByStudentId(studentId));

        return materialList;
    }

    @Override
    public List<Material> getAllMaterialsByCourseId(UUID courseId) {
        List<Material> materialList = new ArrayList<>();

        materialList.addAll(documentRepository.getAllMaterialsByCourseId(courseId));
        materialList.addAll(testRepository.getAllMaterialsByCourseId(courseId));
        materialList.addAll(videoRepository.getAllMaterialsByCourseId(courseId));

        return materialList;
    }
}
