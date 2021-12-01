package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day1Test {
    @Test
    public void partOne_shouldReturnIncreaseCount() throws IOException {
        List<String> input = InputLoader.LoadTest("day1.txt");

        String result = new Day1().partOne(input);

        assertThat(result).isEqualTo("7");
    }

    @Test
    public void partTwo_shouldReturnSlidingIncreaseCount() throws IOException {
        List<String> input = InputLoader.LoadTest("day1.txt");

        String result = new Day1().partTwo(input);

        assertThat(result).isEqualTo("5");
    }
}
