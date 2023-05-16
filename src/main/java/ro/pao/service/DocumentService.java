package ro.pao.service;

import ro.pao.model.Document;
import ro.pao.model.enums.Discipline;
import ro.pao.model.enums.DocumentType;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public interface DocumentService {
    Optional<Document> getById(UUID id);

    Map<UUID, Document> getAllItems();

    Map<UUID, Document> getDocumentsByType(DocumentType documentType);

    Map<UUID, Document> getMaterialsByDiscipline(Discipline discipline);

    void addOnlyOne(Document document);

    void addMany(Map<UUID, Document> documents);

    void removeById(UUID id);

    void modifyById(UUID id, Document document);
}
