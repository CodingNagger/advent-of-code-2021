package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day5Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day5.txt");
    private static final Day DAY = new Day5();

    @Test
    public void partOne_shouldReturnExpectedResult() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("5");
    }

    @Test
    public void partTwo_shouldReturnExpectedResult() {
        String result = DAY.partTwo(INPUT);

        assertThat(result).isEqualTo("12");
    }
}
