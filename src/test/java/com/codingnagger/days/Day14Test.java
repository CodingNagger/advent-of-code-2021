package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day14Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day14.txt");
    private static final Day DAY = new Day14();

    @Test
    public void partOne_shoudlYieldCorrectResult() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("1588");
    }

    @Test
    public void partTwo_shoudlYieldCorrectResult() {
        String result = DAY.partTwo(INPUT);

        assertThat(result).isEqualTo("2188189693529");
    }
}
