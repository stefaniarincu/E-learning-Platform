package ro.pao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.abstracts.Material;
import ro.pao.model.enums.DocumentType;

@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class Document extends Material {
    private DocumentType documentType;

    public Document() {

    }

    @Override
    public String toString(){
        if (this.documentType.equals(DocumentType.COURSE)) {
            return "COURSE (contains theory) ---> " + super.toString();
        } else if (this.documentType.equals(DocumentType.PRACTICE)) {
            return "PRACTICE (contains exercises) ---> " + super.toString();
        } else {
            return "IMPLEMENTATION (contains solutions) ---> " + super.toString();
        }
    }
}
