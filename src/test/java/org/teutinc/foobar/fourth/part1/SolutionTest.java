package org.teutinc.foobar.fourth.part1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @Test
    void it_should_validate_first_case() {
        // GIVEN
        var entrances = new int[]{0, 1};
        var exits = new int[]{4, 5};
        var paths = new int[][]{
            {0, 0, 4, 6, 0, 0},
            {0, 0, 5, 2, 0, 0},
            {0, 0, 0, 0, 4, 4},
            {0, 0, 0, 0, 6, 6},
            {0, 0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0, 0}
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            16,
            res
        );
    }

    @Test
    void it_should_validate_second_case() {
        // GIVEN
        var entrances = new int[]{0};
        var exits = new int[]{3};
        var paths = new int[][]{
            {0, 7, 0, 0},
            {0, 0, 6, 0},
            {0, 0, 0, 8},
            {9, 0, 0, 0}
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            6,
            res
        );
    }

    @Test
    void it_should_validate_third_case() {
        // GIVEN
        var entrances = new int[]{0};
        var exits = new int[]{3};
        var paths = new int[][]{
            {0, 8, 0, 0},
            {0, 0, 5, 2},
            {0, 0, 0, 3},
            {0, 0, 0, 0}
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            5,
            res
        );
    }

    @Test
    void it_should_validate_fourth_case() {
        // GIVEN
        var entrances = new int[]{0};
        var exits = new int[]{3};
        var paths = new int[][]{
            {0, 3, 2, 0},
            {0, 0, 5, 0},
            {0, 0, 0, 8},
            {0, 0, 0, 0}
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            5,
            res
        );
    }

    @Test
    void it_should_validate_fifth_case() {
        // GIVEN
        var entrances = new int[]{0, 1};
        var exits = new int[]{4};
        var paths = new int[][]{
            {0, 0, 8, 0, 0},
            {0, 0, 10, 0, 0},
            {0, 0, 0, 6, 2},
            {0, 0, 0, 0, 8},
            {0, 0, 0, 0, 0}
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            8,
            res
        );
    }

    @Test
    void it_should_validate_sixth_case() {
        // GIVEN
        var entrances = new int[]{0, 1};
        var exits = new int[]{3, 4};
        var paths = new int[][]{
            {0, 0, 8, 0, 0},
            {0, 0, 10, 0, 0},
            {0, 0, 0, 2, 6},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0}
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            8,
            res
        );
    }

    @Test
    void it_should_validate_seventh_case() {
        // GIVEN
        var entrances = new int[]{0};
        var exits = new int[]{2, 3};
        var paths = new int[][]{
            {0, 18, 0, 0},
            {0, 0, 2, 6},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            8,
            res
        );
    }

    @Test
    void it_should_validate_eighth_case() {
        // GIVEN
        var entrances = new int[]{0};
        var exits = new int[]{4};
        var paths = new int[][]{
            {0, 4, 0, 0, 0},
            {0, 0, 3, 0, 0},
            {0, 0, 0, 8, 5},
            {0, 10, 0, 0, 0},
            {0, 0, 0, 0, 0}
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            3,
            res
        );
    }

    @Test
    void it_should_validate_basic_first_case() {
        // GIVEN
        /*
            0 -5-> 1
         */

        var entrances = new int[]{0};
        var exits = new int[]{1};
        var paths = new int[][]{
            {0, 5},
            {0, 0}
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            5,
            res
        );
    }

    @Test
    void it_should_validate_basic_first_case_with_cycle() {
        // GIVEN
        /*
            0 -5-> 1
             \_30_/
         */

        var entrances = new int[]{0};
        var exits = new int[]{1};
        var paths = new int[][]{
            {0, 5},
            {30, 0}
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            5,
            res
        );
    }

    @Test
    void it_should_validate_basic_first_case_with_another_cycle() {
        // GIVEN
        /*
           |--|
          34  0 -5-> 1
           |--^
         */

        var entrances = new int[]{0};
        var exits = new int[]{1};
        var paths = new int[][]{
            {34, 5},
            {0, 0}
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            5,
            res
        );
    }

    @Test
    void it_should_validate_basic_second_case() {
        // GIVEN
        /*
            0 -5-> 2

            1 -8-> 3

         */

        var entrances = new int[]{0, 1};
        var exits = new int[]{2, 3};
        var paths = new int[][]{
            {0, 0, 5, 0},
            {0, 0, 0, 8},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            13,
            res
        );
    }

    @Test
    void it_should_validate_basic_second_case_with_linked_entrances() {
        // GIVEN
        /*
            0 -5-> 2
            I
           30
            V
            1 -8-> 3

         */

        var entrances = new int[]{0, 1};
        var exits = new int[]{2, 3};
        var paths = new int[][]{
            {0, 30, 5, 0},
            {0, 0, 0, 8},
            {0, 0, 0, 0},
            {0, 0, 0, 0},
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            13,
            res
        );
    }

    @Test
    void it_should_validate_new_first_case() {
        // GIVEN
        /*
                            /--5-> 3
            0 -8-> 1 -13-> 2
                            \--5-> 4
         */

        var entrances = new int[]{0};
        var exits = new int[]{3, 4};
        var paths = new int[][]{
            {0, 8, 0, 0, 0},
            {0, 0, 13, 0, 0},
            {0, 0, 0, 5, 5},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            8,
            res
        );
    }

    @Test
    void it_should_validate_new_second_case() {
        // GIVEN
        /*
                            /--5-> 3
            0 -13-> 1 -8-> 2
                            \--5-> 4
         */

        var entrances = new int[]{0};
        var exits = new int[]{3, 4};
        var paths = new int[][]{
            {0, 13, 0, 0, 0},
            {0, 0, 8, 0, 0},
            {0, 0, 0, 5, 5},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            8,
            res
        );
    }

    @Test
    void it_should_validate_new_third_case() {
        // GIVEN
        /*
                            /--15-> 3
            0 -13-> 1 -8-> 2
                            \--5-> 4
         */

        var entrances = new int[]{0};
        var exits = new int[]{3, 4};
        var paths = new int[][]{
            {0, 13, 0, 0, 0},
            {0, 0, 8, 0, 0},
            {0, 0, 0, 5, 5},
            {0, 0, 0, 0, 0},
            {0, 0, 0, 0, 0},
        };

        // WHEN
        var res = Solution.solution(entrances, exits, paths);

        // THEN
        assertEquals(
            8,
            res
        );
    }
}