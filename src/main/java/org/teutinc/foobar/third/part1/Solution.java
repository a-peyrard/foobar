package org.teutinc.foobar.third.part1;

import java.math.BigInteger;

/*
Bomb, Baby!
===========

You're so close to destroying the LAMBCHOP doomsday device you can taste it! But in order to do so, you need to deploy special self-replicating bombs designed for you by the brightest
scientists on Bunny Planet. There are two types: Mach bombs (M) and Facula bombs (F). The bombs, once released into the LAMBCHOP's inner workings, will automatically deploy to all the
strategic points you've identified and destroy them at the same time.

But there's a few catches. First, the bombs self-replicate via one of two distinct processes:
Every Mach bomb retrieves a sync unit from a Facula bomb; for every Mach bomb, a Facula bomb is created;
Every Facula bomb spontaneously creates a Mach bomb.

For example, if you had 3 Mach bombs and 2 Facula bombs, they could either produce 3 Mach bombs and 5 Facula bombs, or 5 Mach bombs and 2 Facula bombs. The replication process can be changed
each cycle.

Second, you need to ensure that you have exactly the right number of Mach and Facula bombs to destroy the LAMBCHOP device. Too few, and the device might survive. Too many, and you might
overload the mass capacitors and create a singularity at the heart of the space station - not good!

And finally, you were only able to smuggle one of each type of bomb - one Mach, one Facula - aboard the ship when you arrived, so that's all you have to start with. (Thus it may be
impossible to deploy the bombs to destroy the LAMBCHOP, but that's not going to stop you from trying!)

You need to know how many replication cycles (generations) it will take to generate the correct amount of bombs to destroy the LAMBCHOP. Write a function solution(M, F) where M and F are the
number of Mach and Facula bombs needed. Return the fewest number of generations (as a string) that need to pass before you'll have the exact number of bombs necessary to destroy the
LAMBCHOP, or the string "impossible" if this can't be done! M and F will be string representations of positive integers no larger than 10^50. For example, if M = "2"
and F = "1", one generation would need to pass, so the solution would be "1". However, if M = "2" and F = "4", it would not be possible.

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
Solution.solution('2', '1')
Output:
    1

Input:
Solution.solution('4', '7')
Output:
    4

-- Python cases --
Input:
solution.solution('4', '7')
Output:
    4

Input:
solution.solution('2', '1')
Output:
    1

Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will
be removed from your home folder.
 */
public class Solution {
    public static String solution(String x, String y) {
        BigInteger first = new BigInteger(x);
        BigInteger second = new BigInteger(y);
        BigInteger numberOfSteps = new BigInteger("0");

        // we will decrement the number of bombs in each steps, to backtrack and see how we've been here
        while (first.compareTo(BigInteger.ZERO) > 0 && second.compareTo(BigInteger.ZERO) > 0) { // safety, but while true would have been the same...
            if (first.equals(BigInteger.ONE)) {
                return numberOfSteps.add(second.subtract(BigInteger.ONE)).toString(); // (1, X) is done in X-1 steps
            }
            if (second.equals(BigInteger.ONE)) {
                return numberOfSteps.add(first.subtract(BigInteger.ONE)).toString();
            }

            // if we have more facula than mash, it means that previous step was
            final int firstComparedToSecond = first.compareTo(second);
            if (firstComparedToSecond < 0) {
                // if (x > y) we can not remove x from y, we will have a negative case (7, 4) could not have
                // a predecessor where mash where use to create facula
                // we will also optimize, as for example if we have (4, 27), we don't want to do 6 steps
                // to end to (4, 3), we can directly do: floor(27/4) = 6, and then y = 27 - 6*4 = 3, and increment nb
                // steps by 6.
                BigInteger[] tuple = subtract(second, first);
                second = tuple[0];
                numberOfSteps = numberOfSteps.add(tuple[1]);
            } else if (firstComparedToSecond > 0) {
                BigInteger[] tuple = subtract(first, second);
                first = tuple[0];
                numberOfSteps = numberOfSteps.add(tuple[1]);
            } else {
                // this is an impossible case, we can not manage to get (x, x) except for (1, 1)
                return "impossible";
            }
        }

        return "impossible";
    }

    static BigInteger[] subtract(BigInteger toBeSubtracted, BigInteger toSubtract) {
        BigInteger rounds = toBeSubtracted.divide(toSubtract);
        return new BigInteger[]{
            toBeSubtracted.subtract(toSubtract.multiply(rounds)),
            rounds
        };
    }
}
