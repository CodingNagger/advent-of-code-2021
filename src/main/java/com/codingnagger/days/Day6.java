package com.codingnagger.days;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static com.codingnagger.utils.InputConverter.convertToIntegers;

public class Day6 implements Day {
    @Override
    public String partOne(List<String> input) {
        LanterfishPool pool = parse(input);
        pool.run(80);
        return pool.fishCount().toString();
    }

    @Override
    public String partTwo(List<String> input) {
        LanterfishPool pool = parse(input);
        pool.run(256);
        return pool.fishCount().toString();
    }

    public LanterfishPool parse(List<String> input) {
        return new LanterfishPool(convertToIntegers(input.get(0), ","));
    }

    public static class LanterfishPool {
        private final BigInteger[] fishes;

        public LanterfishPool(List<Integer> fishStates) {
            fishes = new BigInteger[9];

            for (int i = 0; i < 9; i++) {
                fishes[i] = BigInteger.ZERO;
            }

            for (Integer state : fishStates) {
                fishes[state] = fishes[state].add(BigInteger.ONE);
            }
        }

        public void run(int cycleCount) {
            for (int i = 0; i < cycleCount; i++) {
                tick();
            }
        }

        public void tick() {
            BigInteger newBorns = fishes[0];

            System.arraycopy(fishes, 1, fishes, 0, 8);

            fishes[6] = fishes[6].add(newBorns);
            fishes[8] = newBorns;
        }

        public BigInteger fishCount() {
            BigInteger count = BigInteger.ZERO;

            for (int i = 0; i < 9; i++) {
                count = count.add(fishes[i]);
            }

            return count;
        }
    }
}
