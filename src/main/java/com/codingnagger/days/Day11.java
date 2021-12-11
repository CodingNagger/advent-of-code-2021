package com.codingnagger.days;

import java.util.Arrays;
import java.util.List;

public class Day11 implements Day {

    @Override
    public String partOne(List<String> input) {
        OctopusGrid grid = new OctopusGrid(input);
        grid.iterate(100);
        return String.valueOf(grid.getFlashCount());
    }

    @Override
    public String partTwo(List<String> input) {
        OctopusGrid grid = new OctopusGrid(input);
        grid.iterateUntilSynchronisation();
        return String.valueOf(grid.getLastStep());
    }

    static class OctopusGrid {
        private final Integer[][] octopuses;

        private boolean[][] flashed;
        private long flashCount;
        private long lastStep;

        OctopusGrid(List<String> input) {
            this.octopuses = parseSingleDigitIntegerGrid(input);
        }

        public long getFlashCount() {
            print();

            return flashCount;
        }

        public void iterate(int steps) {
            flashCount = 0;

            for (int step = 0; step < steps; step++) {
                flashed = createFlashedGrid(octopuses);

                for (int y = 0; y < octopuses.length; y++) {
                    for (int x = 0; x < octopuses[y].length; x++) {
                        increment(x,y);
                    }
                }
            }
        }

        public void iterateUntilSynchronisation() {
            flashCount = 0;
            lastStep = 1;

            while (true) {
                flashed = createFlashedGrid(octopuses);

                for (int y = 0; y < octopuses.length; y++) {
                    for (int x = 0; x < octopuses[y].length; x++) {
                        increment(x,y);
                    }
                }

                boolean allFlashed = true;

                for (int y = 0; y < octopuses.length; y++) {
                    for (int x = 0; x < octopuses[y].length; x++) {
                        allFlashed &= flashed[y][x];
                    }
                }

                if (allFlashed) {
                    break;
                }

                lastStep++;
            }
        }

        private long getLastStep() {
            return lastStep;
        }

        private void increment(int x, int y) {
            if (flashed[y][x]) {
                return;
            }

            octopuses[y][x]++;
            System.out.printf("Incremented [%d][%d] - %d%n", y, x, octopuses[y][x]);

            if (octopuses[y][x] > 9) {
                System.out.printf("Flashed [%d][%d] - %d%n", y, x, octopuses[y][x]);

                flashCount++;
                octopuses[y][x] = 0;
                flashed[y][x] = true;
                incrementNeighbours(x,y);
            }
        }

        private void incrementNeighbours(int x, int y) {
            int minX = Math.max(0, x - 1);
            int maxX = Math.min(octopuses[y].length - 1, x + 1);
            int minY = Math.max(0, y - 1);
            int maxY = Math.min(octopuses.length - 1, y + 1);

            for (int cy = minY; cy <= maxY; cy++) {
                for (int cx = minX; cx <= maxX; cx++) {
                    increment(cx, cy);
                }
            }
        }

        void print() {
            System.out.println("=======================================");

            for (int y = 0; y < octopuses.length; y++) {
                for (int x = 0; x < octopuses[y].length; x++) {
                    System.out.print(octopuses[y][x]);
                }

                System.out.println();
            }
        }
    }

    private static Integer[][] parseSingleDigitIntegerGrid(List<String> input) {
        int maxY = input.size();

        Integer[][] locations = new Integer[maxY][];

        for (int i = 0; i < maxY; i++) {
            locations[i] = Arrays.stream(input.get(i).split("")).map(Integer::parseInt).toArray(Integer[]::new);
        }

        return locations;
    }

    private static boolean[][] createFlashedGrid(Integer[][] octopuses) {
        boolean[][] visited = new boolean[octopuses.length][];

        for (int i = 0; i < octopuses.length; i++) {
            visited[i] = new boolean[octopuses[i].length];
        }

        return visited;
    }


}
