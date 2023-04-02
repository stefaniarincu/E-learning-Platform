package ro.pao.service;

import org.junit.jupiter.api.Test;
import ro.pao.application.Menu;
import ro.pao.model.materials.Document;
import ro.pao.service.materials.DocumentService;
import ro.pao.service.materials.impl.DocumentServiceImpl;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DocumentServiceTest {

    private final Menu menu = Menu.getInstance();
    private final DocumentService documentService = new DocumentServiceImpl();

    @Test
    void whenGivenDocumentClass_thenElementIsAdd() {

        Document document = Document.builder()
                .id(UUID.randomUUID())
                .build();

        documentService.addOnlyOne(document);

        assertEquals(1, documentService.getAllItems().size());
    }
}