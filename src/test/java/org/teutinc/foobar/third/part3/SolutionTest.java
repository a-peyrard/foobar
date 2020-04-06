package org.teutinc.foobar.third.part3;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @Test
    void it_should_validate_first_case() {
        // GIVEN
        var l = new int[]{1, 1, 1};

        // WHEN
        var res = Solution.solution(l);

        // THEN
        assertEquals(
            1,
            res
        );
    }

    @Test
    void it_should_validate_second_case() {
        // GIVEN
        var l = new int[]{1, 2, 3, 4, 5, 6};

        // WHEN
        var res = Solution.solution(l);

        // THEN
        assertEquals(
            3,
            res
        );
    }
}