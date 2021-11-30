package com.codingnagger;

import java.io.IOException;

import com.codingnagger.utils.InputLoader;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args ) throws IOException
    {
        System.out.println( "Advent of Code 2021" );

        Iterable<String> input = new InputLoader().Load("day1.txt");

        // Day day = new Day1();

        System.out.println( "Part 1:" );
        // System.out.println(day1.partOne(input));

        System.out.println( "Part 2:" );
        // System.out.println(day1.partTwo(input));
    }
}
