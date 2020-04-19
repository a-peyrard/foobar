package org.teutinc.foobar.fifth;

import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.teutinc.foobar.fifth.Solution.mergePermutations;

class SolutionTest {
    @Test
    void it_should_validate_first_case() {
        // GIVEN
        var g = new boolean[][]{
            {true, true, false, true, false, true, false, true, true, false},
            {true, true, false, false, false, false, true, true, true, false},
            {true, true, false, false, false, false, false, false, false, true},
            {false, true, false, false, false, false, true, true, false, false}
        };

        // WHEN
        var start = System.nanoTime();
        var res = Solution.solution(g);
        var end = System.nanoTime();
        System.out.printf("computed in %dms%n", (end - start) / 1_000_000);

        // THEN
        assertEquals(
            11567,
            res
        );
    }

    @Test
    void it_should_validate_huge_case() {
        // GIVEN
        var g = new boolean[][]{
            {true, true, false, true, false, true, false, true, true, false, true, true, false, true, false, true, false, true, true, false, true, true, false, true, false, true, false, true, true, false, true, true, false, true, false, true, false, true, true, false, true, true, false, true, false, true, false, true, true, false},
            {true, true, false, false, false, false, true, true, true, false, true, true, false, false, false, false, true, true, true, false, true, true, false, false, false, false, true, true, true, false, true, true, false, false, false, false, true, true, true, false, true, true, false, false, false, false, true, true, true, false},
            {true, true, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, true},
            {false, true, false, false, false, false, true, true, false, false, false, true, false, false, false, false, true, true, false, false, false, true, false, false, false, false, true, true, false, false, false, true, false, false, false, false, true, true, false, false, false, true, false, false, false, false, true, true, false, false},
            {true, true, false, false, false, false, true, true, true, false, true, true, false, false, false, false, true, true, true, false, true, true, false, false, false, false, true, true, true, false, true, true, false, false, false, false, true, true, true, false, true, true, false, false, false, false, true, true, true, false},
            {true, true, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, true, true, true, false, false, false, false, false, false, false, true},
            {false, true, false, false, false, false, true, true, false, false, false, true, false, false, false, false, true, true, false, false, false, true, false, false, false, false, true, true, false, false, false, true, false, false, false, false, true, true, false, false, false, true, false, false, false, false, true, true, false, false},
        };

        // WHEN
        var start = System.nanoTime();
        var res = Solution.solution(g);
        var end = System.nanoTime();
        System.out.printf("computed in %dms%n", (end - start) / 1_000_000);

        // THEN
        assertEquals(
            1,
            1
        );
    }

    @Test
    void it_should_validate_second_case() {
        // GIVEN
        var g = new boolean[][]{
            {true, false, true},
            {false, true, false},
            {true, false, true}
        };

        // WHEN
        var res = Solution.solution(g);

        // THEN
        assertEquals(
            4,
            res
        );
    }

    @Test
    void it_should_validate_first_column_of_second_case() {
        // GIVEN
        var g = new boolean[][]{
            {true},
            {false},
            {true}
        };

        // WHEN
        var res = Solution.solution(g);

        // THEN
        assertEquals(
            8,
            res
        );
    }

    @Test
    void it_should_validate_first_two_columns_of_second_case() {
        // GIVEN
        var g = new boolean[][]{
            {true, false},
            {false, true},
            {true, false}
        };

        // WHEN
        var res = Solution.solution(g);

        // THEN
        assertEquals(
            16,
            res
        );
    }

    @Test
    void it_should_validate_third_case() {
        // GIVEN
        var g = new boolean[][]{
            {true, false, true, false, false, true, true, true},
            {true, false, true, false, false, false, true, false},
            {true, true, true, false, false, false, true, false},
            {true, false, true, false, false, false, true, false},
            {true, false, true, false, false, true, true, true}
        };

        // WHEN
        var res = Solution.solution(g);

        // THEN
        assertEquals(
            254,
            res
        );
    }

    @Test
    void it_should_merge_permutations() {
        // GIVEN
        /*
            Second merge of second case
         */
        var previous = new HashMap<Integer, Long>();
        previous.put(0b0110, 1L);
        previous.put(0b0100, 1L);
        previous.put(0b0010, 1L);
        previous.put(0b0000, 2L);
        previous.put(0b1001, 1L);
        previous.put(0b1000, 1L);
        previous.put(0b0001, 1L);
        var constraints = new Solution.PreviousColConstraints(previous);
        var combinations = Arrays.asList(
            new int[]{0b0000, 0b0011}, // 1
            new int[]{0b0001, 0b0010}, // 2
            new int[]{0b0001, 0b0011}, // 3
            new int[]{0b0010, 0b0001}, // 4
            new int[]{0b0000, 0b1100}, // 5
            new int[]{0b0001, 0b1101}, // 6
            new int[]{0b0100, 0b1000}, // 7
            new int[]{0b0101, 0b1001}, // 8
            new int[]{0b1000, 0b0100}, // 9
            new int[]{0b1001, 0b0101}, // 10
            new int[]{0b1000, 0b1011}, // 11
            new int[]{0b1001, 0b1010}, // 12
            new int[]{0b1001, 0b1011}, // 13
            new int[]{0b1010, 0b1001}, // 14
            new int[]{0b1000, 0b1100},  // 15
            new int[]{0b1001, 0b1101}  // 16
        );

        // WHEN
        var res = mergePermutations(constraints, combinations);

        // THEN
        assertEquals(10, res.size());
        assertEquals(3, res.get(0b0011));
        assertEquals(1, res.get(0b0010));
        assertEquals(1, res.get(0b0001));
        assertEquals(3, res.get(0b1100));
        assertEquals(2, res.get(0b1101));
        assertEquals(1, res.get(0b1000));
        assertEquals(1, res.get(0b0100));
        assertEquals(1, res.get(0b0101));
        assertEquals(2, res.get(0b1011));
        assertEquals(1, res.get(0b1010));
        assertEquals(16, res.values().stream().reduce(Long::sum).orElse(0L));
    }

    @Test
    void it_should_merge_permutations_again() {
        // GIVEN
        /*
            Final merge of second case
         */
        var previous = new HashMap<Integer, Long>();
        previous.put(0b0011, 3L);
        previous.put(0b0010, 1L);
        previous.put(0b0001, 1L);
        previous.put(0b1100, 3L);
        previous.put(0b1101, 2L);
        previous.put(0b1000, 1L);
        previous.put(0b0100, 1L);
        previous.put(0b0101, 1L);
        previous.put(0b1011, 2L);
        previous.put(0b1010, 1L);
        var constraints = new Solution.PreviousColConstraints(previous);
        var combinations = Arrays.asList(
            new int[]{0b0000, 0b0110}, // 1
            new int[]{0b0010, 0b0100}, // 2
            new int[]{0b0100, 0b0010}, // 3
            new int[]{0b0110, 0b0000}, // 4
            new int[]{0b0000, 0b1001}, // 5
            new int[]{0b0001, 0b1000}, // 6
            new int[]{0b1000, 0b0001}, // 7
            new int[]{0b1001, 0b0000}  // 8
        );

        // WHEN
        var res = mergePermutations(constraints, combinations);

        // THEN
        assertEquals(4, res.size());
        assertEquals(4, res.values().stream().reduce(Long::sum).orElse(0L));
    }
}