package com.codingnagger.days;

import com.codingnagger.bits.Bits;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.codingnagger.bits.Bits.parse;

public class Day16 implements Day {
    @Override
    public String partOne(List<String> input) {
        Bits.Packet rootPacket = parse(input.get(0));
        return String.valueOf(rootPacket.getVersionSum());
    }

    @Override
    public String partTwo(List<String> input) {
        Bits.Packet rootPacket = parse(input.get(0));
        return String.valueOf(rootPacket.computeValue());
    }
}
