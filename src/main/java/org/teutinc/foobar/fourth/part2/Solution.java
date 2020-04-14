package org.teutinc.foobar.fourth.part2;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import static java.lang.String.format;

/*
Running with Bunnies
====================

You and your rescued bunny prisoners need to get out of this collapsing death trap of a space station - and fast! Unfortunately, some of the bunnies have been weakened by their long
imprisonment and can't run very fast. Their friends are trying to help them, but this escape would go a lot faster if you also pitched in. The defensive bulkhead doors have begun to
close, and if you don't make it through in time, you'll be trapped! You need to grab as many bunnies as you can and get through the bulkheads before they close.

The time it takes to move from your starting point to all of the bunnies and to the bulkhead will be given to you in a square matrix of integers. Each row will tell you the time it takes to
get to the start, first bunny, second bunny, ..., last bunny, and the bulkhead in that order. The order of the rows follows the same pattern (start, each bunny, bulkhead). The bunnies can
jump into your arms, so picking them up is instantaneous, and arriving at the bulkhead at the same time as it seals still allows for a successful, if dramatic, escape. (Don't worry, any
bunnies you don't pick up will be able to escape with you since they no longer have to carry the ones you did pick up.) You can revisit different spots if you wish, and moving to the
bulkhead doesn't mean you have to immediately leave - you can move to and from the bulkhead to pick up additional bunnies if time permits.

In addition to spending time traveling between bunnies, some paths interact with the space station's security checkpoints and add time back to the clock. Adding time to the clock will
delay the closing of the bulkhead doors, and if the time goes back up to 0 or a positive number after the doors have already closed, it triggers the bulkhead to reopen. Therefore, it might be
possible to walk in a circle and keep gaining time: that is, each time a path is traversed, the same amount of time is used or added.

Write a function of the form solution(times, time_limit) to calculate the most bunnies you can pick up and which bunnies they are, while still escaping through the bulkhead before the doors
close for good. If there are multiple sets of bunnies of the same size, return the set of bunnies with the lowest prisoner IDs (as indexes) in sorted order. The bunnies are represented as a
sorted list by prisoner ID, with the first bunny being 0. There are at most 5 bunnies, and time_limit is a non-negative integer that is at most 999.

For instance, in the case of
[
  [0, 2, 2, 2, -1],  # 0 = Start
  [9, 0, 2, 2, -1],  # 1 = Bunny 0
  [9, 3, 0, 2, -1],  # 2 = Bunny 1
  [9, 3, 2, 0, -1],  # 3 = Bunny 2
  [9, 3, 2, 2,  0],  # 4 = Bulkhead
]
and a time limit of 1, the five inner array rows designate the starting point, bunny 0, bunny 1, bunny 2, and the bulkhead door exit respectively. You could take the path:

Start End Delta Time Status
    -   0     -    1 Bulkhead initially open
    0   4    -1    2
    4   2     2    0
    2   4    -1    1
    4   3     2   -1 Bulkhead closes
    3   4    -1    0 Bulkhead reopens; you and the bunnies exit

With this solution, you would pick up bunnies 1 and 2. This is the best combination for this space station hallway, so the answer is [1, 2].

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
Solution.solution({{0, 1, 1, 1, 1}, {1, 0, 1, 1, 1}, {1, 1, 0, 1, 1}, {1, 1, 1, 0, 1}, {1, 1, 1, 1, 0}}, 3)
Output:
    [0, 1]

Input:
Solution.solution({{0, 2, 2, 2, -1}, {9, 0, 2, 2, -1}, {9, 3, 0, 2, -1}, {9, 3, 2, 0, -1}, {9, 3, 2, 2, 0}}, 1)
Output:
    [1, 2]

-- Python cases --
Input:
solution.solution([[0, 2, 2, 2, -1], [9, 0, 2, 2, -1], [9, 3, 0, 2, -1], [9, 3, 2, 0, -1], [9, 3, 2, 2, 0]], 1)
Output:
    [1, 2]

Input:
solution.solution([[0, 1, 1, 1, 1], [1, 0, 1, 1, 1], [1, 1, 0, 1, 1], [1, 1, 1, 0, 1], [1, 1, 1, 1, 0]], 3)
Output:
    [0, 1]

Use verify [file] to test your solution and see how it does. When you are finished editing your code, use submit [file] to submit your answer. If your solution passes the test cases, it will
be removed from your home folder.
 */
public class Solution {
    static final boolean DEBUG = true;

