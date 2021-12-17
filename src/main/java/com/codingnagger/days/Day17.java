package com.codingnagger.days;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Vector;
import java.util.stream.Collectors;

public class Day17 implements Day {
    int maxY = Integer.MIN_VALUE;

    @Override
    public String partOne(List<String> input) {
        final Area targetArea = parseTargetArea(input.get(0));
        final Position origin = new Position(0,0);

        Integer maxY = Integer.MIN_VALUE;
        Position probeVector = new Position(1, 0);
        Position finalPoint = new Position(Integer.MAX_VALUE, Integer.MIN_VALUE);

        while (!targetArea.containsPosition(finalPoint) ) {
            Answer answer = highestYHittingTarget(targetArea, probeVector, origin);

            if (answer.getMaxY().isPresent()) {
                finalPoint = answer.getFinalPosition();
                maxY = answer.getMaxY().get();
            } else {
                probeVector = probeVector.applyVector(new Position(1, 0));
            }
        }

        int stepsToExplore = -targetArea.bottomRight.y - probeVector.y;
        probeVector = probeVector.applyVector(new Position(0, 1));

        for (int i = 0; i < stepsToExplore; i++) {
            Answer answer = highestYHittingTarget(targetArea, probeVector, origin);

            probeVector = probeVector.applyVector(new Position(0, 1));

            if (answer.getMaxY().isPresent()) {
                maxY = answer.getMaxY().get();
            }
        }

        return maxY.toString();
    }

    @Override
    public String partTwo(List<String> input) {
        final Area targetArea = parseTargetArea(input.get(0));
        final Position origin = new Position(0,0);
        int counter = 0;

        for (int x = 0; x <= targetArea.bottomRight.x; x++) {
            for (int y = targetArea.bottomRight.y; y <= -targetArea.bottomRight.y; y++) {
                if (highestYHittingTarget(targetArea, new Position(x,y), origin).getMaxY().isPresent()) {
                    counter++;
                }
            }
        }
        return String.valueOf(counter);
    }

    private Answer highestYHittingTarget(final Area targetArea, final Position vectorStart, final Position probeStart) {
        final Position frictionVector = new Position(-1, -1);
        final Position divingVector = new Position(0, -1);

        Position probe = probeStart;
        Position vector = vectorStart;

        int maxY = probe.y;

        while (targetArea.canBeReachedBy(probe) && !targetArea.containsPosition(probe)) {
            probe = probe.applyVector(vector);

            if (vector.x == 0) {
                vector = vector.applyVector(divingVector);
            } else {
                vector = vector.applyVector(frictionVector);
            }

            maxY = Math.max(maxY, probe.y);
        }

        return new Answer(probe, targetArea.containsPosition(probe) ? maxY : null);
    }

    static Area parseTargetArea(String line) {
        List<String> s = Arrays.stream(line.split("x=")[1].split(", y=")).collect(Collectors.toList());
        List<Integer> xBounds = Arrays.stream(s.get(0).split("\\.\\.")).map(Integer::parseInt).collect(Collectors.toList());
        List<Integer> yBounds = Arrays.stream(s.get(1).split("\\.\\.")).map(Integer::parseInt).collect(Collectors.toList());

        return new Area(
                new Position(Math.min(xBounds.get(0), xBounds.get(1)), Math.max(yBounds.get(0), yBounds.get(1))),
                new Position(Math.max(xBounds.get(0), xBounds.get(1)), Math.min(yBounds.get(0), yBounds.get(1))));
    }

    public static class Answer {
        private final Position finalPosition;
        private final Integer maxY;

        public Answer(Position finalPosition, Integer maxY) {
            this.finalPosition = finalPosition;
            this.maxY = maxY;
        }

        public Position getFinalPosition() {
            return finalPosition;
        }

        public Optional<Integer> getMaxY() {
            return Optional.ofNullable(maxY);
        }
    }

    public static class Area {
        private final Position topLeft, bottomRight;

        public Area(Position topLeft, Position bottomRight) {
            this.topLeft = topLeft;
            this.bottomRight = bottomRight;
        }

        public Position getTopLeft() {
            return topLeft;
        }

        public Position getBottomRight() {
            return bottomRight;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Area area = (Area) o;
            return Objects.equals(topLeft, area.topLeft) && Objects.equals(bottomRight, area.bottomRight);
        }

        @Override
        public int hashCode() {
            return Objects.hash(topLeft, bottomRight);
        }

        public boolean tooFar(Position p) {
            return p.x > bottomRight.x;
        }

        public boolean tooLow(Position p) {
            return p.y < bottomRight.y;
        }

        public boolean canBeReachedBy(Position p) {
            return !tooFar(p) && !tooLow(p);
        }

        public boolean containsPosition(Position p) {
            return p.x >= topLeft.x && p.x <= bottomRight.x && p.y <= topLeft.y && p.y >= bottomRight.y;
        }

        @Override
        public String toString() {
            return "Area{" +
                    "topLeft=" + topLeft +
                    ", bottomRight=" + bottomRight +
                    '}';
        }
    }

    public static class Position {
        private final int x, y;

        public Position(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public Position applyVector(Position v) {
            return new Position(x + v.x, y + v.y);
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

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
