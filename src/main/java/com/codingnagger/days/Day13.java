package com.codingnagger.days;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Day13 implements Day  {
    @Override
    public String partOne(List<String> input) {
        Origami origami = new Origami(input);
        origami.foldOnce();
        return String.valueOf(origami.countVisibleDots());
    }

    @Override
    public String partTwo(List<String> input) {
        Origami origami = new Origami(input);
        origami.foldAllTheWay();
        origami.print();
        return "";
    }

    static class Origami {
        private boolean[][] grid;
        List<String> foldInstructions;

        public Origami(List<String> input) {
            List<int[]> coordinates = input.stream()
                    .filter(s -> s.matches("\\d+,\\d+"))
                    .map(s -> s.split(","))
                    .map(s -> new int[]{Integer.parseInt(s[0]), Integer.parseInt(s[1])})
                    .collect(Collectors.toList());

            int maxX = coordinates.stream().max(Comparator.comparingInt(a -> a[0])).get()[0];
            int maxY = coordinates.stream().max(Comparator.comparingInt(a -> a[1])).get()[1];

            grid = new boolean[maxY+1][];

            for (int y = 0; y <= maxY; y++) {
                grid[y] = new boolean[maxX+1];
            }

            coordinates.forEach(c -> {
                grid[c[1]][c[0]] = true;
            });

            foldInstructions = input.stream().skip(coordinates.size()+1).collect(Collectors.toList());
        }

        public void foldOnce() {
            grid = fold(grid, foldInstructions.get(0));
        }

        private boolean[][] fold(boolean[][] grid, String foldInstruction) {
            if (foldInstruction.contains("x=")) {
                return foldLeft(grid, Integer.parseInt(foldInstruction.split("x=")[1]));
            } else {
                return foldUp(grid, Integer.parseInt(foldInstruction.split("y=")[1]));
            }
        }

        private boolean[][] foldUp(boolean[][] grid, int yFoldLine) {
            boolean[][] newGrid = new boolean[yFoldLine][];

            for (int y = 0; y < yFoldLine; y++) {
                newGrid[y] = new boolean[grid[y].length];

                for (int x = 0; x < grid[y].length; x++) {
                    newGrid[y][x] = grid[y][x];
                }
            }

            for (int y = yFoldLine+1; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    newGrid[2*yFoldLine-y][x] |= grid[y][x];
                }
            }

            return newGrid;
        }

        private boolean[][] foldLeft(boolean[][] grid, int xFoldLine) {
            boolean[][] newGrid = new boolean[grid.length][];

            for (int y = 0; y < grid.length; y++) {
                newGrid[y] = new boolean[xFoldLine];

                for (int x = 0; x < xFoldLine; x++) {
                    newGrid[y][x] = grid[y][x];
                }

                for (int x = xFoldLine+1; x < grid[y].length; x++) {
                    newGrid[y][2*xFoldLine-x] |= grid[y][x];
                }
            }

            return newGrid;
        }

        public void print() {
            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    System.out.print(grid[y][x] ? '#' : '.');
                }
                System.out.println();
            }
        }

        public int countVisibleDots() {
            int sum = 0;

            for (int y = 0; y < grid.length; y++) {
                for (int x = 0; x < grid[y].length; x++) {
                    sum += grid[y][x] ? 1 : 0;
                }
            }

            return sum;
        }

        public void foldAllTheWay() {
            for (String instruction : foldInstructions) {
                grid = fold(grid, instruction);
            }
        }
    }
}
