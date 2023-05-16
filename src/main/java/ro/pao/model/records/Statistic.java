package ro.pao.model.records;

import ro.pao.model.Test;

public record Statistic(Test test, String difficulty, Double averageSolvability) {
    public Statistic {
        if (test == null || difficulty == null || averageSolvability == null || averageSolvability < 0.0) {
            System.out.println("Could not create object Statistic!");
        }
    }

    public String toString(){
        return test.getTitle() + " ---- difficulty: " + difficulty.toLowerCase() + " ---- solvability: " + averageSolvability + "%.";
    }
}
