package com.codingnagger.days;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day8 implements Day {
    public static Map<Integer, Character> simpleDigitCandidatesBySize = createSimpleDigitDefinitions();

    public static Map<Integer, Character> createSimpleDigitDefinitions() {
        HashMap<Integer, Character> digitDefinition = new HashMap<>();
        digitDefinition.put(2, '1');
        digitDefinition.put(4, '4');
        digitDefinition.put(3, '7');
        digitDefinition.put(7, '8');
        return digitDefinition;
    }

    public static List<SevenSegmentDisplay> parse(List<String> input) {
        return input.stream().map(Day8::parseEntry).collect(Collectors.toList());
    }

    public static SevenSegmentDisplay parseEntry(String entry) {
        String[] entryParts = entry.split(" \\| ");
        return new SevenSegmentDisplay(Arrays.asList(entryParts[0].split(" ")), Arrays.asList(entryParts[1].split(" ")));
    }

    @Override
    public String partOne(List<String> input) {
        List<SevenSegmentDisplay> displays = parse(input);

        return "" + displays.stream()
                .map(d -> d.digits)
                .flatMap(Collection::stream)
                .filter(d -> simpleDigitCandidatesBySize.containsKey(d.length()))
                .count();
    }

    @Override
    public String partTwo(List<String> input) {
        return "" + input.stream().map(Day8::parseEntry).mapToInt(e -> e.output()).sum();
    }

    static class SevenSegmentDisplay {
        List<String> signalPatterns;
        List<String> digits;
        Map<String, Character> foundDigits;
        Map<Character, String> digitPatterns;
        Map<Character, Character> segmentMap;

        public SevenSegmentDisplay(List<String> signalPatterns, List<String> digits) {
            this.signalPatterns = signalPatterns;
            this.digits = digits;
        }

        public String determineOutput() {
            return digits.stream().map(this::getMatchingDigit).collect(Collectors.joining(""));
        }

        public int output() {
            findDigits();
            return Integer.parseInt(determineOutput());
        }

        private String getMatchingDigit(String test) {
            return foundDigits.entrySet().stream()
                    .filter(d -> {
                        char[] a = d.getKey().toCharArray();
                        char[] b = test.toCharArray();

                        Arrays.sort(a);
                        Arrays.sort(b);

                        return Arrays.equals(a, b);
                    })
                    .map(d -> d.getValue() + "")
                    .findFirst()
                    .get();
        }

        public char getDigit(String pattern) {
            return foundDigits.get(pattern);
        }

        public void findDigits() {
            foundDigits = new HashMap<>();
            digitPatterns = new HashMap<>();

            for (String pattern : signalPatterns) {
                if (simpleDigitCandidatesBySize.containsKey(pattern.length())) {
                    foundDigits.put(pattern, simpleDigitCandidatesBySize.get(pattern.length()));
                    digitPatterns.put(simpleDigitCandidatesBySize.get(pattern.length()), pattern);
                }
            }

            segmentMap = findSegments();

            List<String> patternsToFind = signalPatterns.stream().filter(p -> !foundDigits.containsKey(p)).collect(Collectors.toList());

            foundDigits.put(patternsToFind.stream().filter(this::isZero).findFirst().get(), '0');
            foundDigits.put(patternsToFind.stream().filter(this::isTwo).findFirst().get(), '2');
            foundDigits.put(patternsToFind.stream().filter(this::isThree).findFirst().get(), '3');
            foundDigits.put(patternsToFind.stream().filter(this::isFive).findFirst().get(), '5');
            foundDigits.put(patternsToFind.stream().filter(this::isSix).findFirst().get(), '6');
            foundDigits.put(patternsToFind.stream().filter(this::isNine).findFirst().get(), '9');
        }

        private boolean isNine(String s) {
            return s.length() == 6 && matches("a,b,c,d,f,g", s);
        }

        private boolean isSix(String s) {
            return s.length() == 6 && matches("a,b,d,e,f,g", s);
        }

        private boolean isFive(String s) {
            return s.length() == 5 && matches("a,b,d,f,g", s);
        }

        private boolean isThree(String s) {
            return s.length() == 5 && matches("a,c,d,f,g", s);
        }

        private boolean isTwo(String s) {
            return s.length() == 5 && matches("a,c,d,e,g", s);
        }

        private boolean matches(String csvValue, String test) {
            return Arrays.stream(csvValue.split(",")).map(c -> segmentMap.get(c.charAt(0))).allMatch(c -> containsChar(test, c));
        }

        private boolean isZero(String s) {
            return s.length() == 6 && matches("a,b,c,e,f,g", s);
        }

        public Map<Character, Character> findSegments() {
            HashMap<Character, Character> segments = new HashMap<>();

            char[] partsOfOne = digitPatterns.get('1').toCharArray();

            if (isC(partsOfOne[0])) {
                segments.put('c', partsOfOne[0]);
                segments.put('f', partsOfOne[1]);
            } else {
                segments.put('c', partsOfOne[1]);
                segments.put('f', partsOfOne[0]);
            }

            char a = digitPatterns.get('7').replace("" + partsOfOne[0], "").replace("" + partsOfOne[1], "").charAt(0);

            segments.put('a', a);

            char[] partsOfEight = digitPatterns.get('8').toCharArray();

            for (char c : partsOfEight) {
                if (isD(c)) {
                    segments.put('d', c);
                } else if (isE(c)) {
                    segments.put('e', c);
                } else if (isG(c)) {
                    segments.put('g', c);
                }
            }

            for (char c : partsOfEight) {
                if (segments.containsValue(c)) {
                    continue;
                }

                segments.put('b', c);
            }

            return segments;
        }

        private boolean isG(char c) {
            return containsChar(digitPatterns.get('8'), c) &&
                    !containsChar(digitPatterns.get('1'), c) &&
                    !containsChar(digitPatterns.get('4'), c) &&
                    !containsChar(digitPatterns.get('7'), c) &&
                    countAppearancesPatternsWithLength(c, 6) == 3 &&
                    countAppearancesPatternsWithLength(c, 5) == 3;
        }

        private boolean isE(char c) {
            return containsChar(digitPatterns.get('8'), c) &&
                    countAppearancesPatternsWithLength(c, 6) == 2 &&
                    countAppearancesPatternsWithLength(c, 5) == 1;
        }

        private boolean isD(char c) {
            return containsChar(digitPatterns.get('8'), c) &&
                    countAppearancesPatternsWithLength(c, 6) == 2 &&
                    countAppearancesPatternsWithLength(c, 5) == 3;
        }

        private boolean isC(char c) {
            return containsChar(digitPatterns.get('1'), c) &&
                    countAppearancesPatternsWithLength(c, 6) == 2 &&
                    countAppearancesPatternsWithLength(c, 5) == 2;
        }

        private boolean containsChar(String value, char test) {
            return value.contains(test + "");
        }

        private long countAppearancesPatternsWithLength(char c, int length) {
            return signalPatterns.stream()
                    .filter(p -> p.length() == length)
                    .filter(p -> containsChar(p, c))
                    .count();
        }
    }
}
