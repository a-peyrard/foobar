package org.teutinc.foobar.third.part1;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

    @Test
    void it_should_validate_case_1_6() {
        // GIVEN
        var x = "1";
        var y = "6";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "5",
            res
        );
    }

    @Test
    void it_should_validate_case_6_1() {
        // GIVEN
        var x = "6";
        var y = "1";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "5",
            res
        );
    }

    @Test
    void it_should_validate_case_6_5() {
        // GIVEN
        var x = "6";
        var y = "5";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "5",
            res
        );
    }

    @Test
    void it_should_validate_case_5_9() {
        // GIVEN
        var x = "5";
        var y = "9";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "5",
            res
        );
    }

    @Test
    void it_should_validate_case_9_4() {
        // GIVEN
        var x = "9";
        var y = "4";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "5",
            res
        );
    }

    @Test
    void it_should_validate_case_4_11() {
        // GIVEN
        var x = "4";
        var y = "11";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "5",
            res
        );
    }

    @Test
    void it_should_validate_case_11_7() {
        // GIVEN
        var x = "11";
        var y = "7";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "5",
            res
        );
    }

    @Test
    void it_should_validate_case_7_10() {
        // GIVEN
        var x = "7";
        var y = "10";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "5",
            res
        );
    }

    @Test
    void it_should_validate_case_10_3() {
        // GIVEN
        var x = "10";
        var y = "3";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "5",
            res
        );
    }

    @Test
    void it_should_validate_case_3_11() {
        // GIVEN
        var x = "3";
        var y = "11";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "5",
            res
        );
    }

    @Test
    void it_should_validate_case_1_1() {
        // GIVEN
        var x = "1";
        var y = "1";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "0",
            res
        );
    }

    @Test
    void it_should_validate_case_7_17() {
        // GIVEN
        var x = "7";
        var y = "17";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "6",
            res
        );
    }

    @Test
    void it_should_validate_case_11_2() {
        // GIVEN
        var x = "11";
        var y = "2";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "6",
            res
        );
    }

    @Test
    void it_should_validate_case_7_9() {
        // GIVEN
        var x = "7";
        var y = "9";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "5",
            res
        );
    }

    @Test
    void it_should_validate_big_big_case() {
        /*
            we have no proof for its correctness,
            but at least it should not end in stack overflow...
         */

        // GIVEN
        var x = "794322434343324343344343243355555";
        var y = "93342432434343433243432342";

        // WHEN
        var res = Solution.solution(x, y);

        // THEN
        assertEquals(
            "8510201",
            res
        );
    }
}