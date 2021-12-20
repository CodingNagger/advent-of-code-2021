package com.codingnagger.days;

import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class Day20 implements Day {
    @Override
    public String partOne(List<String> input) {
        ImageEnhancer enhancer = ImageEnhancer.parse(input);
        enhancer.enhanceImage(2);
        enhancer.printImage();
        return String.valueOf(enhancer.litPixelCount);
    }

    @Override
    public String partTwo(List<String> input) {
        ImageEnhancer enhancer = ImageEnhancer.parse(input);
        enhancer.enhanceImage(50);
        enhancer.printImage();
        return String.valueOf(enhancer.litPixelCount);
    }

    static class ImageEnhancer {
        private static final char DARK = '.';
        private static final char LIGHT = '#';
        private HashSet<Position> image;
        private final String algorithm;
        private int litPixelCount;
        private boolean noSpecialTreatment;

        private ImageEnhancer(String algorithm, HashSet<Position> image) {
            this.algorithm = algorithm;
            this.image = image;
        }

        public static ImageEnhancer parse(List<String> input) {
            boolean parsingAlgorithm = true;
            StringBuilder algorithmBuilder = new StringBuilder();
            HashSet<Position> image = new HashSet<>();
            int imageLineCursor = 0;

            for (String line : input) {
                if (line.isBlank()) {
                    parsingAlgorithm = false;
                }
                else if (parsingAlgorithm) {
                    algorithmBuilder.append(line);
                } else {
                    if (Arrays.stream(line.split("")).allMatch(s -> s.charAt(0) == DARK)) {
                        continue;
                    }

                    char[] charLine = line.toCharArray();

                    for (int x = 0; x < charLine.length; x++) {
                        if (charLine[x] == LIGHT) {
                            image.add(new Position(x, imageLineCursor));
                        }
                    }

                    imageLineCursor++;
                }
            }

            return new ImageEnhancer(algorithmBuilder.toString(), image);
        }

        public char getRefinedPixel(HashSet<Position> originalImage, int x, int y) {
            StringBuilder sb = new StringBuilder();

            Position topLeft = getTopLeftCorner(originalImage);
            Position bottomRight = getBottomRightCorner(originalImage);

            for (int yy = y-1; yy <= y+1; yy++) {
                for (int xx = x-1; xx <= x+1; xx++) {
                    Position p = new Position(xx,yy);

                    if (noSpecialTreatment) {
                        sb.append(originalImage.contains(new Position(xx,yy)) ? '1' : '0');
                    } else {
                        if (algorithm.charAt(0) == DARK ||
                                (yy >= topLeft.y && yy <= bottomRight.y && xx >= topLeft.x && xx <= bottomRight.x)) {
                            sb.append(originalImage.contains(new Position(xx,yy)) ? '1' : '0');
                        } else {
                            sb.append('1');
                        }
                    }
                }
            }

            return algorithm.charAt(Integer.parseInt(sb.toString(), 2));
        }

        void enhanceImage(int times) {
            for (int step = 0; step < times; step++) {
                System.out.println("Step "+(step+1));
                noSpecialTreatment = step % 2 == 0;
                this.image = enhance(this.image);
            }
        }

        private HashSet<Position> enhance(HashSet<Position> originalImage) {
            HashSet<Position> image = new HashSet<>();

            Position topLeftCorner = getTopLeftCorner(originalImage);
            Position bottomRightCorner = getBottomRightCorner(originalImage);

            litPixelCount = 0;

            for (int y = topLeftCorner.y-2; y < bottomRightCorner.y+2; y++) {
                for (int x = topLeftCorner.x-2; x < bottomRightCorner.x+2; x++) {
//                    if (y < yShift || y + yShift >= newMaxY || x < xShift || x + xShift >= newMaxX) {
//                        image.put(new Position(x, y), DARK);
//                        continue;
//                    }

                    char refinedPixel = getRefinedPixel(originalImage, x, y);

                    if (refinedPixel == LIGHT) {
                        image.add(new Position(x+1, y+1));
                        litPixelCount++;
                    }
                }
            }

            return image;
        }

        public void printImage() {
            System.out.println();
            System.out.println();

            Position topLeftCorner = getTopLeftCorner(image);
            Position bottomRightCorner = getBottomRightCorner(image);

            for (int y = topLeftCorner.y; y <= bottomRightCorner.y; y++) {
                for (int x = topLeftCorner.x; x <= bottomRightCorner.x; x++) {
                    System.out.print(image.contains(new Position(x,y)) ? LIGHT : DARK);
                }
                System.out.println();
            }
        }

        private static Position getTopLeftCorner(HashSet<Position> image) {
            int minX = image.stream().min(Comparator.comparingInt(p -> p.x)).get().x;
            int minY = image.stream().min(Comparator.comparingInt(p -> p.y)).get().y;
            return new Position(minX, minY);
        }

        private static Position getBottomRightCorner(HashSet<Position> image) {
            int minX = image.stream().max(Comparator.comparingInt(p -> p.x)).get().x;
            int minY = image.stream().max(Comparator.comparingInt(p -> p.y)).get().y;
            return new Position(minX, minY);
        }
    }

    public static class Position {
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

        @Override
        public String toString() {
            return "Position{" +
                    "x=" + x +
                    ", y=" + y +
                    '}';
        }
    }
}
