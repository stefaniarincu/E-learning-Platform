package ro.pao.repository;

import ro.pao.model.Document;
import ro.pao.model.enums.DocumentType;

import java.sql.SQLException;
import java.util.List;

public interface DocumentRepository extends MaterialRepository<Document>{
    List<Document> getAllDocumentsByType(DocumentType documentType) throws SQLException;
}
