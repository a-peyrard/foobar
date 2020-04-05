package org.teutinc.foobar.third.part1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @Test
    void it_should_validate_first_case() {
        // GIVEN
        var x = "2";
        var y = "1";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "1",
            res
        );
    }

    @Test
    void it_should_validate_second_case() {
        // GIVEN
        var x = "4";
        var y = "7";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "4",
            res
        );
    }

    @Test
    void it_should_validate_impossible_case() {
        // GIVEN
        var x = "2";
        var y = "4";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "impossible",
            res
        );
    }
}