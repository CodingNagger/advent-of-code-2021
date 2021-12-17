
package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day17Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day17.txt");
    private static final Day DAY = new Day17();

    @Test
    public void partOne_shoudlYieldCorrectResult() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("45");
    }

    @Test
    public void partTwo_shoudlYieldCorrectResult() {
        String result = DAY.partTwo(INPUT);

        assertThat(result).isEqualTo("112");
    }
}
