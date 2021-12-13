package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day13Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day13.txt");
    private static final Day DAY = new Day13();

    @Test
    public void partOne_shoudlYieldCorrectResult() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("17");
    }

    @Test
    public void partTwo_shouldYieldNothing_sinceWeCareAboutThePrinting() {
        DAY.partTwo(INPUT);
    }
}
