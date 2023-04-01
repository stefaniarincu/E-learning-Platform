package ro.pao.service.materials.impl;

import ro.pao.model.materials.Document;
import ro.pao.model.materials.enums.Discipline;
import ro.pao.model.materials.enums.DocumentType;
import ro.pao.service.materials.DocumentService;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class DocumentServiceImpl implements DocumentService {
    private static final Map<UUID, Document> documentMap = new HashMap<>();

    @Override
    public Optional<Document> getById(UUID id) {
        return Optional.ofNullable(documentMap.getOrDefault(id, null));
    }

    @Override
    public Map<UUID, Document> getAllItems() {
        return documentMap;
    }

    @Override
    public Map<UUID, Document> getDocumentsByType(DocumentType documentType) {
        if (documentType == null) {
            return Collections.emptyMap();
        }

        return documentMap.values().stream()
                .filter(document -> documentType.equals(document.getDocumentType()))
                .collect(Collectors.toMap(Document::getId, Function.identity()));
    }

    @Override
    public Map<UUID, Document> getMaterialsByDiscipline(Discipline discipline) {
        if (discipline == null) {
            return Collections.emptyMap();
        }

        return documentMap.values().stream()
                .filter(document -> discipline.equals(document.getDiscipline()))
                .collect(Collectors.toMap(Document::getId, Function.identity()));
    }

    @Override
    public void addOnlyOne(Document document) {
        if(document == null) {
            System.out.println("The document that you wanted to add is invalid!");
        } else if(documentMap.containsKey(document.getId())) {
            System.out.println("The document: --- " + document.getTitle() + " --- that you wanted to add is already in the map!");
        } else {
            documentMap.put(document.getId(), document);
        }
    }

    @Override
    public void addMany(Map<UUID, Document> documents) {
        for(UUID id: documents.keySet()) {
            if(documents.get(id) == null) {
                System.out.println("The document that you wanted to add is invalid!");
            } else if(!documentMap.containsKey(id)) {
                documentMap.put(id, documents.get(id));
            } else {
                System.out.println("The document: --- " + documents.get(id).getTitle() + " --- that you wanted to add is already in the map!");
            }
        }
    }

    @Override
    public void removeById(UUID id) {
        Document document = documentMap.get(id);

        if(document == null) {
            System.out.println("The document that you wanted to remove is invalid!");
        } else {
            documentMap.remove(id);
        }
    }

    @Override
    public void modifyById(UUID id, Document document) {
        if (document == null) {
            System.out.println("The document you wanted to modify is null!");
        } else if (documentMap.containsKey(id)) {
            removeById(id);
            addOnlyOne(document);
        } else {
            System.out.println("The document: --- " + document.getId() + " --- that you wanted to modify does not exist in the map!");
        }
    }
}
