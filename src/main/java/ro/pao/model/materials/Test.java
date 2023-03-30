package ro.pao.model.materials;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.TestType;

@SuperBuilder
@Getter
@Setter
public class Test extends Material {
    private TestType testType;
    private Double score;
}
