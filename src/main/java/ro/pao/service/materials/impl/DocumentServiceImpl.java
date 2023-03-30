package ro.pao.service.materials.impl;

import ro.pao.model.materials.Document;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.materials.enums.DocumentType;
import ro.pao.service.materials.DocumentService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DocumentServiceImpl implements DocumentService {
    private static Map<UUID, Document> documentMap = new HashMap<>();

    @Override
    public Optional<Document> getById(UUID id) {
        return documentMap.values().stream()
                .filter(document -> id.equals(document.getId()))
                .findAny();
    }

    @Override
    public Map<UUID, Document> getAllItems() {
        return documentMap;
    }

    @Override
    public Map<UUID, Document> getDocumentsByType(DocumentType documentType) {
        return documentMap.values().stream()
                .filter(document -> documentType.equals(document.getDocumentType()))
                .collect(Collectors.toMap(Document::getId, Function.identity()));
    }

    @Override
    public Map<UUID, Document> getMaterialsByDiscipline(Discipline discipline) {
        return documentMap.values().stream()
                .filter(document -> discipline.equals(document.getDiscipline()))
                .collect(Collectors.toMap(Document::getId, Function.identity()));
    }

    @Override
    public void addOnlyOne(Document document) {
        documentMap.put(document.getId(), document);
    }

    @Override
    public void addMany(Map<UUID, Document> documents) {
        documentMap.putAll(documents);
    }

    @Override
    public void removeById(UUID id) {
        documentMap = documentMap.entrySet().stream()
                .filter(document -> !id.equals(document.getKey()))
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    @Override
    public void modifyById(UUID id, Document document) {
        removeById(id);

        addOnlyOne(document);
    }
}
