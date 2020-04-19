package org.teutinc.foobar.fifth;

import java.util.Arrays;
import java.util.stream.Collectors;

import static java.lang.String.format;

/*
    Some experimentations to solve the fifth challenge.
 */
public class Experimentations {
    static long[] toBits(boolean[][] g) {
        final long[] res = new long[g.length];
        for (int i = 0; i < g.length; i++) {
            res[i] = rowToBits(g[i]);
        }
        return res;
    }

    static long rowToBits(boolean[] row) {
        long res = 0;
        for (int i = 0; i < row.length; i++) {
            if (row[i]) {
                res |= 1L << row.length - 1 - i;
            }
        }
        return res;
    }

    static String formatBinary(long number, int width) {
        return format("%" + width + "s", Long.toBinaryString(number))
            .replace(" ", "0");
    }

    static String dump(long[] matrix, int width) {
        return Arrays.stream(matrix)
                     .mapToObj(n -> formatBinary(n, width))
                     .collect(Collectors.joining("\n"));
    }

    static void print(String label, long[] matrix, int width) {
        System.out.println("==== " + label + " (start) ====");
        System.out.println(dump(matrix, width));
        System.out.println("==== " + label + " (end) ====\n");
    }

    static long[] nextStep(long[] step, int width) {
        long mask1 = 0b0011;
        long mask2 = 0b1100;
        long[] res = new long[step.length - 1];
        for (int i = 0; i < step.length - 1; i++) {
            long row = 0;
            for (int j = width; j >= 2; j--) {
                long firstRow = reposition(step[i], j, 4, mask2);
                long secondRow = reposition(step[i + 1], j, 2, mask1);

                int square = (int)(firstRow | secondRow);
                if (isPowerOf2(square)) {
                    row |= 1 << (j - 2);
//                    System.out.println("on: " + formatBinary(row, width - 1));
                }

//                System.out.printf("(%d, %d)%n", i, j);
//                System.out.printf("first: %s%n", formatBinary(firstRow, width));
//                System.out.printf("second: %s%n", formatBinary(secondRow, width));
//                System.out.println(formatBinary(square, width));
//                System.out.println();
            }
            res[i] = row;
        }

        return res; // fixme
    }

    static boolean isPowerOf2(int n) {
        return n != 0 && ((n & n - 1) == 0);
    }

    static long reposition(long original, int index, int destIndex, long mask) {
        long res = original;
        if (index > destIndex) {
            res >>= index - destIndex;
        } else if (destIndex > index) {
            res <<= destIndex - index;
        }
        //        System.out.printf(
//            "reposition %s (%d -> %d) => %s%n",
//            formatBinary(original, 8), index, destIndex, formatBinary(val, 4)
//        );
        return res & mask;
    }
    public static void main(String[] args) {
        /*
            .O..
            ..O.
            ...O
            O...
         */
        var p = new boolean[][]{
            {false, true, false, false},
            {false, false, true, false},
            {false, false, false, true},
            {true, false, false, false}
        };

//        var g1 = new boolean[][]{
//            {true, true, false, true, false, true, false, true, true, false},
//            {true, true, false, false, false, false, true, true, true, false},
//            {true, true, false, false, false, false, false, false, false, true},
//            {false, true, false, false, false, false, true, true, false, false}
//        };
//        var g2 = new boolean[][]{
//            {true, false, true},
//            {false, true, false},
//            {true, false, true}
//        };
        var g3 = new boolean[][]{
            {true, false, true, false, false, true, true, true},
            {true, false, true, false, false, false, true, false},
            {true, true, true, false, false, false, true, false},
            {true, false, true, false, false, false, true, false},
            {true, false, true, false, false, true, true, true}
        };
//        print("g1", toBits(g1), g1[0].length);
//        print("g2", toBits(g2), g2[0].length);
//        print("g3", toBits(g3), g3[0].length);

        print("p", toBits(p), p[0].length);
        print("nextStep(p)", nextStep(toBits(p), p[0].length), p[0].length - 1);

        print("g3", toBits(g3), g3[0].length);
        print("nextStep(p)", nextStep(toBits(g3), g3[0].length), g3[0].length - 1);

        var test = new long[]{
            0b00,
            0b01,
            0b01
        };
        print("test", test, 2);
        print("n(test)", nextStep(test, 2), 1);
    }
}
