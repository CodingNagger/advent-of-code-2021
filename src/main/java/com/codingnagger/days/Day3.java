package com.codingnagger.days;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Day3 implements Day {

    @Override
    public String partOne(List<String> input) {
        BitInfo bitInfo = new BitInfo();
        bitInfo.compute(input);
        return "" + bitInfo.gamma * bitInfo.epsilon;
    }

    @Override
    public String partTwo(List<String> input) {
        List<String> filteredGammaInput = new ArrayList<>(input);
        List<String> filteredEpsilonInput = new ArrayList<>(input);
        BitInfo bitInfo = new BitInfo();

        int cursor = 0;

        while (cursor <= input.get(0).length()) {
            final int lambdaCursor = cursor;

            if (filteredGammaInput.size() > 1) {
                bitInfo.compute(filteredGammaInput);
                filteredGammaInput = filteredGammaInput.stream().filter(line -> line.charAt(lambdaCursor) == bitInfo.gammaBits.charAt(lambdaCursor)).collect(Collectors.toList());
            }

            if (filteredEpsilonInput.size() > 1) {
                bitInfo.compute(filteredEpsilonInput);
                filteredEpsilonInput = filteredEpsilonInput.stream().filter(line -> line.charAt(lambdaCursor) == bitInfo.epsilonBits.charAt(lambdaCursor)).collect(Collectors.toList());
            }

            cursor++;
        }

        return "" + toDecimal(filteredGammaInput.get(0)) * toDecimal(filteredEpsilonInput.get(0));


    }

    int toDecimal(String bits) {
        int result = 0;
        for (int i = 0; i < bits.length(); i++) {
            double a = toDigit(bits.charAt(i));
            int n = bits.length()-i-1;
            result += n == 0 ? a : Math.pow(2 * a, n);
        }
        return result;
    }

    class BitInfo {
        int gamma;
        String gammaBits;
        int epsilon;
        String epsilonBits;

        void compute(List<String> input) {

            if (input.isEmpty()) {
                gammaBits = "";
                epsilonBits = "";
                gamma = 0;
                epsilon = 0;
                return;
            }

            int positionSize = input.get(0).length();
            int[][] data = new int[positionSize][2];

            for (int i = 0; i < positionSize; i++) {
                data[i] = new int[]{0, 0};
            }

            input.forEach(line -> {
                for (int i = 0; i < positionSize; i++) {
                    data[i][toDigit(line.charAt(i))]++;
                }
            });

            StringBuilder gammaBitsSb = new StringBuilder();
            StringBuilder epsilonBitsSb = new StringBuilder();

            for (int i = 0; i < positionSize; i++) {
                gammaBitsSb.append(data[i][1] >= data[i][0] ? 1 : 0);
                epsilonBitsSb.append(data[i][1] >= data[i][0] ? 0 : 1);
            }

            gammaBits = gammaBitsSb.toString();
            gamma = toDecimal(gammaBits);
            epsilonBits = epsilonBitsSb.toString();
            epsilon = toDecimal(epsilonBits);
        }
    }

    private int toDigit(char c) {
        return c == '1' ? 1 : 0;
    }
}
