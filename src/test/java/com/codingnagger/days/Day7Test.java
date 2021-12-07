package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day7Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day7.txt");
    private static final Day7 DAY = new Day7();

    @Test
    public void partOne_shouldReturnExpectedResult() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("37");
    }

    @Test
    public void partTwo_shouldReturnExpectedResult() {
        String result = DAY.partTwo(INPUT);

        assertThat(result).isEqualTo("168");
    }

    @ParameterizedTest
    @CsvSource({"11, 66", "4, 10", "3,6", "1,1", "2,3", "9,45", "5,15"})
    public void parTwoDistance_shouldHaveRightResult(Integer distance, Integer expectedResult) {
        assertThat(DAY.partTwoDistance(distance)).isEqualTo(expectedResult);
    }
}