    public static int[] solution(int[][] times, int timeLimit) {
        if (times.length < 3) {
            // we don't have any bunnies to save
            return new int[]{};
        }

        // if there is a negative cycle all bunnies can escape!
        if (hasNegativeCycle(times)) {
            return generateAllBunnies(times.length - 2);
        }

        // find the sum of all negative edges, to max the time for a path
        int maxTime = timeLimit + (-1 * sumNegativeEdges(times));

        // initialize a store where we will keep all best states for each vertex/bunnies.
        // using this will allow to cut branches using infinite loops, or going through
        // paths that have already been analyzed with a better solution
        // This variable will be a matrix of (vertex, bunnies), with bunnies being an int using bits
        // to know which bunnies have been visited. So we have (2^#bunnies - 1) possibilities.
        int[][] bestPaths = new int[times.length][];
        for (int i = 0; i < times.length; i++) {
            bestPaths[i] = new int[(int) Math.pow(2, times.length) - 1];
            Arrays.fill(bestPaths[i], Integer.MAX_VALUE);
        }

        Set<Integer> solutions = new HashSet<>();
        boolean optimumSolutionFound = visit(
            0,
            times,
            new int[]{},
            0,
            0,
            timeLimit,
            maxTime,
            bestPaths,
            solutions
        );

        if (optimumSolutionFound) {
            return generateAllBunnies(times.length - 2);
        }

        if (solutions.isEmpty()) {
            return new int[]{};
        }

        if (DEBUG) log(format(
            "solutions:\n%s",
            solutions.stream()
                     .sorted(Solution::comparePath)
                     .map(solution -> transformBunniesBitToArray(solution, times.length - 2))
                     .map(Arrays::toString)
                     .collect(Collectors.joining("\n"))
        ));

        // sort the solution, and get the first one, then
        // transform the solution into an array as it is stored in an int, with
        // bunnies being some bits
        return solutions.stream()
                        .sorted(Solution::comparePath)
                        .map(solution -> transformBunniesBitToArray(solution, times.length - 2))
                        .findFirst()
                        .orElseGet(() -> new int[]{});
    }

    private static int[] transformBunniesBitToArray(int visitedBunnies, int numberOfBunnies) {
        int[] bunniesArray = new int[numberOfVisitedBunnies(visitedBunnies)];
        int index = 0;
        for (int i = 0; i < numberOfBunnies; i++) {
            if ((visitedBunnies & 1 << i) > 0) {
                bunniesArray[index++] = i;
            }
        }
        return bunniesArray;
    }

    static boolean visit(int vertex,
                         int[][] paths,
                         int[] currentPath,
                         int currentPathTime,
                         int visitedBunnies,
                         int timeLimit,
                         int maxTime,
                         int[][] bestPaths,
                         Set<Integer> solutions) {

        if (bestPaths[vertex][visitedBunnies] > currentPathTime) {
            // we either find a first path, or a best path to reach this vertex with this set of bunnies
            bestPaths[vertex][visitedBunnies] = currentPathTime;
        } else {
            // we already went to this path, with the same bunnies, and we were having more time, so don't go
            // any further, there is a better solution than that
            if (DEBUG) log(format(
                "--/-- cut the branch from vertex %d, with bunnies %s, time %d, and path %s, as we found a better solution before",
                vertex,
                Integer.toBinaryString(visitedBunnies),
                currentPathTime,
                Arrays.toString(currentPath)
            ));
            return false;
        }

        if (vertex == paths.length - 1 && currentPathTime <= timeLimit) {
            // we are on the BulkHead and right on time
            // so add the current path as a solution
            addSolution(visitedBunnies, currentPath, solutions);

            if (areAllBunniesVisited(visitedBunnies, paths.length - 2)) {
                if (DEBUG) log("*** optimum solution found, all bunnies are saved \\o/");
                // don't go further, we have the best solution
                return true;
            }
        }

        // add the new vertex to the path
        currentPath = addToPath(vertex, currentPath);
        // maybe we were visiting a bunny
        visitedBunnies = visitBunny(vertex, paths.length, visitedBunnies);

        if (DEBUG) log(format(
            "~ path %s, current time %d (max %d), bunnies %s",
            Arrays.toString(currentPath),
            currentPathTime,
            maxTime,
            Integer.toBinaryString(visitedBunnies)
        ));

        // only loop over vertex - 1, we don't want to analyze (x, x)
        for (int i = 0; i < paths.length - 1; i++) {
            // we want to force the first path to be analyzed to be (S, 0, 1, 2, ..., BH),
            // so let's always try to go the vertex + 1 first
            int nextVertex = (i + vertex + 1) % paths.length;

            int weight = paths[vertex][nextVertex];
            if (currentPathTime + weight <= maxTime) {
                boolean optimumFound = visit(
                    nextVertex,
                    paths,
                    currentPath,
                    currentPathTime + weight,
                    visitedBunnies,
                    timeLimit,
                    // remove the negative time from the max weight, we will not use this bonus anymore...
                    weight > 0 ? maxTime : maxTime + weight,
                    bestPaths,
                    solutions
                );
                if (optimumFound) {
                    return true;
                }
            }
        }

        // default, we will only return true if the optimum solution is found
        return false;
    }

