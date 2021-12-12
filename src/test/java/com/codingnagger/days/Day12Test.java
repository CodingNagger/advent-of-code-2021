package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day12Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day12.txt");
    private static final Day DAY = new Day12();

    @Test
    public void partOne_shoudlYieldCorrectResult() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("10");
    }

    @Test
    public void partTwo_shoudlYieldCorrectResult() {
        String result = DAY.partTwo(INPUT);

        assertThat(result).isEqualTo("36");
    }
}
