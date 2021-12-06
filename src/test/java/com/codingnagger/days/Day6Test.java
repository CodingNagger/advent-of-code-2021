package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day6Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day6.txt");
    private static final Day DAY = new Day6();

    @Test
    public void partOne_shouldReturnExpectedResult() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("5934");
    }

    @ParameterizedTest
    @CsvSource({"5,10", "6,10","7,10","8,10","10,12", "18,26"})
    public void partOne_lanternFishCount(int cyclesCount, BigInteger expectedResult) {
        Day6.LanterfishPool pool = new Day6.LanterfishPool(Arrays.asList(3,4,3,1,2));

        pool.run(cyclesCount);

        assertThat(pool.fishCount()).isEqualTo(expectedResult);
    }

    @Test
    public void partTwo_shouldReturnExpectedResult() {
        String result = DAY.partTwo(INPUT);

        assertThat(result).isEqualTo("26984457539");
    }
}