    private static void addSolution(int visitedBunnies, int[] currentPath, Set<Integer> solutions) {
        if (DEBUG) log(format("==> path found %s + -> BulkHead", Arrays.toString(currentPath)));
        solutions.add(visitedBunnies);
    }

    static int[] addToPath(int vertex, int[] currentPath) {
        int[] newPath = new int[currentPath.length + 1];
        System.arraycopy(currentPath, 0, newPath, 0, currentPath.length);
        newPath[currentPath.length] = vertex;
        return newPath;
    }

    // we stored the visited bunnies in an int, it will be used as a solution later
    // we are storing the bunnies like this:
    // 00001 => mean bunny 0 is visited.
    static int visitBunny(int vertex, int numberOfVertices, int visitedBunnies) {
        if (vertex > 0 && vertex < numberOfVertices - 1) {
            if (DEBUG) log(format("-> visit bunny %d", vertex -1));
            return visitedBunnies | 1 << (vertex - 1);
        }
        return visitedBunnies;
    }

    static boolean areAllBunniesVisited(int visitedBunnies, int numberOfBunnies) {
        return visitedBunnies == Math.pow(2, numberOfBunnies) - 1;
    }

    static int sumNegativeEdges(int[][] paths) {
        int sum = 0;
        for (int i = 0; i < paths.length; i++) {
            for (int j = i+1; j < paths.length; j++) {
                if (paths[i][j] < 0) {
                    sum += paths[i][j];
                } else if (paths[j][i] < 0) {
                    sum += paths[j][i];
                }
            }
        }
        return sum;
    }

    static int[] generateAllBunnies(int numberOfBunnies) {
        return IntStream.range(0, numberOfBunnies).toArray();
    }

    static boolean hasNegativeCycle(int[][] paths) {
        // first check fi there is a negative edge, if we don't have any,
        // we can't have a negative circle
        boolean mightHaveNegativeCycle = false;
        for (int i = 0; i < paths.length; i++) {
            for (int j = i+1; j < paths.length; j++) {
                if (paths[i][j] < 0 || paths[j][i] < 0) {
                    mightHaveNegativeCycle = true;
                    break;
                }
            }
        }
        if (!mightHaveNegativeCycle) {
            return false;
        }

        // use bellman ford algorithm to find negative cycle
        return findNegativeCycleWithBellmanFord(paths);
    }

    static boolean findNegativeCycleWithBellmanFord(int[][] paths) {
        // Implementation from the wikipedia
        int[] distances = new int[paths.length];
        Arrays.fill(distances, Integer.MAX_VALUE);

        distances[0] = 0;
        for (int step = 1; step < paths.length; step++) {
            for (int i = 0; i < paths.length; i++) {
                for (int j = 0; j < paths.length; j++) {
                    final int weight = paths[i][j];
                    if (distances[i] + weight < distances[j]) {
                        distances[j] = distances[i] + weight;
                    }
                }
            }
        }

        // check for negative-weight cycles
        for (int i = 0; i < paths.length; i++) {
            for (int j = 0; j < paths.length; j++) {
                final int weight = paths[i][j];
                if (distances[i] + weight < distances[j]) {
                    return true;
                }
            }
        }
        return false;
    }

    static int comparePath(int path1, int path2) {
        int numberOfBunnies1 = numberOfVisitedBunnies(path1);
        int numberOfBunnies2 = numberOfVisitedBunnies(path2);
        if (numberOfBunnies1 > numberOfBunnies2) {
            return -1;
        } else if (numberOfBunnies1 < numberOfBunnies2) {
            return 1;
        } else {
            return Integer.compare(path1, path2); // 00001 should come before 00010
        }
    }

    static int numberOfVisitedBunnies(int visitedBunnies) {
        int count = 0;
        while (visitedBunnies > 0) {
            count += visitedBunnies & 1;
            visitedBunnies >>= 1;
        }
        return count;
    }

    static void log(String msg) {
        System.out.println(msg);
    }
}
