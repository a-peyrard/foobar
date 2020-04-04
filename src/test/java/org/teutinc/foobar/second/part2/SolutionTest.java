package org.teutinc.foobar.second.part2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {
    @Test
    void it_should_validate_first_case() {
        // GIVEN
        var l = new String[]{"1.11", "2.0.0", "1.2", "2", "0.1", "1.2.1", "1.1.1", "2.0"};

        // WHEN
        var res = Solution.solution(l);

        // THEN
        assertArrayEquals(
            new String[]{"0.1", "1.1.1", "1.2", "1.2.1", "1.11", "2", "2.0", "2.0.0"},
            res
        );
    }

    @Test
    void it_should_validate_second_case() {
        // GIVEN
        var l = new String[]{"1.1.2", "1.0", "1.3.3", "1.0.12", "1.0.2"};

        // WHEN
        var res = Solution.solution(l);

        // THEN
        assertArrayEquals(
            new String[]{"1.0", "1.0.2", "1.0.12", "1.1.2", "1.3.3"},
            res
        );
    }
}