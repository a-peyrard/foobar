package org.teutinc.foobar.first;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SolutionTest {

    @Test
    void it_should_ensure_first_case() {
        // GIVEN
        var msg = "Yvzs! I xzm'g yvorvev Lzmxv olhg srh qly zg gsv xlolmb!!";

        // WHEN
        var deciphered = Solution.solution(msg);

        // THEN
        assertEquals(
            "Yeah! I can't believe Lance lost his job at the colony!!",
            deciphered
        );
    }

    @Test
    void it_should_ensure_second_case() {
        // GIVEN
        var msg = "wrw blf hvv ozhg mrtsg'h vkrhlwv?";

        // WHEN
        var deciphered = Solution.solution(msg);

        // THEN
        assertEquals(
            "did you see last night's episode?",
            deciphered
        );
    }
}