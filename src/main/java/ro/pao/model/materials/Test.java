package ro.pao.model.materials;

import ro.pao.model.materials.abstracts.Material;
import ro.pao.model.materials.enums.TestType;

public class Test extends Material {
    private TestType testType;
    private Double score;

    public TestType getTestType() {
        return testType;
    }

    public void setTestType(TestType testType) {
        this.testType = testType;
    }

    public Double getScore() {
        return score;
    }

    public void setScore(Double score) {
        this.score = score;
    }
}
