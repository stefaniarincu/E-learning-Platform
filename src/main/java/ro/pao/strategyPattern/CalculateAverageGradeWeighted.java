package ro.pao.strategyPattern;

import ro.pao.model.Grade;

import java.util.List;

public class CalculateAverageGradeWeighted implements CalculateAverageGradeStrategy{
    @Override
    public double calculateAverageGrade(List<Grade> grades) {
        if (grades == null || grades.isEmpty()) {
            return 0.0;
        }

        double weightedSum = 0.0;
        double totalWeight = 0.0;

        for (Grade grade: grades) {
            weightedSum += grade.getWeight() * grade.getGrade();
            totalWeight += grade.getWeight();
        }

        return weightedSum / totalWeight;
    }
}
