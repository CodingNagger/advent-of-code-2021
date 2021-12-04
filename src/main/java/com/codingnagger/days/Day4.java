package com.codingnagger.days;

import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Day4 implements Day {
    @Override
    public String partOne(List<String> input) {
        BingoGame game = parseGame(input);
        game.play();
        return "" + game.getWinner().getScore();
    }

    @Override
    public String partTwo(List<String> input) {
        FudgedBingoGame game = parseFudgedGame(input);
        game.play();
        return "" + game.getLastWinner().getScore();
    }

    FudgedBingoGame parseFudgedGame(List<String> input) {
        List<BigDecimal> draw = getBigDecimalStream(input.get(0), ",").collect(Collectors.toList());
        FudgedBingoGame game = new FudgedBingoGame(draw);
        return (FudgedBingoGame) parseGame(draw, game, input);
    }

    BingoGame parseGame(List<String> input) {
        List<BigDecimal> draw = getBigDecimalStream(input.get(0), ",").collect(Collectors.toList());
        BingoGame game = new BingoGame(draw);
        return parseGame(draw, game, input);
    }

    BingoGame parseGame(List<BigDecimal> draw, BingoGame game, List<String> input) {
        BingoBoard board = new BingoBoard();

        for (int i = 2; i < input.size(); i++) {
            String line = input.get(i);

            if (line.isBlank()) {
                game.addBoard(board);
                board = new BingoBoard();
            } else {
                board.addLine(line);
            }
        }

        game.addBoard(board);

        return game;
    }

    public class FudgedBingoGame extends BingoGame {
        private BingoBoard lastWinner;
        private List<BingoBoard> winners;

        public FudgedBingoGame(List<BigDecimal> draw) {
            super(draw);
        }

        @Override
        protected void playRound() {
            winners = new ArrayList<>();

            super.playRound();

            if (isOver()) {
                if (boards.size() > 1) {
                    if (winners.size() > 0) {
                        System.out.println("Last number" + lastWinner.lastNumber);
                        System.out.println("===== Remove "+winners.size()+" winners (remaining: "+boards.size()+" =====");
                        boards.removeAll(winners);
                        System.out.println("=========================");
                        super.setWinner(null);
                    }
                }
            }
        }

        @Override
        void setWinner(BingoBoard b) {
            winners.add(b);
            lastWinner = b;
            super.setWinner(b);
        }

        public BingoBoard getLastWinner() {
            return lastWinner;
        }
    }

    public class BingoGame {
        private int cursor;
        private final List<BigDecimal> draw;
        protected final List<BingoBoard> boards;
        private BingoBoard winner;

        public BingoGame(List<BigDecimal> draw) {
            this.cursor = 0;
            this.draw = draw;
            this.boards = new ArrayList<>();
        }

        public void addBoard(BingoBoard board) {
            boards.add(board);
        }

        public boolean isOver() {
            return winner != null || cursor >= draw.size() ;
        }

        public void play() {
            while (!isOver()) {
                playRound();
            }
        }

        protected void playRound() {
            System.out.println();
            System.out.println();
            BigDecimal drawn = draw.get(cursor);
            System.out.println("======= Play Round - Drawn "+drawn+ " =======");

            for (BingoBoard b : boards) {
                b.print();
                b.numberFound(drawn);
                if (b.isWinner()) {
                    setWinner(b);
                }
                System.out.println();
            }

            System.out.println();
            cursor++;
        }

        void setWinner(BingoBoard b) {
            winner = b;
        }

        public BingoBoard getWinner() {
            return winner;
        }
    }

    private class BingoBoard {
        private final int BOARD_SIDE_LENGTH = 5;
        private final BigDecimal[][] numbers = new BigDecimal[BOARD_SIDE_LENGTH][];
        private final boolean[][] found = new boolean[BOARD_SIDE_LENGTH][];
        private int creationCursor = 0;
        private BigDecimal lastNumber;

        public void addLine(String line) {
            BigDecimal[] lineNumbers = getBigDecimalStream(line).toArray(BigDecimal[]::new);
            numbers[creationCursor] = lineNumbers;
            found[creationCursor] = new boolean[lineNumbers.length];
            creationCursor++;
        }

        boolean isWinner() {
            for (int i = 0; i < BOARD_SIDE_LENGTH; i++) {
                boolean winner = true;

                for (int j = 0; j < BOARD_SIDE_LENGTH; j++) {
                    winner &= found[i][j];
                }

                if (winner) {
                    return true;
                }
            }

            for (int i = 0; i < BOARD_SIDE_LENGTH; i++) {
                boolean winner = true;

                for (int j = 0; j < BOARD_SIDE_LENGTH; j++) {
                    winner &= found[j][i];
                }

                if (winner) {
                    return true;
                }
            }

            return false;
        }

        public void numberFound(BigDecimal number) {
            outerloop:for (int i = 0; i < BOARD_SIDE_LENGTH; i++) {
                for (int j = 0; j < BOARD_SIDE_LENGTH; j++) {
                    if (Objects.equals(numbers[i][j], number)) {
                        System.out.println("Found "+number);
                        found[i][j] = true;
                        lastNumber = number;
                        print();
                        break outerloop;
                    }
                }
            }
        }

        public BigDecimal getScore() {
            BigDecimal score = BigDecimal.ZERO;

            for (int i = 0; i < BOARD_SIDE_LENGTH; i++) {
                for (int j = 0; j < BOARD_SIDE_LENGTH; j++) {
                    if (!found[i][j]) {
                        score = score.add(numbers[i][j]);
                    }
                }
            }

            System.out.println("Score "+score+ " - last: "+lastNumber+ " - board:");
            print();
            return score.multiply(lastNumber);
        }

        public void print() {
            System.out.println("=== Board ===");
            for (int i = 0; i < BOARD_SIDE_LENGTH; i++) {
                for (int j = 0; j < BOARD_SIDE_LENGTH; j++) {
                    System.out.print(numbers[i][j]+"|"+found[i][j]+" \t");
                }
                System.out.println();
            }
            System.out.println();
        }
    }

    private static Stream<BigDecimal> getBigDecimalStream(String s, String separator) {
        return Arrays.stream(s.split(separator)).map(String::trim).map(BigDecimal::new);
    }

    private static Stream<BigDecimal> getBigDecimalStream(String s) {
        return getBigDecimalStream(s.trim(), "[^\\d]+");
    }
}
