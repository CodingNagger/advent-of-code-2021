package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day2Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day2.txt");
    private static final Day DAY_2 = new Day2();

    @Test
    public void partOne_shouldReturnIncreaseCount() {
        String result = DAY_2.partOne(INPUT);

        assertThat(result).isEqualTo("150");
    }

    @Test
    public void partTwo_shouldReturnSlidingIncreaseCount() {
        String result = DAY_2.partTwo(INPUT);

        assertThat(result).isEqualTo("900");
    }
}
