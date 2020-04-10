package org.teutinc.foobar.fourth.part1;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import static java.lang.String.format;

/*
Escape Pods
===========

You've blown up the LAMBCHOP doomsday device and broken the bunnies out of
Lambda's prison - and now you need to escape from the space station as quickly and as
orderly as possible! The bunnies have all gathered in various locations throughout the
station, and need to make their way towards the seemingly endless amount of escape pods
positioned in other parts of the station. You need to get the numerous bunnies through the
various rooms to the escape pods. Unfortunately, the corridors between the rooms can only
fit so many bunnies at a time. What's more, many of the corridors were resized to
accommodate the LAMBCHOP, so they vary in how many bunnies can move through them at a
time.

Given the starting room numbers of the groups of bunnies, the room numbers of the escape
pods, and how many bunnies can fit through at a time in each direction of every corridor
in between, figure out how many bunnies can safely make it to the escape pods at a time at
peak.

Write a function solution(entrances, exits, path) that takes an array of integers denoting
where the groups of gathered bunnies are, an array of integers denoting where the escape
pods are located, and an array of an array of integers of the corridors, returning the
total number of bunnies that can get through at each time step as an int. The entrances
and exits are disjoint and thus will never overlap. The path element path[A][B] = C
describes that the corridor going from A to B can fit C bunnies at each time step.  There
are at most 50 rooms connected by the corridors and at most 2000000 bunnies that will fit
at a time.

For example, if you have:
entrances = [0, 1]
exits = [4, 5]
path = [
  [0, 0, 4, 6, 0, 0],  # Room 0: Bunnies
  [0, 0, 5, 2, 0, 0],  # Room 1: Bunnies
  [0, 0, 0, 0, 4, 4],  # Room 2: Intermediate room
  [0, 0, 0, 0, 6, 6],  # Room 3: Intermediate room
  [0, 0, 0, 0, 0, 0],  # Room 4: Escape pods
  [0, 0, 0, 0, 0, 0],  # Room 5: Escape pods
]

Then in each time step, the following might happen:
0 sends 4/4 bunnies to 2 and 6/6 bunnies to 3
1 sends 4/5 bunnies to 2 and 2/2 bunnies to 3
2 sends 4/4 bunnies to 4 and 4/4 bunnies to 5
3 sends 4/6 bunnies to 4 and 4/6 bunnies to 5

So, in total, 16 bunnies could make it to the escape pods at 4 and 5 at each time step.
(Note that in this example, room 3 could have sent any variation of 8 bunnies to 4 and 5,
such as 2/6 and 6/6, but the final solution remains the same.)

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
Solution.solution({0, 1}, {4, 5}, {{0, 0, 4, 6, 0, 0}, {0, 0, 5, 2, 0, 0}, {0, 0, 0, 0, 4,
4}, {0, 0, 0, 0, 6, 6}, {0, 0, 0, 0, 0, 0}, {0, 0, 0, 0, 0, 0}})
Output:
    16

Input:
Solution.solution({0}, {3}, {{0, 7, 0, 0}, {0, 0, 6, 0}, {0, 0, 0, 8}, {9, 0, 0, 0}})
Output:
    6

-- Python cases --
Input:
solution.solution([0], [3], [[0, 7, 0, 0], [0, 0, 6, 0], [0, 0, 0, 8], [9, 0, 0, 0]])
Output:
    6

Input:
solution.solution([0, 1], [4, 5], [[0, 0, 4, 6, 0, 0], [0, 0, 5, 2, 0, 0], [0, 0, 0, 0, 4,
4], [0, 0, 0, 0, 6, 6], [0, 0, 0, 0, 0, 0], [0, 0, 0, 0, 0, 0]])
Output:
    16
 */
public class Solution {
    static final boolean DEBUG = false;

    public static int solution(int[] entrances, int[] exits, int[][] paths) {
        final Set<Integer> visited = new HashSet<>();
        final Set<Integer> entranceSet = Arrays.stream(entrances)
                                               .boxed()
                                               .collect(Collectors.toSet());

        final int[] remainingFlows = new int[paths.length];
        Arrays.fill(remainingFlows, -1);

        int flow = 0;
        for (int exit : exits) {
            flow += visit(
                exit,
                Integer.MAX_VALUE,
                Integer.MAX_VALUE,
                visited,
                paths,
                entranceSet,
                remainingFlows,
                0
            );
        }

        return flow;
    }

    static int visit(int vertex,
                     int maxAllowedFlow,
                     int maxPossibleFlow,
                     Set<Integer> visited,
                     int[][] paths,
                     Set<Integer> entrances,
                     int[] remainingFlows,
                     int depth) {

        if (DEBUG) log("-> visit " + vertex, depth);

        visited.add(vertex);
        if (entrances.contains(vertex)) {
            if (DEBUG) log("<- entrance " + vertex, depth);
            // the vertex is an entrance, store the available flow from this vertex
            remainingFlows[vertex] = Integer.MAX_VALUE;
            // so return the max flow we could get from the successor
            return maxPossibleFlow;
        }

        int availableFlow = 0;
        for (int predecessor = 0; predecessor < paths.length; predecessor++) {
            int possibleFlow = paths[predecessor][vertex];
            if (possibleFlow != 0) {
                int actualFlow = 0;
                if (visited.contains(predecessor)) {
                    // the vertex has already been visited, but maybe there is still some available flow in it
                    int remainingFlow = remainingFlows[predecessor];
                    if (remainingFlow > 0) {
                        if (remainingFlow == Integer.MAX_VALUE) {
                            // the node is having some infinite flow, so just get as much as we want
                            actualFlow = possibleFlow;
                        } else if (remainingFlow > possibleFlow) {
                            // we have more remaining flow that we can take, just take
                            // as much as we can
                            actualFlow = possibleFlow;
                            remainingFlows[predecessor] -= possibleFlow;
                        } else {
                            // just take what is available
                            actualFlow = remainingFlows[predecessor];
                            remainingFlows[predecessor] = 0;
                        }
                    }
                } else {
                    // visit the unvisited vertex
                    actualFlow = visit(
                        predecessor,
                        Math.min(maxAllowedFlow, possibleFlow),
                        possibleFlow,
                        visited,
                        paths,
                        entrances,
                        remainingFlows,
                        depth + 1
                    );
                    if (DEBUG) log(format("actual flow from %d is %d", predecessor, actualFlow), depth);
                }
                availableFlow += actualFlow;
            }
        }

        if (availableFlow > maxAllowedFlow) {
            // here we have more flow available at the vertex, we will just get
            // the maximum we can handle, and store the remaining flow in case another path
            // comes here
            remainingFlows[vertex] = availableFlow - maxAllowedFlow;
            if (DEBUG) log(format("<- exit %d with remaining flow: %d", vertex, remainingFlows[vertex]), depth);
            return maxAllowedFlow;
        } else {
            // in this case, we are taking all the available flow, we would have loved
            // to get more, but just take what is available
            if (DEBUG) log(format("<- exit %d no remaining flow", vertex), depth);
            return availableFlow;
        }
    }

    static void log(String msg, int depth) {
        StringBuilder prefix = new StringBuilder();
        //noinspection StringRepeatCanBeUsed
        for (int i = 0; i < depth; i++) {
            prefix.append("  ");
        }
        System.out.println(prefix.toString() + msg);
    }
}
