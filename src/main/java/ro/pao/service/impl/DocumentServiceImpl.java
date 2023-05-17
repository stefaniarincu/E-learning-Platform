package ro.pao.service.impl;

import ro.pao.model.Document;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.DocumentType;
import ro.pao.repository.DocumentRepository;
import ro.pao.repository.impl.DocumentRepositoryImpl;
import ro.pao.service.DocumentService;

import java.sql.SQLException;
import java.util.*;

public class DocumentServiceImpl implements DocumentService {
    private static final DocumentRepository documentRepository = new DocumentRepositoryImpl();
    @Override
    public Optional<Document> getById(UUID id) throws SQLException {
        return documentRepository.getObjectById(id);
    }

    @Override
    public List<Document> getAllItems() throws SQLException {
        return documentRepository.getAll();
    }

    @Override
    public void addOnlyOne(Document newObject) {
        documentRepository.addNewObject(newObject);
    }

    @Override
    public void addMany(List<Document> objectList) {
        documentRepository.addAllFromGivenList(objectList);
    }

    @Override
    public void removeById(UUID id) {
        documentRepository.deleteObjectById(id);
    }

    @Override
    public void updateById(UUID id, Document newObject) {
        documentRepository.updateObjectById(id, newObject);
    }

    @Override
    public List<Document> getDocumentsByType(DocumentType documentType) throws SQLException {
        return documentRepository.getAllDocumentsByType(documentType);
    }

    @Override
    public List<Document> getAllMaterialsByDiscipline(Discipline discipline) {
        return documentRepository.getAllMaterialsByDiscipline(discipline);
    }
}

