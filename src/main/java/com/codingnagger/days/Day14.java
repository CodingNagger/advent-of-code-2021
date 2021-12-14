package com.codingnagger.days;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day14 implements Day {
    @Override
    public String partOne(List<String> input) {
        return polymerize(input, 10);
    }

    @Override
    public String partTwo(List<String> input) {
        return polymerize(input, 40);
    }

    private String polymerize(List<String> input, int rounds) {
        LinkedHashMap<String, BigInteger> bucket = createBucket(input.get(0));
        Map<String, Character> insertionPairs = input.stream().skip(2).map(i -> i.split(" -> ")).collect(Collectors.toUnmodifiableMap(i -> i[0], i -> i[1].charAt(0)));

        for (int i = 0; i < rounds; i++) {
            bucket = polymerize(bucket, insertionPairs);
        }

        Map<Character, BigInteger> countCharacters = countCharacters(bucket);

        return String.valueOf(countCharacters.values().stream().max(Comparator.naturalOrder()).get().subtract(countCharacters.values().stream().min(Comparator.naturalOrder()).get()));
    }

    private LinkedHashMap<String, BigInteger> createBucket(String polymerTemplate) {
        LinkedHashMap<String, BigInteger> bucket = new LinkedHashMap<>();

        List<Character> template = Arrays.stream(polymerTemplate.split("")).map(s -> s.charAt(0)).collect(Collectors.toList());

        for (int i = 0; i < template.size()-1; i++) {
            String pair = new String(new char[]{template.get(i), template.get(i+1)});
            if (bucket.containsKey(pair)) {
                bucket.put(pair, bucket.get(pair).add(BigInteger.ONE));
            } else {
                bucket.put(pair, BigInteger.ONE);
            }
        }

        return bucket;
    }

    private Map<Character, BigInteger> countCharacters(LinkedHashMap<String, BigInteger> polymerTemplate) {
        Map<Character, BigInteger> characterCounts = new HashMap<>();

        for (Map.Entry<String, BigInteger> pair : polymerTemplate.entrySet()) {
            char[] chars = pair.getKey().toCharArray();

            if (characterCounts.isEmpty()) {
                characterCounts.put(chars[0], polymerTemplate.get(pair.getKey()));
            }

            if (!characterCounts.containsKey(chars[1])) {
                characterCounts.put(chars[1], polymerTemplate.get(pair.getKey()));
            } else {
                characterCounts.put(chars[1], characterCounts.get(chars[1]).add(polymerTemplate.get(pair.getKey())));
            }
        }

        return characterCounts;
    }

    private LinkedHashMap<String, BigInteger> polymerize(LinkedHashMap<String, BigInteger> bucket, Map<String, Character> insertionPairs) {
        LinkedHashMap<String, BigInteger> newBucket = new LinkedHashMap<>();

        for (String key : bucket.keySet()) {
            char c = insertionPairs.get(key);
            String firstNewPair = new String(new char[]{key.charAt(0), c});
            String secondNewPair = new String(new char[]{c, key.charAt(1)});

            if (newBucket.containsKey(firstNewPair)) {
                newBucket.put(firstNewPair, newBucket.get(firstNewPair).add(bucket.get(key)));
            } else {
                newBucket.put(firstNewPair, bucket.get(key));
            }

            if (newBucket.containsKey(secondNewPair)) {
                newBucket.put(secondNewPair, newBucket.get(secondNewPair).add(bucket.get(key)));
            } else {
                newBucket.put(secondNewPair, bucket.get(key));
            }
        }

        return newBucket;
    }
}
