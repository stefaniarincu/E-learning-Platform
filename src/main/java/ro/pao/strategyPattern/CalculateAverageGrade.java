package ro.pao.strategyPattern;

import ro.pao.model.Grade;

import java.util.List;

public class CalculateAverageGrade implements CalculateAverageGradeStrategy{

    @Override
    public double calculateAverageGrade(List<Grade> grades) {
        if (grades.isEmpty()) {
            return 0.0;
        }

        double sum = 0.0;
        for (Grade grade : grades) {
            sum += grade.getGrade();
        }

        return sum / grades.size();
    }
}
