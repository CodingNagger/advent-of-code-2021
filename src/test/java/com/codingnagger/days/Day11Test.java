package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class Day11Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day11.txt");
    private static final Day DAY = new Day11();

    @Test
    public void partOne_shouldReturnIncreaseCount() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("1656");
    }

    @Test
    public void octopusGrid_shouldReturnRightCount_forLightSampleInput() {
        Day11.OctopusGrid octopusGrid = new Day11.OctopusGrid(InputLoader.LoadTest("day11_light.txt"));
        octopusGrid.iterate(2);
        assertThat(octopusGrid.getFlashCount()).isEqualTo(9);
    }

    @ParameterizedTest
    @CsvSource({"0,0","1,0","2,35","3,80"})
    public void octopusGrid_shouldReturnRightCount(int days, int expectedResult) {
        Day11.OctopusGrid octopusGrid = new Day11.OctopusGrid(INPUT);
        octopusGrid.iterate(days);
        assertThat(octopusGrid.getFlashCount()).isEqualTo(expectedResult);
    }


    @Test
    public void partTwo_shouldReturnSlidingIncreaseCount() {
        String result = DAY.partTwo(INPUT);

        assertThat(result).isEqualTo("195");
    }
}
