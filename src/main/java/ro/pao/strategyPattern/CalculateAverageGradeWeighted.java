package ro.pao.strategyPattern;

import ro.pao.model.Grade;
import ro.pao.model.Test;
import ro.pao.service.TestService;
import ro.pao.service.impl.TestServiceImpl;

import java.sql.SQLException;
import java.util.List;

public class CalculateAverageGradeWeighted implements CalculateAverageGradeStrategy{
    @Override
    public double calculateAverageGrade(List<Grade> grades) throws SQLException {
        if (grades.isEmpty()) {
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
