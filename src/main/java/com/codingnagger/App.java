package com.codingnagger;

import com.codingnagger.days.Day;
import com.codingnagger.days.Day1;
import com.codingnagger.days.Day2;
import com.codingnagger.days.Day3;
import com.codingnagger.utils.InputLoader;

import java.io.IOException;
import java.util.List;

/**
 * Hello world!
 */
public class App {
    public static void main(String[] args) throws IOException {
        System.out.println("Advent of Code 2021");

        List<String> input = InputLoader.Load("day3.txt");

        Day day = new Day3();

        System.out.println("Part 1:");
        System.out.println(day.partOne(input));

        System.out.println("Part 2:");
        System.out.println(day.partTwo(input));
    }
}