package ro.pao.strategyPattern;

import ro.pao.model.Grade;

import java.sql.SQLException;
import java.util.List;

public interface CalculateAverageGradeStrategy {
    public double calculateAverageGrade(List<Grade> grades) throws SQLException;
}
