package com.codingnagger.utils;

import java.util.List;
import java.util.stream.Collectors;

public class InputConverter {
    public static List<Integer> convertToIntegers(List<String> input) {
        return input.stream().map(Integer::parseInt).collect(Collectors.toList());
    }
}
