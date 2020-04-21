package org.teutinc.foobar.fifth;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

/*
Expanding Nebula
================
You've escaped Commander Lambda's exploding space station along with numerous escape pods full of bunnies. But - oh no! - one of the escape pods has flown into a nearby nebula,
causing you to lose track of it. You start monitoring the nebula, but unfortunately, just a moment too late to find where the pod went. However, you do find that the gas of the steadily
expanding nebula follows a simple pattern, meaning that you should be able to determine the previous state of the gas and narrow down where you might find the pod.
From the scans of the nebula, you have found that it is very flat and distributed in distinct patches, so you can model it as a 2D grid. You find that the current existence of gas in a cell
of the grid is determined exactly by its 4 nearby cells, specifically, (1) that cell, (2) the cell below it, (3) the cell to the right of it, and (4) the cell below and to the right of it.
If, in the current state, exactly 1 of those 4 cells in the 2x2 block has gas, then it will also have gas in the next state. Otherwise, the cell will be empty in the next state.
For example, let's say the previous state of the grid (p) was:
.O..
..O.
...O
O...
To see how this grid will change to become the current grid (c) over the next time step, consider the 2x2 blocks of cells around each cell.  Of the 2x2 block of [p[0][0], p[0][1], p[1][0],
p[1][1]], only p[0][1] has gas in it, which means this 2x2 block would become cell c[0][0] with gas in the next time step:
.O -> O
..
Likewise, in the next 2x2 block to the right consisting of [p[0][1], p[0][2], p[1][1], p[1][2]], two of the containing cells have gas, so in the next state of the grid, c[0][1] will NOT have
gas:
O. -> .
.O
Following this pattern to its conclusion, from the previous state p, the current state of the grid c will be:
O.O
.O.
O.O
Note that the resulting output will have 1 fewer row and column, since the bottom and rightmost cells do not have a cell below and to the right of them, respectively.
Write a function solution(g) where g is an array of array of bools saying whether there is gas in each cell (the current scan of the nebula), and return an int with the number of possible
previous states that could have resulted in that grid after 1 time step.  For instance, if the function were given the current state c above, it would deduce that the possible previous states
were p (given above) as well as its horizontal and vertical reflections, and would return 4. The width of the grid will be between 3 and 50 inclusive, and the height of the grid will be
between 3 and 9 inclusive.  The answer will always be less than one billion (10^9).
Languages
=========
To provide a Java solution, edit Solution.java
To provide a Python solution, edit solution.py
Test cases
==========
Your code should pass the following test cases.
Note that it may also be run against hidden test cases not shown here.
-- Java cases --
Input:
Solution.solution({{true, true, false, true, false, true, false, true, true, false}, {true, true, false, false, false, false, true, true, true, false}, {true, true, false, false, false,
false, false, false, false, true}, {false, true, false, false, false, false, true, true, false, false}})
Output:
    11567
Input:
Solution.solution({{true, false, true}, {false, true, false}, {true, false, true}})
Output:
    4
Input:
Solution.solution({{true, false, true, false, false, true, true, true}, {true, false, true, false, false, false, true, false}, {true, true, true, false, false, false, true, false}, {true,
false, true, false, false, false, true, false}, {true, false, true, false, false, true, true, true}}
Output:
    254
-- Python cases --
Input:
solution.solution([[True, True, False, True, False, True, False, True, True, False], [True, True, False, False, False, False, True, True, True, False], [True, True, False, False, False,
False, False, False, False, True], [False, True, False, False, False, False, True, True, False, False]])
Output:
    11567
Input:
solution.solution([[True, False, True], [False, True, False], [True, False, True]])
Output:
    4
Input:
solution.solution([[True, False, True, False, False, True, True, True], [True, False, True, False, False, False, True, False], [True, True, True, False, False, False, True, False], [True,
False, True, False, False, False, True, False], [True, False, True, False, False, True, True, True]])
Output:
    254
Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will
be removed from your home folder.
 */
public class Solution {
    static final boolean DEBUG = false;

    static boolean isPowerOf2(int n) {
        return n != 0 && ((n & n - 1) == 0);
    }

    static class Piece {
        /*
            if we have a piece:
            12
            34
         */

        // store the drawing like this 00001234
        final int drawing;
        // top part: 12
        final int top;
        // right part: 24
        final int right;
        // 34
        final int bottom;
        // 13
        final int left;
        // producing
        final boolean producingOne;
        // vertical symmetry
        final boolean symmetric;

        Piece(int drawing) {
            this.drawing = drawing;
            this.top = drawing >> 2;
            this.right = ((drawing >> 1) & 0b10) | (drawing & 0b1);
            this.bottom = drawing & 0b11;
            this.left = ((drawing >> 2) & 0b10) | ((drawing >> 1) & 0b1);
            this.producingOne = isPowerOf2(drawing);
            this.symmetric = this.left == this.right;
        }

