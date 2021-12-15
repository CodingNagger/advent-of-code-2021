package com.codingnagger.days;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.PriorityQueue;

public class Day15 implements Day {
    @Override
    public String partOne(List<String> input) {
        Integer[][] locations = parseLocations(input);
        return String.valueOf(findShortestDistanceByThePowerOfDijkstra(
                new Position(locations[0].length-1, locations.length-1, Integer.MAX_VALUE),
                locations, createDistances(locations)));
    }

    @Override
    public String partTwo(List<String> input) {
        Integer[][] locations = parseAllLocations(input);
        return String.valueOf(findShortestDistanceByThePowerOfDijkstra(
                new Position(locations[0].length-1, locations.length-1, Integer.MAX_VALUE),
                locations, createDistances(locations)));
    }

    private Integer[][] parseAllLocations(List<String> input) {
        int maxMultiplier = 5;
        int maxCost = 9;
        Integer[][] locations = parseLocations(input);
        Integer[][] allLocations = new Integer[locations.length*maxMultiplier][];
        int baseLineLength = locations[0].length;

        for (int y = 0; y < locations.length; y++) {
            allLocations[y] = new Integer[locations[0].length*maxMultiplier];

            for (int xMultiplier = 0; xMultiplier < maxMultiplier; xMultiplier++) {
                for (int x = 0; x < locations[0].length; x++) {
                    int cost = (locations[y % locations.length][x] + xMultiplier);
                    if (cost > maxCost) {
                        cost -= maxCost;
                    }

                    allLocations[y][x + baseLineLength * xMultiplier] = cost;
                }
            }
        }

        for (int y = locations.length; y < allLocations.length; y++) {
            allLocations[y] = new Integer[locations[0].length*maxMultiplier];

            for (int x = 0; x < allLocations[0].length; x++) {
                int cost = allLocations[y - locations.length][x] + 1;
                if (cost >= 10) {
                    cost -= 9;
                }

                allLocations[y][x] = cost;
            }
        }

//        print(allLocations);

        return allLocations;
    }

    private Integer findShortestDistanceByThePowerOfDijkstra(Position end, final Integer[][] locations, Integer[][] distances) {
        PriorityQueue<Position> queue = new PriorityQueue<>(Comparator.comparingInt(p -> p.distance));
        queue.add(new Position(0,0,distances[0][0]));

        while (!queue.isEmpty()) {
            Position current = queue.poll();
            List<Position> visitableNeighbors = getNeighbors(current, locations);

            for (Position neighbor : visitableNeighbors) {
                int currentDistanceToNeighbor = distances[current.y][current.x] + locations[neighbor.y][neighbor.x];

                if (distances[neighbor.y][neighbor.x] > currentDistanceToNeighbor) {
                    if (distances[neighbor.y][neighbor.x] != Integer.MAX_VALUE)
                    {
                        queue.remove(neighbor);
                    }

                    distances[neighbor.y][neighbor.x] = currentDistanceToNeighbor;
                    queue.add(new Position(neighbor.x, neighbor.y, currentDistanceToNeighbor));
                }
            }
        }

        return distances[end.y][end.x];
    }

    private void print(Integer[][] distances) {
        System.out.println();
        for (int y = 0; y < distances.length; y++) {
            for (int x = 0; x < distances[y].length; x++) {
                System.out.print(distances[y][x] == Integer.MAX_VALUE || distances[y][x] == null ? "." : String.valueOf(distances[y][x]));
                System.out.print(' ');
            }
            System.out.println();
        }
    }

    private Integer[][] createDistances(Integer[][] locations) {
        Integer[][] distances = new Integer[locations.length][];

        for (int i = 0; i < distances.length; i++) {
            distances[i] = new Integer[locations[i].length];
            Arrays.fill(distances[i], Integer.MAX_VALUE);
        }

        distances[0][0] = 0;

        return distances;
    }

    private Integer[][] parseLocations(List<String> input) {
        int maxY = input.size();

        Integer[][] locations = new Integer[maxY][];

        for (int i = 0; i < maxY; i++) {
            locations[i] = Arrays.stream(input.get(i).split("")).map(Integer::parseInt).toArray(Integer[]::new);
        }

        return locations;
    }

    private List<Position> getNeighbors(Position current, Integer[][] locations) {
        int x = current.x;
        int y = current.y;
        int minX = Math.max(0, x - 1);
        int maxX = Math.min(locations[y].length - 1, x + 1);
        int minY = Math.max(0, y - 1);
        int maxY = Math.min(locations.length - 1, y + 1);

        List<Position> neighbors = new ArrayList<>();

        if (y != minY) {
            neighbors.add(new Position(x, minY, locations[minY][x]));
        }

        if (y != maxY) {
            neighbors.add(new Position(x, maxY, locations[maxY][x]));
        }

        if (x != minX) {
            neighbors.add(new Position(minX, y, locations[y][minX]));
        }

        if (x != maxX) {
            neighbors.add(new Position(maxX, y, locations[y][maxX]));
        }

        return neighbors;
    }

    public class Position {
        private final int x, y, distance;

        public Position(int x, int y, int distance) {
            this.x = x;
            this.y = y;
            this.distance = distance;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Position position = (Position) o;
            return x == position.x && y == position.y && distance == position.distance;
        }

        @Override
        public int hashCode() {
            return Objects.hash(x, y, distance);
        }
    }
}
