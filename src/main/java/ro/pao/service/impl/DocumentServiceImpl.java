package ro.pao.service.impl;

import ro.pao.exceptions.ObjectNotFoundException;
import ro.pao.model.Document;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.DocumentType;
import ro.pao.repository.DocumentRepository;
import ro.pao.repository.impl.DocumentRepositoryImpl;
import ro.pao.service.DocumentService;

import java.sql.SQLException;
import java.util.*;
import java.util.logging.Level;

public class DocumentServiceImpl implements DocumentService {
    private static final DocumentRepository documentRepository = new DocumentRepositoryImpl();
    @Override
    public Optional<Document> getById(UUID id) {
        try {
            return documentRepository.getObjectById(id);
        } catch (ObjectNotFoundException e) {
            LogServiceImpl.getInstance().log(Level.WARNING, e.getMessage());
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }

        return Optional.empty();
    }

    @Override
    public List<Document> getAllItems() {
        return documentRepository.getAll();
    }

    @Override
    public void addOnlyOne(Document newObject) {
        try {
            documentRepository.addNewObject(newObject);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
    }

    @Override
    public void addMany(List<Document> objectList) {
        try {
            documentRepository.addAllFromGivenList(objectList);
        } catch (SQLException e) {
            LogServiceImpl.getInstance().log(Level.SEVERE, e.getMessage());
        }
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
    public List<Document> getDocumentsByType(DocumentType documentType) {
        try {
            return documentRepository.getAllDocumentsByType(documentType);
        } catch (SQLException e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<Document> getAllMaterialsByDiscipline(Discipline discipline) {
        return documentRepository.getAllMaterialsByDiscipline(discipline);
    }
}

