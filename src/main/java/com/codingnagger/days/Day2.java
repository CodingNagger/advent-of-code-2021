package com.codingnagger.days;

import java.util.List;

import static com.codingnagger.utils.InputConverter.convertToIntegers;

public class Day2 implements Day {

    @Override
    public String partOne(List<String> input) {
        Submarine s = new Submarine();
        input.forEach(i -> s.move(i));
        return "" + s.depth * s.horizontalPosition;
    }

    @Override
    public String partTwo(List<String> input) {
        Submarine2 s = new Submarine2();
        input.forEach(i -> s.move(i));
        return "" + s.depth * s.horizontalPosition;
    }

    public class Submarine2 {
        int depth = 0;
        int horizontalPosition = 0;
        int aim = 0;

        void move(String action) {
            String[] actionDetails = action.split(" ");
            String command = actionDetails[0];
            int value = Integer.parseInt(actionDetails[1]);

            switch (command) {
                case "forward":
                    horizontalPosition += value;
                    depth += aim * value;
                    break;

                case "down":
                    aim += value;
                    break;

                case "up":
                    aim -= value;
                    break;
            }
        }
    }

    public class Submarine {
        int depth = 0;
        int horizontalPosition = 0;

        void move(String action) {
            String[] actionDetails = action.split(" ");
            String command = actionDetails[0];
            int value = Integer.parseInt(actionDetails[1]);

            switch (command) {
                case "forward":
                    horizontalPosition += value;
                    break;

                case "down":
                    depth += value;
                    break;

                case "up":
                    depth -= value;
                    break;
            }
        }
    }
}
