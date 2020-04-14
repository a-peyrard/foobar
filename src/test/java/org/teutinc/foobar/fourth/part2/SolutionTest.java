package org.teutinc.foobar.fourth.part2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

class SolutionTest {
    @Test
    void it_should_validate_first_case() {
        // GIVEN
        var times = new int[][]{
            {0, 1, 1, 1, 1},
            {1, 0, 1, 1, 1},
            {1, 1, 0, 1, 1},
            {1, 1, 1, 0, 1},
            {1, 1, 1, 1, 0}
        };
        var timeLimit = 3;

        // WHEN
        var res = Solution.solution(times, timeLimit);

        // THEN
        assertArrayEquals(
            new int[]{0, 1},
            res
        );
    }

    @Test
    void it_should_validate_second_case() {
        // GIVEN
        var times = new int[][]{
            {0, 2, 2, 2, -1},
            {9, 0, 2, 2, -1},
            {9, 3, 0, 2, -1},
            {9, 3, 2, 0, -1},
            {9, 3, 2, 2, 0}
        };
        var timeLimit = 1;

        // WHEN
        var res = Solution.solution(times, timeLimit);

        // THEN
        assertArrayEquals(
            new int[]{1, 2},
            res
        );
    }

    @Test
    void it_should_validate_all_paths_to_0_case() {
        // GIVEN
        var times = new int[][]{
            {0, 1, 10, 10, 10},
            {1, 0, 1, 1, 1},
            {10, 1, 0, 10, 10},
            {10, 1, 10, 0, 10},
            {10, 1, 10, 10, 0}
        };
        var timeLimit = 6;

        // WHEN
        var res = Solution.solution(times, timeLimit);

        // THEN
        assertArrayEquals(
            new int[]{0, 1, 2},
            res
        );
    }

    @Test
    void it_should_validate_all_paths_to_0_second_case() {
        // GIVEN
        // same as previous, but bunny 1 is too expensive to get
        var times = new int[][]{
            {0, 1, 10, 10, 10},
            {1, 0, 10, 1, 1},
            {10, 1, 0, 10, 10},
            {10, 1, 10, 0, 10},
            {10, 1, 10, 10, 0}
        };
        var timeLimit = 6;

        // WHEN
        var res = Solution.solution(times, timeLimit);

        // THEN
        assertArrayEquals(
            new int[]{0, 2},
            res
        );
    }

    @Test
    void it_should_validate_all_paths_to_0_third_case() {
        // GIVEN
        // a bit like the initial case, we always have to get through 0 to
        // go to other bunnies, but we also need to go through BH.
        // So paths look like this:
        // 0 -> X -> BH -> 0 / 0 -> Y -> BH - 0...
        // se we are using the edge BH -> 0 more than once
        // solution => S01B02B03B
        var times = new int[][]{
            {0, 1, 10, 10, 10, 10},
            {10, 0, 1, 1, 1, 10},
            {10, 10, 0, 10, 10, 1},
            {10, 10, 10, 0, 10, 1},
            {10, 10, 10, 10, 0, 1},
            {10, 1, 10, 10, 10, 0}
        };
        var timeLimit = 9;

        // WHEN
        var res = Solution.solution(times, timeLimit);

        // THEN
        assertArrayEquals(
            new int[]{0, 1, 2, 3},
            res
        );
    }

    @Test
    void it_should_get_all_bunnies_with_negative_cycles() {
        // GIVEN
        var times = new int[][]{
            {0, 10, 10, 10, 5},
            {-4, 0, 10, 10, 10},
            {10, 10, 0, 10, 10},
            {10, 10, 10, 0, 10},
            {10, -3, 10, 10, 0}
        };
        var timeLimit = 1;

        // WHEN
        var res = Solution.solution(times, timeLimit);

        // THEN
        assertArrayEquals(
            new int[]{0, 1, 2},
            res
        );
    }

    @Test
    void it_should_get_all_bunnies_with_more_than_enough_time() {
        // GIVEN
        var times = new int[][]{
            {0, 1, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 1, 1},
            {1, 1, 0, 1, 1, 1, 1},
            {1, 1, 1, 0, 1, 1, 1},
            {1, 1, 1, 1, 0, 1, 1},
            {1, 1, 1, 1, 1, 0, 1},
            {1, 1, 1, 1, 1, 1, 0},
        };
        var timeLimit = 999;

        // WHEN
        var res = Solution.solution(times, timeLimit);

        // THEN
        assertArrayEquals(
            new int[]{0, 1, 2, 3, 4},
            res
        );
    }

    @Test
    void it_should_get_all_bunnies_but_one_unreachable_with_more_than_enough_time() {
        // GIVEN
        var times = new int[][]{
            {0, 1000, 1, 1, 1, 1, 1},
            {1, 0, 1, 1, 1, 1, 1},
            {1, 1000, 0, 1, 1, 1, 1},
            {1, 1000, 1, 0, 1, 1, 1},
            {1, 1000, 1, 1, 0, 1, 1},
            {1, 1000, 1, 1, 1, 0, 1},
            {1, 1000, 1, 1, 1, 1, 0},
        };
        var timeLimit = 999;

        // WHEN
        var res = Solution.solution(times, timeLimit);

        // THEN
        assertArrayEquals(
            new int[]{1, 2, 3, 4},
            res
        );
    }

    @Test
    void it_should_get_all_bunnies_with_null_path() {
        // GIVEN
        var times = new int[][]{
            {0, 2, 2, 2, 2, 0, 2},
            {2, 0, 2, 2, 2, 2, 0},
            {2, 0, 0, 2, 2, 2, 2},
            {2, 2, 0, 0, 2, 2, 2},
            {2, 2, 2, 0, 0, 2, 2},
            {2, 2, 2, 2, 0, 0, 2},
            {2, 2, 2, 2, 2, 2, 0},
        };
        var timeLimit = 1;

        // WHEN
        var res = Solution.solution(times, timeLimit);

        // THEN
        assertArrayEquals(
            new int[]{0, 1, 2, 3, 4},
            res
        );
    }
}