package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day3Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day3.txt");
    private static final Day3 DAY = new Day3();

    @Test
    public void partOne_shouldReturnExpectedResult() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("198");
    }

    @Test
    public void partTwo_shouldReturnExpectedResult() {
        String result = DAY.partTwo(INPUT);

        assertThat(result).isEqualTo("230");
    }

    @Test
    public void toDigit_shouldWork() {
        int result = DAY.toDecimal("10110");

        assertThat(result).isEqualTo(22);
    }
}
