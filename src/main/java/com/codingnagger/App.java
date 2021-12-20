package com.codingnagger;

import com.codingnagger.days.Day;
import com.codingnagger.days.Day16;
import com.codingnagger.days.Day20;
import com.codingnagger.utils.InputLoader;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Advent of Code 2021");

        List<String> input = InputLoader.Load("day20.txt");

        Day day = new Day20();

        System.out.println("Part 1:");
        System.out.println(day.partOne(input));

        System.out.println("Part 2:");
        System.out.println(day.partTwo(input));
    }
}
