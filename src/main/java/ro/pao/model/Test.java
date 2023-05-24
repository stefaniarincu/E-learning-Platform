package ro.pao.model;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import ro.pao.model.abstracts.Material;
import ro.pao.model.enums.TestType;


@SuperBuilder(toBuilder = true)
@Getter
@Setter
public class Test extends Material {
    private TestType testType;

    public Test() {

    }

    @Override
    public String toString(){
        if (this.testType.equals(TestType.EXAM)) {
            return "EXAM (very important) ---> " + super.toString();
        } else {
            return "QUIZ (just for fun) ---> " + super.toString();
        }
    }
}
