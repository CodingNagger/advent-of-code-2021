package com.codingnagger.days;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Day16 implements Day {
    private static final Map<String, String> HEXA_MAP = Map.ofEntries(
            Map.entry("0", "0000"), Map.entry("1", "0001"), Map.entry("2", "0010"),
            Map.entry("3", "0011"), Map.entry("4", "0100"), Map.entry("5", "0101"),
            Map.entry("6", "0110"), Map.entry("7", "0111"), Map.entry("8", "1000"),
            Map.entry("9", "1001"), Map.entry("A", "1010"), Map.entry("B", "1011"),
            Map.entry("C", "1100"), Map.entry("D", "1101"), Map.entry("E", "1110"),
            Map.entry("F", "1111"));

    private static final String L15_BITS = "0";
    private static final String L11_BITS = "1";

    private static final int T_LITERAL = 4;

    private static final String G_LITERAL_LAST_GROUP = "0";

    @Override
    public String partOne(List<String> input) {
        Packet rootPacket = parse(input.get(0));
        return String.valueOf(rootPacket.getVersionSum());
    }

    @Override
    public String partTwo(List<String> input) {
        Packet rootPacket = parse(input.get(0));
        return String.valueOf(rootPacket.computeValue());
    }

    public static Packet parse(String input) {
        String binaryInput = convertHexaToBinaryString(input);
        return getPackets(binaryInput);
    }

    private static Packet getPackets(String binaryInput) {
        LinkedList<Character> bitsQueue = Arrays.stream(binaryInput.split("")).filter(s -> !s.isBlank()).map(s -> s.charAt(0)).collect(Collectors.toCollection(LinkedList::new));
        return readPacket(bitsQueue).getPacket();
    }

    private static PacketReadResult readPacket(LinkedList<Character> bitsQueue) {
        int packetVersion = Integer.parseInt(readBits(bitsQueue, 3), 2);
        int packetTypeId = Integer.parseInt(readBits(bitsQueue, 3), 2);
        int readBits = 6;

        if (packetTypeId == T_LITERAL) {
            StringBuilder binaryContentsBuilder = new StringBuilder();

            while(!G_LITERAL_LAST_GROUP.equals(readBits(bitsQueue, 1))) {
                binaryContentsBuilder.append(readBits(bitsQueue, 4));
                readBits += 5;
            }

            binaryContentsBuilder.append(readBits(bitsQueue, 4));
            readBits += 5;

            return new PacketReadResult(new LiteralPacket(packetVersion, packetTypeId, binaryContentsBuilder.toString()), readBits);
        } else {
            String lengthTypeId = readBits(bitsQueue, 1);
            List<Packet> children = new ArrayList<>();

            if (L15_BITS.equals(lengthTypeId)) {
                int bitsToRead = Integer.parseInt(readBits(bitsQueue, 15), 2);

                LinkedList<Character> childrenBitsToRead = new LinkedList<>();

                for (int i = 0; i < bitsToRead; i++) {
                    childrenBitsToRead.add(bitsQueue.poll());
                }

                while (!childrenBitsToRead.isEmpty()) {
                    PacketReadResult res = readPacket(childrenBitsToRead);

                    bitsToRead -= res.getReadBits();
                    readBits += res.getReadBits();
                    children.add(res.getPacket());
                }

                System.out.printf("Bits left to read: %d%n", bitsToRead);
            } else if (L11_BITS.equals(lengthTypeId)) {
                int childrenToRead = Integer.parseInt(readBits(bitsQueue, 11), 2);

                while (childrenToRead > 0) {
                    PacketReadResult res = readPacket(bitsQueue);

                    readBits += res.getReadBits();
                    children.add(res.getPacket());
                    childrenToRead--;
                }
            }

            return new PacketReadResult(new OperationPacket(packetVersion, packetTypeId, children), readBits);
        }
    }

    private static String readBits(LinkedList<Character> bitsQueue, int count) {
        StringBuilder sb = new StringBuilder(count);

        for (int i = 0; i < count; i++) {
            sb.append(bitsQueue.poll());
        }

        return sb.toString();
    }

    public static String convertHexaToBinaryString(String hexa) {
        return Arrays.stream(hexa.split("")).map(HEXA_MAP::get).collect(Collectors.joining(""));
    }

    static class PacketReadResult {
        private final Packet packet;
        private final int readBits;

        public PacketReadResult(Packet packet, int readBits) {
            this.packet = packet;
            this.readBits = readBits;
        }

        public Packet getPacket() {
            return packet;
        }

        public int getReadBits() {
            return readBits;
        }
    }

    static abstract class Packet {
        private final int packetVersion;
        private final int packetTypeId;

        public Packet(int packetVersion, int packetTypeId) {
            this.packetVersion = packetVersion;
            this.packetTypeId = packetTypeId;
        }

        public int getPacketVersion() {
            return packetVersion;
        }

        public int getPacketTypeId() {
            return packetTypeId;
        }

        public int getVersionSum() {
            return packetVersion;
        }

        public abstract BigDecimal computeValue();
    }

    static class LiteralPacket extends Packet {
        private final String binaryContents;

        public LiteralPacket(int packetVersion, int packetTypeId, String binaryContents) {
            super(packetVersion, packetTypeId);
            this.binaryContents = binaryContents;
        }

        public String getBinaryContents() {
            return binaryContents;
        }

        public BigDecimal computeValue() {
            return new BigDecimal(new BigInteger(binaryContents, 2).longValue());
        }
    }

    static class OperationPacket extends Packet {
        private final List<Packet> subPackets;

        public OperationPacket(int packetVersion, int packetTypeId, List<Packet> subPackets) {
            super(packetVersion, packetTypeId);
            this.subPackets = subPackets;
        }

        @Override
        public int getVersionSum() {
            return getPacketVersion() + subPackets.stream().mapToInt(Packet::getVersionSum).sum();
        }

        public BigDecimal computeValue() {
            switch (getPacketTypeId()) {
                case 0: return subPackets.stream().map(Packet::computeValue).reduce(BigDecimal.ZERO, BigDecimal::add);
                case 1: return subPackets.stream().map(Packet::computeValue).reduce(BigDecimal.ONE, BigDecimal::multiply);
                case 2: return subPackets.stream().map(Packet::computeValue).reduce(BigDecimal.valueOf(Long.MAX_VALUE), BigDecimal::min);
                case 3: return subPackets.stream().map(Packet::computeValue).reduce(BigDecimal.valueOf(Long.MIN_VALUE), BigDecimal::max);

                case 5: return subPackets.get(0).computeValue().compareTo(subPackets.get(1).computeValue()) > 0 ? BigDecimal.ONE : BigDecimal.ZERO;
                case 6: return subPackets.get(0).computeValue().compareTo(subPackets.get(1).computeValue()) < 0 ? BigDecimal.ONE : BigDecimal.ZERO;
                case 7: return subPackets.get(0).computeValue().equals(subPackets.get(1).computeValue() ) ? BigDecimal.ONE : BigDecimal.ZERO;
            }

            return null;
        }
    }
}
