package com.codingnagger;

import com.codingnagger.days.Day;
import com.codingnagger.days.Day10;
import com.codingnagger.days.Day11;
import com.codingnagger.days.Day12;
import com.codingnagger.days.Day13;
import com.codingnagger.days.Day14;
import com.codingnagger.days.Day15;
import com.codingnagger.days.Day17;
import com.codingnagger.days.Day9;
import com.codingnagger.utils.InputLoader;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Advent of Code 2021");

        List<String> input = InputLoader.Load("day17.txt");

        Day day = new Day17();

        System.out.println("Part 1:");
        System.out.println(day.partOne(input));

        System.out.println("Part 2:");
        System.out.println(day.partTwo(input));
    }
}
