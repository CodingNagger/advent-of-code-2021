package com.codingnagger.days;

import java.util.List;

import static com.codingnagger.utils.InputConverter.convertToIntegers;

public class Day1 implements Day {

    @Override
    public String partOne(List<String> input) {
        return computeIncreases(input, 1);
    }

    @Override
    public String partTwo(List<String> input) {
        return computeIncreases(input, 3);
    }

    private String computeIncreases(List<String> input, int slidingWindowSize) {
        List<Integer> integerInput = convertToIntegers(input);
        int increaseCount = 0;
        int previous = slidingWindowSum(integerInput, 0, slidingWindowSize);

        for (int i = 1; i < integerInput.size() - slidingWindowSize + 1; i++) {
            int value = slidingWindowSum(integerInput, i, slidingWindowSize);

            if (value > previous) {
                increaseCount++;
            }
            previous = value;
        }
        return "" + increaseCount;
    }

    private int slidingWindowSum(List<Integer> integerInput, int index, int slidingWindowSize) {
        int sum = 0;
        for (int i = 0; i < slidingWindowSize; i++) {
            sum += integerInput.get(i + index);
        }
        return sum;
    }
}
