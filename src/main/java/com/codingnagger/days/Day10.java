package com.codingnagger.days;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Stack;
import java.util.stream.Collectors;

public class Day10 implements Day {
    private final static Map<Character, Character> closersByOpeners = createOpenCloseMap();
    private final static Map<Character, Integer> illegalsScoreMap = createIllegalsScoreMap();
    private final static Map<Character, Integer> legalsScoreMap = createLegalsScoreMap();

    @Override
    public String partOne(List<String> input) {
        List<Character> illegals = input.stream()
                .map(this::findFirstIllegalCharacter)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .collect(Collectors.toList());


        int score = 0;

        for (char c : illegals) {
            score += illegalsScoreMap.get(c);
        }

        return "" + score;
    }

    @Override
    public String partTwo(List<String> input) {
        List<BigInteger> completionStrings = input.stream()
                .filter(line -> findFirstIllegalCharacter(line).isEmpty())
                .map(this::getCompletion)
                .filter(Optional::isPresent)
                .map(Optional::get)
                .map(this::calculateCompletionScoreForLine)
                .sorted()
                .collect(Collectors.toList());

        return completionStrings.get(completionStrings.size()/2).toString();
    }

    private BigInteger calculateCompletionScoreForLine(String s) {
        BigInteger multiplier = BigInteger.valueOf(5);
        BigInteger result = BigInteger.ZERO;

        for (char c : s.toCharArray()) {
            result = result.multiply(multiplier).add(BigInteger.valueOf(legalsScoreMap.get(c)));
        }

        return result;
    }

    private Optional<String> getCompletion(String line) {
        Stack<Character> expectedClosers = new Stack<>();

        for (char c : line.toCharArray()) {
            if (closersByOpeners.containsKey(c)) {
                expectedClosers.push(closersByOpeners.get(c));
            } else {
                expectedClosers.pop();
            }
        }

        if (expectedClosers.isEmpty()) {
            return Optional.empty();
        }

        StringBuilder sb = new StringBuilder();

        while (!expectedClosers.empty()) {
            sb.append(expectedClosers.pop());
        }

        return Optional.of(sb.toString());
    }

    private static Map<Character, Integer> createLegalsScoreMap() {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put(')', 1);
        map.put(']', 2);
        map.put('}', 3);
        map.put('>', 4);
        return map;
    }


    private static Map<Character, Integer> createIllegalsScoreMap() {
        HashMap<Character, Integer> map = new HashMap<>();
        map.put(')', 3);
        map.put(']', 57);
        map.put('}', 1197);
        map.put('>', 25137);
        return map;
    }

    private static Map<Character, Character> createOpenCloseMap() {
        HashMap<Character, Character> map = new HashMap<>();
        map.put('(', ')');
        map.put('[', ']');
        map.put('{', '}');
        map.put('<', '>');
        return map;
    }

    public Optional<Character> findFirstIllegalCharacter(String s) {
        Stack<Character> expectedClosers = new Stack<>();

        for (char c : s.toCharArray()) {
            if (closersByOpeners.containsKey(c)) {
                expectedClosers.push(closersByOpeners.get(c));
            } else if (expectedClosers.peek().equals(c)) {
                expectedClosers.pop();
            } else {
                return Optional.of(c);
            }
        }

        return Optional.empty();
    }
}
