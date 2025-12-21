package com.pokerfans.excelsums.dto;

import java.util.List;

public class CombinationResult {

    private final List<Double> numbers;
    private final double sum;

    public CombinationResult(List<Double> numbers) {
        this.numbers = numbers;
        this.sum = numbers.stream().mapToDouble(Double::doubleValue).sum();
    }

    public List<Double> getNumbers() {
        return numbers;
    }

    public double getSum() {
        return sum;
    }
}
