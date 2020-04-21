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

    static void findSolutions(String problemName, boolean[][] g, final boolean printSolutions) {
        final var solution = toBits(g);
        System.out.printf("===> analyzing problem '%s':%n%s%n%n",
            problemName,
            dump(solution, g[0].length)
        );
        var start = System.nanoTime();

        var counter = 0;

        var height = g.length + 1;
        var width = g[0].length + 1;

        long max = 1L << height * width;
        int mask = 0;
        for (int i = 0; i < width; i++) {
            mask |= 1 << i;
        }

        long step = max / 1000000000;
        if (step == 0) {
            step = 10000;
        }

        for (long i = 0; i < max; i++) {
            long[] proposal = new long[height];
            for (int j = 0; j < height; j++) {
                proposal[j] = (i >> j * width) & mask;
            }
            var proposalNextStep = nextStep(proposal, width);
            if (areEquals(proposalNextStep, solution)) {
                counter++;
                if (printSolutions) {
                    System.out.printf(
                        "+ solution %d:%n%s%n",
                        counter,
                        dump(proposal, width)
                    );
                }
            }
            if (i != 0 && i % step == 0) {
                var stepEnd = System.nanoTime();
                var elapsedInNanos = start - stepEnd;
                var remainingNanos = max / i * elapsedInNanos;
                System.out.printf(
                    "---> analyzed (%d/%d) proposals so far... (%d solutions found) remaining estimate time%dms%n",
                    i, max, counter, remainingNanos / 1_000_000
                );
            }
        }

        var end = System.nanoTime();

        System.out.printf(
            "== found %d solutions for:%n%s%nin %dms%n",
            counter,
            dump(solution, g[0].length),
            (end - start) / 1_000_000
        );
    }

    static boolean areEquals(long[] proposal, long[] solution) {
        if (proposal.length != solution.length) {
            return false;
        }
        for (int i = 0; i < proposal.length; i++) {
            if (proposal[i] != solution[i]) {
                return false;
            }
        }
        return true;
    }


    public static void main(String[] args) {
//        /*
//            .O..
//            ..O.
//            ...O
//            O...
//         */
//        var p = new boolean[][]{
//            {false, true, false, false},
//            {false, false, true, false},
//            {false, false, false, true},
//            {true, false, false, false}
//        };
//
////        var g1 = new boolean[][]{
////            {true, true, false, true, false, true, false, true, true, false},
////            {true, true, false, false, false, false, true, true, true, false},
////            {true, true, false, false, false, false, false, false, false, true},
////            {false, true, false, false, false, false, true, true, false, false}
////        };
////        var g2 = new boolean[][]{
////            {true, false, true},
////            {false, true, false},
////            {true, false, true}
////        };
//        var g3 = new boolean[][]{
//            {true, false, true, false, false, true, true, true},
//            {true, false, true, false, false, false, true, false},
//            {true, true, true, false, false, false, true, false},
//            {true, false, true, false, false, false, true, false},
//            {true, false, true, false, false, true, true, true}
//        };
////        print("g1", toBits(g1), g1[0].length);
////        print("g2", toBits(g2), g2[0].length);
////        print("g3", toBits(g3), g3[0].length);
//
//        print("p", toBits(p), p[0].length);
//        print("nextStep(p)", nextStep(toBits(p), p[0].length), p[0].length - 1);
//
//        print("g3", toBits(g3), g3[0].length);
//        print("nextStep(p)", nextStep(toBits(g3), g3[0].length), g3[0].length - 1);
//
//        var test = new long[]{
//            0b00,
//            0b01,
//            0b01
//        };
//        print("test", test, 2);
//        print("n(test)", nextStep(test, 2), 1);


        var case2 = new boolean[][]{
            {true, false, true},
            {false, true, false},
            {true, false, true},
        };
        findSolutions("second case", case2, true);

        var caseSubset = new boolean[][]{
            {false, false, true},
            {true, false, false}
        };
        findSolutions("first case (subset)", caseSubset, false);

        var caseSmallestSubset = new boolean[][]{
            {false, false},
            {true, false}
        };
        findSolutions("first case (small subset)", caseSmallestSubset, false);

//        var case13 = new boolean[][]{
//            {true, true, false},
//            {true, true, false},
//            {true, true, false},
//            {false, true, false}
//        };
//        findSolutions("first case, first 3 cols", case13, false);
//
//        var case14 = new boolean[][]{
//            {true, true, false, true},
//            {true, true, false, false},
//            {true, true, false, false},
//            {false, true, false, false}
//        };
//        findSolutions("first case, first 4 cols", case14, false);
//
////        var case15 = new boolean[][]{
////            {true, true, false, true, false},
////            {true, true, false, false, false},
////            {true, true, false, false, false},
////            {false, true, false, false, false}
////        };
////        findSolutions("first case, first 5 cols", case15, false);
//
//        var caseL4 = new boolean[][]{
//            {false, true, true, false},
//            {true, true, true, false},
//            {false, false, false, true},
//            {true, true, false, false}
//        };
//        findSolutions("first case, last 4 cols", caseL4, false);
//
//        var caseL3 = new boolean[][]{
//            {true, true, false},
//            {true, true, false},
//            {false, false, true},
//            {true, false, false}
//        };
//        findSolutions("first case, last 3 cols", caseL3, false);
//
//        var caseL2 = new boolean[][]{
//            {true, false},
//            {true, false},
//            {false, true},
//            {false, false}
//        };
//        findSolutions("first case, last 2 cols", caseL2, false);
//
//        var caseL1 = new boolean[][]{
//            {false},
//            {false},
//            {true},
//            {false}
//        };
//        findSolutions("first case, last col", caseL1, false);
//
//        var case1BeforeLast = new boolean[][]{
//            {true},
//            {true},
//            {false},
//            {false}
//        };
//        findSolutions("first case, 1 before last col", case1BeforeLast, false);
//
//        var caseL2Top2 = new boolean[][]{
//            {true, false},
//            {true, false},
//        };
//        findSolutions("first case, last 2 cols top 2 rows", caseL2Top2, false);
//
//        var caseL2Bottom2 = new boolean[][]{
//            {false, true},
//            {false, false}
//        };
//        findSolutions("first case, last 2 cols bottom 2 rows", caseL2Bottom2, false);
//
//        var caseL2T3 = new boolean[][]{
//            {true, false},
//            {true, false},
//            {false, true},
//        };
//        findSolutions("first case, last 2 cols top 3 rows", caseL2T3, false);
//
//        var caseL2B3 = new boolean[][]{
//            {true, false},
//            {false, true},
//            {false, false}
//        };
//        findSolutions("first case, last 2 cols bottom 3 rows", caseL2B3, false);
//
//        var caseL2M23 = new boolean[][]{
//            {true, false},
//            {false, true},
//        };
//        findSolutions("first case, last 2 cols middle 2 rows", caseL2M23, false);

//        var case3 = new boolean[][]{
//            {true, false, true, false, false, true, true, true},
//            {true, false, true, false, false, false, true, false},
//            {true, true, true, false, false, false, true, false},
//            {true, false, true, false, false, false, true, false},
//            {true, false, true, false, false, true, true, true}
//        };
//        findSolutions("third case", case3, false);
    }
}
