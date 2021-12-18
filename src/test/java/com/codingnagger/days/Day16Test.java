package com.codingnagger.days;

import com.codingnagger.utils.InputLoader;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.util.Collections;
import java.util.List;

import static com.codingnagger.bits.Bits.convertHexaToBinaryString;
import static org.assertj.core.api.Assertions.assertThat;

public class Day16Test {
    private static final List<String> INPUT = InputLoader.LoadTest("day16.txt");
    private static final Day DAY = new Day16();

    @ParameterizedTest
    @CsvSource({"D2FE28,6", "38006F45291200,9", "EE00D40C823060,14", "8A004A801A8002F478,16", "620080001611562C8802118E34,12", "C0015000016115A2E0802F182340,23", "A0016C880162017C3686B18A3D4780,31"})
    public void partOne_shoudlYieldCorrectResult(String input, String expectedResult) {
        String result = DAY.partOne(Collections.singletonList(input));

        assertThat(result).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({"D2FE28,110100101111111000101000", "38006F45291200,00111000000000000110111101000101001010010001001000000000"})
    public void convertHexaToBinaryString_shouldReturnExpectedBits(String input, String expectedResult) {
        assertThat(convertHexaToBinaryString(input)).isEqualTo(expectedResult);
    }

    @ParameterizedTest
    @CsvSource({"C200B40A82,3", "04005AC33890,54", "880086C3E88112,7", "CE00C43D881120,9", "D8005AC2A8F0,1", "F600BC2D8F,0", "9C005AC2F8F0,0", "9C0141080250320F1802104A08,1",
    "D2FE28,2021","38006F45291200,1", "EE00D40C823060,3"})
    public void partTwo_shoudlYieldCorrectResult(String input, String expectedResult) {
        String result = DAY.partTwo(Collections.singletonList(input));

        assertThat(result).isEqualTo(expectedResult);
    }
}