        @Override
        public String toString() {
            return Integer.toString(drawing);
        }
    }

    static final Piece[] ALL_PIECES = new Piece[]{
        new Piece(0), // 0000 -> 0
        new Piece(1), // 0001 -> 1
        new Piece(2), // 0010 -> 1
        new Piece(3), // 0011 -> 0
        new Piece(4), // 0100 -> 1
        new Piece(5), // 0101 -> 0
        new Piece(6), // 0110 -> 0
        new Piece(7), // 0111 -> 0
        new Piece(8), // 1000 -> 1
        new Piece(9), // 1001 -> 0
        new Piece(10), // 1010 -> 0
        new Piece(11), // 1011 -> 0
        new Piece(12), // 1100 -> 0
        new Piece(13), // 1101 -> 0
        new Piece(14), // 1110 -> 0
        new Piece(15), // 1111 -> 0
    };

    static final Set<Integer> UNIQUE_DRAWINGS =
        Arrays.stream(new int[]{
            // 00
            // 00 -> 0
            0,
            // 00
            // 01 -> 1
            1, // merged with 2
            // 00
            // 11 -> 0
            3,
            // 01
            // 00 -> 1
            4, // merged with 8
            // 01
            // 01 -> 0
            5, // merged with 10
            // 01
            // 10 -> 0
            6, // merged with 9
            // 01
            // 11 -> 0
            7, // merged with 11
            // 11
            // 00 -> 0
            12,
            // 11
            // 01 -> 0
            13, // merged with 14
            // 11
            // 11 -> 0
            15,
        }).boxed().collect(Collectors.toSet());

    static final Piece[] PRODUCES_ONE =
        IntStream.range(0, 16)
                 .filter(index -> ALL_PIECES[index].producingOne)
                 .mapToObj(index -> ALL_PIECES[index])
                 .toArray(Piece[]::new);

    static final Piece[] PRODUCES_ZERO =
        IntStream.range(0, 16)
                 .filter(index -> !ALL_PIECES[index].producingOne)
                 .mapToObj(index -> ALL_PIECES[index])
                 .toArray(Piece[]::new);

    interface Constraints {
        long getNumberOfPermutationsFor(int leftColumn);
    }

    static class NoConstraints implements Constraints {
        @Override
        public long getNumberOfPermutationsFor(int leftColumn) {
            return 1;
        }
    }

    static class PreviousColConstraints implements Constraints {
        final Map<Integer, Long> previousPermutations;

        PreviousColConstraints(Map<Integer, Long> previousPermutations) {
            this.previousPermutations = previousPermutations;
        }

        @Override
        public long getNumberOfPermutationsFor(int leftColumn) {
            return previousPermutations.getOrDefault(leftColumn, 0L);
        }
    }

    interface Vertex {
        boolean matchBottom(Piece piece);
        void addChild(Vertex child);
        void removeChild(Vertex child);
        void addParent(Vertex parent);
        void removeDeadBranch();
        List<Vertex> children();
        /*@Nullable*/ int[] columns();
        List<int[]> appendToCombinations(List<int[]> childCombinations, int depth, int numberOfRows);
        boolean hasNotSymmetricDrawing();
    }

    static abstract class BaseVertex implements Vertex {
        final List<Vertex> children = new ArrayList<>();
        final List<Vertex> parents = new ArrayList<>();

        @Override
        public void addChild(Vertex child) {
            children.add(child);
            child.addParent(this);
        }

        @Override
        public void removeChild(Vertex child) {
            children.remove(child);
            if (children.isEmpty()) {
                for (Vertex parent : parents) {
                    parent.removeChild(this);
                }
            }
        }

        @Override
        public void addParent(Vertex parent) {
            parents.add(parent);
        }

        @Override
        public void removeDeadBranch() {
            for (Vertex parent : parents) {
                parent.removeChild(this);
            }
        }

        @Override
        public List<Vertex> children() {
            return children;
        }
    }

    static class Root extends BaseVertex implements Vertex {
        @Override
        public boolean matchBottom(Piece piece) {
            return true; // we match any pieces
        }

        @Override
        public /*@Nullable*/ int[] columns() {
            return null;
        }

        @Override
        public List<int[]> appendToCombinations(List<int[]> childCombinations, int depth, int numberOfRows) {
            return childCombinations;
        }

        @Override
        public boolean hasNotSymmetricDrawing() {
            return true;
        }
    }

    static class PieceVertex extends BaseVertex implements Vertex {
        final Piece piece;

        PieceVertex(Piece piece) {
            this.piece = piece;
        }

