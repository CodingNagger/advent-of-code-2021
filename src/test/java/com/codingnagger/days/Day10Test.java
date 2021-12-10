package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day10Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day10.txt");
    private static final Day DAY = new Day10();

    @Test
    public void partOne_shouldReturnIncreaseCount() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("26397");
    }

    @Test
    public void partTwo_shouldReturnSlidingIncreaseCount() {
        String result = DAY.partTwo(INPUT);

        assertThat(result).isEqualTo("288957");
    }
}
