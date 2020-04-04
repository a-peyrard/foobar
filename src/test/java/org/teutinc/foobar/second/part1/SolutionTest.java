package org.teutinc.foobar.second.part1;

import org.junit.jupiter.api.Test;
import org.teutinc.foobar.second.part1.Solution;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void it_should_validate_first_case() {
        // GIVEN
        var s = "<<>><";

        // WHEN
        var res = Solution.solution(s);

        // THEN
        assertEquals(4, res);
    }

    @Test
    void it_should_validate_second_case() {
        // GIVEN
        var s = ">----<";

        // WHEN
        var res = Solution.solution(s);

        // THEN
        assertEquals(2, res);
    }

    @Test
    void it_should_validate_no_crossing_case() {
        // GIVEN
        var s = "--<<--->>>>---";

        // WHEN
        var res = Solution.solution(s);

        // THEN
        assertEquals(0, res);
    }

    @Test
    void it_should_validate_no_employees_case() {
        // GIVEN
        var s = "--------";

        // WHEN
        var res = Solution.solution(s);

        // THEN
        assertEquals(0, res);
    }

    @Test
    void it_should_validate_not_everyone_crossing_each_other_case() {
        // GIVEN
        var s = ">><>><";

        // WHEN
        var res = Solution.solution(s);

        // THEN
        assertEquals(12, res);
    }
}