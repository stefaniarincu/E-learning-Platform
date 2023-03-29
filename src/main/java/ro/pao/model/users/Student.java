package ro.pao.model.users;

import ro.pao.model.materials.abstracts.Material;

import java.util.List;

public class Student extends Material {
    private Double averageGrade;
    private List<Double> grades;

    public Double getAverageGrade() {
        return averageGrade;
    }

    public void setAverageGrade(Double averageGrade) {
        this.averageGrade = averageGrade;
    }

    public List<Double> getGrades() {
        return grades;
    }

    public void setGrades(List<Double> grades) {
        this.grades = grades;
    }
}
