package com.codingnagger.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

public class Day9 implements Day {
    @Override
    public String partOne(List<String> input) {
        Integer[][] locations = parseLocations(input);
        List<Integer> lowestPoints = findLowestPoints(locations);
        return String.valueOf(lowestPoints.size() + lowestPoints.stream().mapToInt(i -> i).sum());
    }

    private List<Integer> findLowestPoints(Integer[][] locations) {
        List<Integer> lowestPoints = new ArrayList<>();

        for (int y = 0; y < locations.length; y++) {
            for (int x = 0; x < locations[y].length; x++) {
                if (isLowestPoint(x, y, locations)) {
                    lowestPoints.add(locations[y][x]);
                }
            }
        }

        return lowestPoints;
    }

    private boolean isLowestPoint(int x, int y, Integer[][] locations) {
        int minX = Math.max(0, x - 1);
        int maxX = Math.min(locations[y].length - 1, x + 1);
        int minY = Math.max(0, y - 1);
        int maxY = Math.min(locations.length - 1, y + 1);

        int location = locations[y][x];

        for (int cy = minY; cy <= maxY; cy++) {
            for (int cx = minX; cx <= maxX; cx++) {
                if (cx == x && cy == y) {
                    continue;
                }

                if (locations[cy][cx] <= location) {
                    return false;
                }
            }
        }

        return true;
    }

    private Integer[][] parseLocations(List<String> input) {
        int maxY = input.size();

        Integer[][] locations = new Integer[maxY][];

        for (int i = 0; i < maxY; i++) {
            locations[i] = Arrays.stream(input.get(i).split("")).map(Integer::parseInt).toArray(Integer[]::new);
        }

        return locations;
    }

    @Override
    public String partTwo(List<String> input) {
        List<List<Position>> basins = findBasins(input);
        basins.sort((b1, b2) -> b2.size() - b1.size());
        return String.valueOf(basins.get(0).size() * basins.get(1).size() * basins.get(2).size());
    }

    private List<List<Position>> findBasins(List<String> input) {
        Integer[][] locations = parseLocations(input);
        List<Position> lowestPointPositions = findLowestPointPositions(locations);
        return lowestPointPositions.stream().map(p -> findBasin(p, locations, Collections.emptyList())).collect(Collectors.toList());
    }

    private List<Position> findBasin(Position current, Integer[][] locations, List<Position> explored) {
        if (explored.contains(current) || locations[current.y][current.x] == 9) {
            return Collections.emptyList();
        }

        int x = current.x;
        int y = current.y;
        int minX = Math.max(0, x - 1);
        int maxX = Math.min(locations[y].length - 1, x + 1);
        int minY = Math.max(0, y - 1);
        int maxY = Math.min(locations.length - 1, y + 1);

        List<Position> basin = new ArrayList<>(explored);

        basin.add(current);

        if (y != minY) {
            basin.addAll(findBasin(new Position(x, minY), locations, basin));
        }

        if (y != maxY) {
            basin.addAll(findBasin(new Position(x, maxY), locations, basin));
        }

        if (x != minX) {
            basin.addAll(findBasin(new Position(minX, y), locations, basin));
        }

        if (x != maxX) {
            basin.addAll(findBasin(new Position(maxX, y), locations, basin));
        }

        return basin.stream().distinct().collect(Collectors.toList());
    }

    private List<Position> findLowestPointPositions(Integer[][] locations) {
        List<Position> lowestPointsPositions = new ArrayList<>();

        for (int y = 0; y < locations.length; y++) {
            for (int x = 0; x < locations[y].length; x++) {
                if (isLowestPoint(x, y, locations)) {
                    lowestPointsPositions.add(new Position(x, y));
                }
            }
        }

        return lowestPointsPositions;
    }

    public class Position {
        private final int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y);
        }
    }
}
