package ro.pao.model.materials;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.DocumentType;

@SuperBuilder
@Getter
@Setter
public class Document extends Material {
    private DocumentType documentType;
}