        @Override
        public boolean matchBottom(Piece otherPiece) {
            return piece.bottom == otherPiece.top;
        }

        @Override
        public int[] columns() {
            // we need to swap the bits, because we are writing the solution like this:
            // 00
            // 10
            // 01
            // 11
            // two columns are ints, and we are reading them from top to bottom:
            // left: 1010
            // right: 1100
            return new int[]{
                piece.left,
                piece.right
            };
        }

        @Override
        public List<int[]> appendToCombinations(List<int[]> childCombinations, int depth, int numberOfRows) {
            /*
                if we have a combination like this, and depth is 1 and number of rows is 4:
                [
                    101,
                    010
                ]
                and we want to append: 11
                                       10

                we will just take the first row, the bottom is already part of the solution
                [
                    1000 | 0101 => 1101
                    1000 | 0010 => 1010
                ]
                So for left we need:
                int position = numberOfRows - depth
                int mask = 1 << position
                leftSuffix = (piece.top << position - 1) & mask
                rightSuffix = (piece.top << position) & mask
             */
            int position = numberOfRows - depth;
            int mask = 1 << position;
            int leftSuffix = (piece.top << position - 1) & mask;
            int rightSuffix = (piece.top << position) & mask;

            List<int[]> combinations = new ArrayList<>();
            for (int[] childCombination : childCombinations) {
                combinations.add(new int[]{
                    leftSuffix | childCombination[0],
                    rightSuffix | childCombination[1],
                });
            }
            return combinations;
        }

        @Override
        public boolean hasNotSymmetricDrawing() {
            return !piece.symmetric;
        }
    }

    /*
        It analyzes a row, it will get all combination possible of pieces that are respecting the drawing for this
        column, and matching the existing constraints, the constraints are based on the previous column.
     */
    static Map<Integer, Long> analyzeColumn(int colIdx, Constraints constraints, boolean[][] drawing, Map<Integer, List<int[]>> memo) {
        int colBits = columnToBits(colIdx, drawing);
        List<int[]> combinations;
        if ((combinations = memo.get(colBits)) == null) {
            combinations = generateCombinations(colIdx, drawing);
            memo.put(colBits, combinations);
            if (DEBUG) log(format(
                "+ finishing analyzing column %d\nsolutions are (%d):\n%s\n",
                colIdx,
                combinations.size(),
                combinations.stream()
                             .map(s -> dumpSolution(s, drawing.length + 1))
                             .collect(Collectors.joining("---\n"))
            ));
        } else {
            if (DEBUG) log(format(
                "+ found solutions (%d) from memo, for col %d, drawing:%n%s%n",
                colIdx,
                combinations.size(),
                dumpColumn(colBits, drawing.length)
            ));
        }

        /*
            merge the permutations with the existing constraints, we will
            match the left columns with right columns of existing constraints
            for example:
            if we have the permutations from previous constraints
                ?X->2
                ?Z->3
            and current columns
                XY
                ZW
                ZY
            the merge will be:
                ?XY->2
                ?ZW->3
                ?ZY->3
            and finally we just care about the right column
                ?Y->5
                ?W->3
         */
        return mergePermutations(constraints, combinations);
    }

    static List<int[]> generateCombinations(int colIdx, boolean[][] drawing) {
        final Vertex root = new Root();

        List<Vertex> previousVertices = Collections.singletonList(root);
        for (int rowIdx = 0; rowIdx < drawing.length; rowIdx++) {
             if (DEBUG) log(format(
                 "Analyze cell (%d, %d): %d",
                 rowIdx,
                 colIdx,
                 drawing[rowIdx][colIdx] ? 1 : 0
             ));

            Piece[] allowedPieces = drawing[rowIdx][colIdx] ? PRODUCES_ONE : PRODUCES_ZERO;
            if (rowIdx == 0) {
                /*
                    As all combinations are symmetric, we don't want to analyze all pieces for the
                    first row, we just want the symmetric ones.
                 */
                allowedPieces = Arrays.stream(allowedPieces)
                                      .filter(piece -> UNIQUE_DRAWINGS.contains(piece.drawing))
                                      .toArray(Piece[]::new);
            }
            List<Vertex> previousVerticesForRow = new ArrayList<>();
            for (Piece allowedPiece : allowedPieces) {
                PieceVertex vertex = null;
                for (Vertex previousVertex : previousVertices) {
                    if (previousVertex.matchBottom(allowedPiece)) {
                        if (vertex == null) {
                            vertex = new PieceVertex(allowedPiece);
                            previousVerticesForRow.add(vertex);
                        }
                        previousVertex.addChild(vertex);
                    }
                }
            }
            for (Vertex previousVertex : previousVertices) {
                if (previousVertex.children().isEmpty()) {
                    previousVertex.removeDeadBranch();
                }
            }

            previousVertices = previousVerticesForRow;
        }

        // now we need to analyze the graph, to build all possible columns
        // we will store the combinations in a tuple of ints, first index is current column
        // second index is next column
        return extractCombinations(root, drawing.length + 1);
    }

