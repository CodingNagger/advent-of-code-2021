package com.codingnagger.days;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

import static com.codingnagger.utils.InputConverter.convertToIntegers;

public class Day7 implements Day {
    @Override
    public String partOne(List<String> input) {
        return "" + calculateOptimalFuelCost(input, distance -> distance);
    }

    @Override
    public String partTwo(List<String> input) {
        return "" + calculateOptimalFuelCost(input, this::partTwoDistance);
    }

    Integer calculateOptimalFuelCost(List<String> input, Function<Integer, Integer> calculateFuelCostForDistance) {
        List<Integer> positions = convertToIntegers(input.get(0), ",");
        int minCost = Integer.MAX_VALUE;

        int minPosition = positions.stream().min(Comparator.naturalOrder()).get();
        int maxPosition = positions.stream().max(Comparator.naturalOrder()).get();

        for (int position = minPosition; position <= maxPosition; position++) {
            int cost = 0;

            for (Integer current : positions) {
                if (current.equals(position)) {
                    continue;
                }

                cost += calculateFuelCostForDistance.apply(Math.abs(position - current));
            }
            minCost = Math.min(minCost, cost);
        }

        return minCost;
    }

    public Integer partTwoDistance(Integer distance) {
        int distanceCost = 0;
        for (int i = 0; i <= distance; i++) {
            distanceCost += i;
        }
        return distanceCost;
    }
}
