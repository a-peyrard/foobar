package org.teutinc.foobar.second;

/*
    Alternative solution, way more simple to the problem.

    We analyze from left to right the path, when crossing an employee walking right we increase
    a counter, when crossing an employee heading toward the left direction we just add the number of
    right walker already seen (multiplied by 2, as 2 employees will do 2 salutes) to the global
    counter of salutes.
 */
public class AlternativeSolution {
    private static final char WALKING_LEFT = '<';
    private static final char WALKING_RIGHT = '>';

    public static int solution(String s) {
        final char[] chars = s.toCharArray();

        int rightCounter = 0;
        int numberOfSalutes = 0;

        for (char symbol : chars) {
            if (symbol == WALKING_RIGHT) {
                rightCounter++;
            } else if (symbol == WALKING_LEFT) {
                numberOfSalutes += rightCounter * 2;
            }
        }

        return numberOfSalutes;
    }
}