    static int columnToBits(int colIdx, boolean[][] drawing) {
        int res = 0;
        for (int i = drawing.length - 1; i >= 0; i--) {
            if (drawing[i][colIdx]) {
                res |= 1 << i;
            }
        }
        return res;
    }

    static List<int[]> extractCombinations(Vertex root, int numberOfRows) {
        // do a DFS and extract all combinations
        return extractCombinationsDFS(root, new HashMap<>(), 0, numberOfRows);
    }

    static List<int[]> extractCombinationsDFS(Vertex current, Map<Vertex, List<int[]>> visited, int depth, int numberOfRows) {
        List<int[]> combinations = new ArrayList<>();
        if (current.children().isEmpty()) {
            // return the current drawing in two columns
            int[] currentColumns = current.columns();
            if (currentColumns != null) {
                combinations.add(currentColumns);
            }
        } else {
            for (Vertex child : current.children()) {
                List<int[]> childCombinations;
                if ((childCombinations = visited.get(child)) == null) {
                    childCombinations = extractCombinationsDFS(child, visited, depth + 1, numberOfRows);
                }
                combinations.addAll(current.appendToCombinations(childCombinations, depth, numberOfRows));
            }
        }

        // if we are at first depth, and the current drawing is not symmetric, we need double the solutions
        // if we have
        // AB
        // CD
        // we also want
        // BA
        // DC
        // (because at first level, we analyzed only the unique drawings)
        if (depth == 1 && current.hasNotSymmetricDrawing()) {
            List<int[]> symmetricCombinations = new ArrayList<>(combinations.size());
            for (int[] combination : combinations) {
                symmetricCombinations.add(new int[]{combination[1], combination[0]});
            }
            combinations.addAll(symmetricCombinations);
        }

        if (depth > 1) {
            // store the value in the visited map, we are acyclic so we can do it here
            visited.put(current, combinations);
        }

        return combinations;
    }

    static Map<Integer, Long> mergePermutations(Constraints constraints,
                                                List<int[]> combinations) {

        final Map<Integer, Long> permutations = new HashMap<>();
        for (int[] combination : combinations) {
            int leftColumn = combination[0];
            int rightColumn = combination[1];
            final long numberOfPermutations = constraints.getNumberOfPermutationsFor(leftColumn);
            if (numberOfPermutations > 0) {
                permutations.compute(
                    rightColumn,
                    (k, v) -> v == null ? numberOfPermutations : v + numberOfPermutations
                );
            }
        }

        return permutations;
    }

    public static int solution(boolean[][] g) {
        Constraints constraints = new NoConstraints();
        Map<Integer, Long> columnPermutations = new HashMap<>();

        /*
            we are analyzing columns by columns, a column is containing 9 rows maximum.
            So we have 2^9 possible columns. Each time we find some combinations, we will
            store them. Like that if we meet another similar column, we will not have to
            compute same combinations again
         */
        final Map<Integer, List<int[]>> memo = new HashMap<>();
        for (int colIdx = 0; colIdx < g[0].length; colIdx++) {
            if (DEBUG) log("=> analyze column " + colIdx);
            columnPermutations = analyzeColumn(colIdx, constraints, g, memo);
            constraints = new PreviousColConstraints(columnPermutations);
        }
        final long permutations = columnPermutations.values().stream().reduce(Long::sum).orElse(0L);
        return (int) permutations; // hypothesis is that there are less than 10^9 permutations
    }

    static void log(String msg) {
        System.out.println(msg);
    }

    static String dumpSolution(int[] solution, int numberOfRows) {
        StringBuilder sb = new StringBuilder();
        char[] leftChars = formatBinary(solution[0], numberOfRows).toCharArray();
        char[] rightChars = formatBinary(solution[1], numberOfRows).toCharArray();
        for (int i = 0; i < leftChars.length; i++) {
            sb.append(leftChars[i]).append(rightChars[i]).append('\n');
        }
        return sb.toString();
    }

    static String dumpColumn(int col, int numberOfRows) {
        StringBuilder sb = new StringBuilder();
        char[] chars = formatBinary(col, numberOfRows).toCharArray();
        for (char aChar : chars) {
            sb.append(aChar).append('\n');
        }
        return sb.toString();
    }

    static String formatBinary(long number, int width) {
        return format("%" + width + "s", Long.toBinaryString(number))
            .replace(" ", "0");
    }
}
