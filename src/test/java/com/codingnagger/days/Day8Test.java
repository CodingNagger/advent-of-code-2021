package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.List;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class Day8Test {
    public static final List<String> INPUT = InputLoader.LoadTest("day8.txt");
    private static final Day8 DAY = new Day8();

    public static Stream<Arguments> getPartTwoInputs() {
        return Stream.of(
                Arguments.of(8394, "be cfbegad cbdgef fgaecd cgeb fdcge agebfd fecdb fabcd edb | fdgacbe cefdb cefbgd gcbe"),
                Arguments.of(9781, "edbfga begcd cbg gc gcadebf fbgde acbgfd abcde gfcbed gfec | fcgedb cgb dgebacf gc"),
                Arguments.of(1197, "fgaebd cg bdaec gdafb agbcfd gdcbef bgcad gfac gcb cdgabef | cg cg fdcagb cbg"),
                Arguments.of(9361, "fbegcd cbd adcefb dageb afcb bc aefdc ecdab fgdeca fcdbega | efabcd cedba gadfec cb"),
                Arguments.of(4873, "aecbfdg fbg gf bafeg dbefa fcge gcbea fcaegb dgceab fcbdga | gecf egdcabf bgf bfgea"),
                Arguments.of(8418, "fgeab ca afcebg bdacfeg cfaedg gcfdb baec bfadeg bafgc acf | gebdcfa ecba ca fadegcb"),
                Arguments.of(4548, "dbcfg fgd bdegcaf fgec aegbdf ecdfab fbedc dacgb gdcebf gf | cefg dcbef fcge gbcadfe"),
                Arguments.of(1625, "bdfegc cbegaf gecbf dfcage bdacg ed bedf ced adcbefg gebcd | ed bcgafe cdgba cbgef"),
                Arguments.of(8717, "egadfb cdbfeg cegd fecab cgb gbdefca cg fgcdab egfdb bfceg | gbdfcae bgc cg cgb"),
                Arguments.of(4315, "gcafb gcf dcaebfg ecagb gf abcdeg gaef cafbge fdbac fegbdc | fgae cfgab fg bagce")
        );
    }

    @Test
    public void partOne_shouldReturnExpectedResult() {
        String result = DAY.partOne(INPUT);

        assertThat(result).isEqualTo("26");
    }

    @Test
    public void partTwo_shouldReturnExpectedResult() {
        String result = DAY.partTwo(INPUT);

        assertThat(result).isEqualTo("61229");
    }

    @Test
    public void displayOutput_shouldMatchExpectedOutcome() {
        Day8.SevenSegmentDisplay display = Day8.parseEntry("acedgfb cdfbe gcdfa fbcad dab cefabd cdfgeb eafb cagedb ab | cdfeb fcadb cdfeb cdbaf");

        display.findDigits();
        assertThat(display.getDigit("acedgfb")).isEqualTo('8');
        assertThat(display.getDigit("dab")).isEqualTo('7');
        assertThat(display.getDigit("eafb")).isEqualTo('4');
        assertThat(display.getDigit("ab")).isEqualTo('1');

        assertThat(display.getDigit("cagedb")).isEqualTo('0');
        assertThat(display.getDigit("gcdfa")).isEqualTo('2');
        assertThat(display.getDigit("fbcad")).isEqualTo('3');
        assertThat(display.getDigit("cdfbe")).isEqualTo('5');
        assertThat(display.getDigit("cdfgeb")).isEqualTo('6');
        assertThat(display.getDigit("cefabd")).isEqualTo('9');

        assertThat(display.determineOutput()).isEqualTo("5353");
        assertThat(display.output()).isEqualTo(5353);
    }

    @ParameterizedTest
    @MethodSource("getPartTwoInputs")
    public void displayOutput_shouldMatchExpectedOutcome_again(int expectedResult, String input) {
        Day8.SevenSegmentDisplay display = Day8.parseEntry(input);

        assertThat(display.output()).isEqualTo(expectedResult);
    }
}









