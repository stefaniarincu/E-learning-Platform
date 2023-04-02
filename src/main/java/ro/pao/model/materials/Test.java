package ro.pao.model.materials;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.SuperBuilder;
import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.TestType;


@SuperBuilder
@Getter
@Setter
public class Test extends Material {
    private TestType testType;
    private Double score;

    @Override
    public String toString(){
        if (this.testType.equals(TestType.EXAM)) {
            return "EXAM (very important) ---> " + super.toString();
        } else {
            return "QUIZ (just for jun) ---> " + super.toString();
        }
    }
}
