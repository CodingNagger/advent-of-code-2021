package com.codingnagger.days;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day12 implements Day {
    private static final String START = "start";
    private static final String END = "end";

    @Override
    public String partOne(List<String> input) {
        Map<String, List<String>> map = createMaps(input);
        List<List<String>> paths = findPaths(map, START, END, Collections.emptyList(), false);
        return String.valueOf(paths.size());
    }

    @Override
    public String partTwo(List<String> input) {
        Map<String, List<String>> map = createMaps(input);
        List<List<String>> paths = findPaths(map, START, END, Collections.emptyList(), true);
        return String.valueOf(paths.size());
    }

    private List<List<String>> findPaths(Map<String, List<String>> map, String start, String end, List<String> visited,
                                         boolean canDoubleVisitSmallCave) {
        if (start.equals(end)) {
            return Collections.singletonList(Collections.singletonList(start));
        }

        List<String> currentPath = new ArrayList<>(visited);
        currentPath.add(start);

        boolean isSecondSmallCaveVisit = start.toLowerCase().equals(start) && visited.contains(start);

        List<String> visitable = map.get(start).stream()
                .filter(cave -> !START.equals(cave))
                .filter(cave -> cave.toUpperCase().equals(cave) || !visited.contains(cave) || (!isSecondSmallCaveVisit && canDoubleVisitSmallCave))
                .collect(Collectors.toList());

        List<List<String>> paths = new ArrayList<>();

        visitable.forEach(cave -> paths.addAll(findPaths(map, cave, end, currentPath,
                !isSecondSmallCaveVisit && canDoubleVisitSmallCave)));

        return paths;
    }

    private static Map<String, List<String>> createMaps(List<String> input) {
        Map<String, List<String>> map = new HashMap<>();

        input.stream().map(line -> line.split("-"))
                .forEach(path -> {
                    map.computeIfAbsent(path[0], key -> new ArrayList<>()).add(path[1]);
                    map.computeIfAbsent(path[1], key -> new ArrayList<>()).add(path[0]);
                });

        return map;
    }
}
