package ro.pao.service;

import ro.pao.model.Document;
import ro.pao.model.enums.DocumentType;

import java.sql.SQLException;
import java.util.List;

public interface DocumentService extends MaterialService<Document> {
    List<Document> getDocumentsByType(DocumentType documentType) throws SQLException;
}
