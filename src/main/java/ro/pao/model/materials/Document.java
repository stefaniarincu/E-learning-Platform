package ro.pao.model.materials;

import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.DocumentType;

public class Document extends Material {
    private DocumentType documentType;

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }
}
