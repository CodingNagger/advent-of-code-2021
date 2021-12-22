
package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day21Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day21.txt");
    private static final Day DAY = new Day21();

    @Test
    public void partOne_shoudlYieldCorrectResult() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("739785");
    }

    @Test
    public void partTwo_shoudlYieldCorrectResult() {
        String result = DAY.partTwo(INPUT);

        assertThat(result).isEqualTo("444356092776315"); // test answer too low, real input answer too high 343042929017714
    }
}
