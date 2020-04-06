package org.teutinc.foobar.third.part2;

import java.util.Arrays;

/*
The Grandest Staircase Of Them All
==================================

With her LAMBCHOP doomsday device finished, Commander Lambda is preparing for her debut on the galactic stage - but in order to make a grand entrance, she needs a grand staircase! As her
personal assistant, you've been tasked with figuring out how to build the best staircase EVER.

Lambda has given you an overview of the types of bricks available, plus a budget. You can buy different amounts of the different types of bricks (for example, 3 little pink bricks, or 5 blue
lace bricks). Commander Lambda wants to know how many different types of staircases can be built with each amount of bricks, so she can pick the one with the most options.

Each type of staircase should consist of 2 or more steps.  No two steps are allowed to be at the same height - each step must be lower than the previous one. All steps must contain at least
one brick. A step's height is classified as the total amount of bricks that make up that step.
For example, when N = 3, you have only 1 choice of how to build the staircase, with the first step having a height of 2 and the second step having a height of 1: (# indicates a brick)

#
##
21

When N = 4, you still only have 1 staircase choice:

#
#
##
31

But when N = 5, there are two ways you can build a staircase from the given bricks. The two staircases can have heights (4, 1) or (3, 2), as shown below:

#
#
#
##
41

#
##
##
32

Write a function called solution(n) that takes a positive integer n and returns the number of different staircases that can be built from exactly n bricks. n will always be at least 3 (so you
can have a staircase at all), but no more than 200, because Commander Lambda's not made of money!

Languages
=========

To provide a Java solution, edit Solution.java
To provide a Python solution, edit solution.py

Test cases
==========
Your code should pass the following test cases.
Note that it may also be run against hidden test cases not shown here.

-- Java cases --
Input:
Solution.solution(3)
Output:
    1

Input:
Solution.solution(200)
Output:
    487067745

-- Python cases --
Input:
solution.solution(200)
Output:
    487067745

Input:
solution.solution(3)
Output:
    1

Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will
be removed from your home folder.
 */
public class Solution {
    /*
        This is the sequence of partition with distinct parts http://oeis.org/A000009

        We are just removing 1 to the result, as we want at least 2 steps in our staircase.
     */
    public static int solution(int n) {
        int[][] memo = new int[n + 1][];
        for (int i = 0; i < n + 1; i++) {
            memo[i] = new int[n + 1];
            Arrays.fill(memo[i], -1);
        }

        return q(1, n, memo) - 1;
    }

    static int q(int k, int n, int[][] memo) {
        if (k > n) {
            return 0;
        }
        if (k == n) {
            return 1;
        }
        if (memo[k][n] == -1) {
            memo[k][n] = q(k + 1, n, memo) + q(k + 1, n - k, memo);
        }
        return memo[k][n];
    }
}
