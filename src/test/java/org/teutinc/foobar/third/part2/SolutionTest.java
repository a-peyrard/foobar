package org.teutinc.foobar.third.part2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @Test
    void it_should_validate_first_case() {
        // GIVEN
        var n = 3;

        // WHEN
        var res = Solution.solution(n);

        // THEN
        assertEquals(
            1,
            res
        );
    }

    @Test
    void it_should_validate_second_case() {
        // GIVEN
        var n = 200;

        // WHEN
        var res = Solution.solution(n);

        // THEN
        assertEquals(
            487067745,
            res
        );
    }
}