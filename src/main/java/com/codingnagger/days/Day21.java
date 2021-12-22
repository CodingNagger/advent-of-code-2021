package com.codingnagger.days;

import java.math.BigInteger;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Day21 implements Day {

    public static final int PART_2VICTORY = 21;

    @Override
    public String partOne(List<String> input) {
        int[] diceRoll = new int[] {1,2,3};

        int p1Score = 0, p2Score = 0;
        int p1Position = Integer.parseInt(input.get(0).split("starting position: ")[1]);
        int p2Position = Integer.parseInt(input.get(1).split("starting position: ")[1]);

        boolean p1Turn = true;
        int turns = 0;

        while (p1Score < 1000 && p2Score < 1000) {
            if (p1Turn) {
                p1Position = endPosition(p1Position, diceRoll);
                p1Score += p1Position;
            } else {
                p2Position = endPosition(p2Position, diceRoll);
                p2Score += p2Position;
            }

            diceRoll[0] = roll(diceRoll[0]);
            diceRoll[1] = roll(diceRoll[1]);
            diceRoll[2] = roll(diceRoll[2]);

            turns += 3;
            p1Turn = !p1Turn;
        }
        return String.valueOf(turns * Math.min(p1Score, p2Score));
    }

    private int roll(int value) {
        if (value + 3 > 100) {
            return value + 3 - 100;
        }

        return value + 3;
    }

    private int endPosition(int position, int[] diceRoll) {
        return ((position-1 + diceRoll[0] + diceRoll[1] + diceRoll[2]) % 10) + 1;
    }

    private int endPosition(int position, int diceRoll) {
        return ((position-1 + diceRoll) % 10) + 1;
    }

    private static final Map<String, UniverseResult> UNIVERSE_RESULTS = new HashMap<>();

    @Override
    public String partTwo(List<String> input) {
        int p1Position = Integer.parseInt(input.get(0).split("starting position: ")[1]);
        int p2Position = Integer.parseInt(input.get(1).split("starting position: ")[1]);

        UniverseResult result = playGame(0, 0, p1Position, p2Position, 0, 0);

        return String.valueOf(Math.max(result.getP1Wins().longValue(), result.getP2Wins().longValue()));
    }

    public UniverseResult playGame(int p1Score, int p2Score, int p1Position, int p2Position, int turn, int throwSum) {
        boolean isP1Turn = turn < 3;
        boolean lastP1Throw = turn == 2;
        boolean lastP2Throw = turn == 5;

        String cacheKey =  String.format("%d-%d-%d-%d-%d-%d", p1Position, p1Score, p2Position, p2Score, turn, throwSum);

        UniverseResult res;

        if (p1Score >= PART_2VICTORY) {
            res = new UniverseResult(BigInteger.ONE, BigInteger.ZERO);
        } else if (p2Score >= PART_2VICTORY) {
            res = new UniverseResult(BigInteger.ZERO, BigInteger.ONE);
        } else {
            if (UNIVERSE_RESULTS.containsKey(cacheKey)) {
                return UNIVERSE_RESULTS.get(cacheKey);
            }

            int nextTurn = (turn + 1) % 6;

            BigInteger p1Wins = BigInteger.ZERO;
            BigInteger p2Wins = BigInteger.ZERO;

            for (int roll = 1; roll <= 3; roll++) {
                UniverseResult result = playGame(
                        isP1Turn ? p1Score + (lastP1Throw ? endPosition(p1Position, roll)  : 0) : p1Score,
                        !isP1Turn ? p2Score + (lastP2Throw ? endPosition(p2Position, roll)  : 0) : p2Score,
                        isP1Turn ? endPosition(p1Position, roll) : p1Position,
                        !isP1Turn ? endPosition(p2Position, roll) : p2Position,
                        nextTurn,
                        turn % 3 == 0 ? 0 : throwSum + roll
                );

                p1Wins = p1Wins.add(result.getP1Wins());
                p2Wins = p2Wins.add(result.getP2Wins());
            }

            res = new UniverseResult(p1Wins, p2Wins);
        }

        UNIVERSE_RESULTS.put(cacheKey, res);
        return res;
    }

    class UniverseResult {
        private final BigInteger p1Wins;
        private final BigInteger p2Wins;

        public UniverseResult(BigInteger p1Wins, BigInteger p2Wins) {
            this.p1Wins = p1Wins;
            this.p2Wins = p2Wins;
        }

        public BigInteger getP1Wins() {
            return p1Wins;
        }

        public BigInteger getP2Wins() {
            return p2Wins;
        }
    }
}
