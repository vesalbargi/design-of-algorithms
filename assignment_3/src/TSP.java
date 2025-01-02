import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Stack;

/**
 *
 * Greedy Assignment II Template
 */

public class TSP {
    // any static fields you may need
    private static Integer[][] completeGraph = {
            { 0, 10, 15, 20 },
            { 10, 0, 35, 25 },
            { 15, 35, 0, 30 },
            { 20, 25, 30, 0 }
    };
    private static Integer[][] incompleteGraph = {
            { null, 10, 15, 20 },
            { 10, null, 35, 25 },
            { 15, 35, null, 30 },
            { 20, 25, 30, null }
    };

    public static void main(String[] args) {
        // Your test code
        System.out.println("====================");
        ArrayList<Integer> answerTSPGreedy = new ArrayList<>();
        int totalCostTSPGreedy = TSPGreedy(completeGraph, answerTSPGreedy);
        System.out.println("TSPGreedy Total cost: " + totalCostTSPGreedy);
        System.out.println("Path: " + answerTSPGreedy);
        System.out.println("====================");
        ArrayList<Integer> answerTSPDFSRec = new ArrayList<>();
        int totalCostTSPDFSRec = TSPDFSRec(incompleteGraph, answerTSPDFSRec);
        System.out.println("TSPDFSRec Total cost: " + totalCostTSPDFSRec);
        System.out.println("Path: " + answerTSPDFSRec);
        System.out.println("====================");
        ArrayList<Integer> answerTSPDFSItr = new ArrayList<>();
        int totalCostTSPDFSItr = TSPDFSItr(incompleteGraph, answerTSPDFSItr);
        System.out.println("TSPDFSItr Total cost: " + totalCostTSPDFSItr);
        System.out.println("Path: " + answerTSPDFSItr);
        System.out.println("====================");
        ArrayList<Integer> answerTSPBlind1 = new ArrayList<>();
        int totalCostTSPBlind1 = TSPBlind1(incompleteGraph, answerTSPBlind1);
        System.out.println("TSPBlind1 Total cost: " + totalCostTSPBlind1);
        System.out.println("Path: " + answerTSPBlind1);
        System.out.println("====================");
        ArrayList<Integer> answerTSPBlind2 = new ArrayList<>();
        int totalCostTSPBlind2 = TSPBlind2(incompleteGraph, answerTSPBlind2);
        System.out.println("TSPBlind2 Total cost: " + totalCostTSPBlind2);
        System.out.println("Path: " + answerTSPBlind2);
        System.out.println("====================");
        ArrayList<Integer> answerTSPBlindDC = new ArrayList<>();
        int totalCostTSPBlindDC = TSPBlindDC(incompleteGraph, answerTSPBlindDC);
        System.out.println("TSPBlindDC Total cost: " + totalCostTSPBlindDC);
        System.out.println("Path: " + answerTSPBlindDC);
        System.out.println("====================");
    }

    // Greedy TSP assuming the complete graph
    public static int TSPGreedy(Integer[][] graph, ArrayList<Integer> answer) {
        // This method can call another private method. return is the total TSP cost and
        // answer is the cycle path starting and finishing at node 0 (returned as call
        // by reference)
        int n = graph.length;
        if (n == 0 || graph[0].length != n) {
            return 0;
        }
        HashSet<Integer> visited = new HashSet<>();
        int currentNode = 0;
        visited.add(currentNode);
        answer.add(currentNode);
        int totalCost = 0;
        while (visited.size() < n) {
            int nearestNode = -1;
            int minCost = Integer.MAX_VALUE;
            for (int i = 0; i < n; i++) {
                if (!visited.contains(i) && graph[currentNode][i] < minCost) {
                    nearestNode = i;
                    minCost = graph[currentNode][i];
                }
            }
            if (nearestNode != -1) {
                totalCost += minCost;
                currentNode = nearestNode;
                visited.add(currentNode);
                answer.add(currentNode);
            }
        }
        totalCost += graph[currentNode][0];
        answer.add(0);
        return totalCost;
    }

    // DFS recursive TSP works with any graph ( Doesn't need the graph to be
    // complete)
    public static int TSPDFSRec(Integer[][] graph, ArrayList<Integer> answer) {
        // This method can call another private method. return is the total TSP cost and
        // answer is the cycle path starting and finishing at node 0 (returned as call
        // by reference)
        HashSet<Integer> visited = new HashSet<>();
        answer.clear();
        visited.add(0);
        answer.add(0);
        int totalCost = dfs(graph, 0, visited, answer, 0);
        if (totalCost != Integer.MAX_VALUE) {
            totalCost += graph[answer.get(answer.size() - 1)][0];
            answer.add(0);
        }
        return totalCost;
    }

    // DFS recursive TSP works with any graph ( Doesn't need the graph to be
    // complete)
    public static int TSPDFSItr(Integer[][] graph, ArrayList<Integer> answer) {
        // This method can call another private method. return is the total TSP cost and
        // answer is the cycle path starting and finishing at node 0 (returned as call
        // by reference)
        // Use your own stack
        int n = graph.length;
        Stack<State> stack = new Stack<>();
        boolean[] visited = new boolean[n];
        int minCost = Integer.MAX_VALUE;
        stack.push(new State(0, 0, new ArrayList<>(), visited));
        while (!stack.isEmpty()) {
            State current = stack.pop();
            int currentNode = current.node;
            int currentCost = current.cost;
            ArrayList<Integer> currentPath = current.path;
            boolean[] currentVisited = current.visited;
            currentPath.add(currentNode);
            currentVisited[currentNode] = true;
            if (currentPath.size() == n) {
                if (graph[currentNode][0] != null) {
                    int totalCost = currentCost + graph[currentNode][0];
                    if (totalCost < minCost) {
                        minCost = totalCost;
                        answer.clear();
                        answer.addAll(currentPath);
                        answer.add(0);
                    }
                }
            } else {
                for (int neighbor = 0; neighbor < n; neighbor++) {
                    if (!currentVisited[neighbor] && graph[currentNode][neighbor] != null) {
                        boolean[] newVisited = currentVisited.clone();
                        stack.push(new State(neighbor, currentCost + graph[currentNode][neighbor],
                                new ArrayList<>(currentPath), newVisited));
                    }
                }
            }
        }
        return minCost;
    }

    // BFS recursive TSP works with any graph ( Doesn't need the graph to be
    // complete)
    /*
     * public static int TSPBFS(Integer [][] graph, ArrayList<Integer> answer)
     * {
     * //This method can call another private method. return is the total TSP cost
     * and answer is the cycle path starting and finishing at node 0 (returned as
     * call by reference)
     * //Use your own queue
     * }
     */

    // Blind 1 searchs the full State Space
    public static int TSPBlind1(Integer[][] graph, ArrayList<Integer> answer) {
        // This method can call another private method. return is the total TSP cost and
        // answer is the cycle path starting and finishing at node 0 (returned as call
        // by reference)
        // See jobSchedulingBlind and jobSchedulingBlindOpt methods, and void
        // permutation(Integer[] sequence,int k) from the lecture codes
        Integer[] sequence = new Integer[graph.length - 1];
        for (int i = 1; i < graph.length; i++) {
            sequence[i - 1] = i;
        }
        int[] minCost = { Integer.MAX_VALUE };
        ArrayList<Integer> bestCycle = new ArrayList<>();
        permutation(sequence, 0, graph, minCost, bestCycle);
        answer.add(0);
        answer.addAll(bestCycle);
        answer.add(0);
        return minCost[0];
    }

    // Blind 2 searchs the full State Space
    public static int TSPBlind2(Integer[][] graph, ArrayList<Integer> answer) {
        // This method can call another private method. return is the total TSP cost and
        // answer is the cycle path starting and finishing at node 0 (returned as call
        // by reference)
        // See jobSchedulingBlind2 and ArrayList<ArrayList<Integer>>
        // permutation(ArrayList<Integer> sequence) from the lecture codes
        ArrayList<Integer> nodes = new ArrayList<>();
        for (int i = 1; i < graph.length; i++) {
            nodes.add(i);
        }
        ArrayList<ArrayList<Integer>> permutations = permutation(nodes);
        int minCost = Integer.MAX_VALUE;
        ArrayList<Integer> bestCycle = new ArrayList<>();
        for (ArrayList<Integer> cycle : permutations) {
            int cost = calculateCycleCost(graph, cycle);
            if (cost < minCost) {
                minCost = cost;
                bestCycle = new ArrayList<>(cycle);
            }
        }
        answer.add(0);
        answer.addAll(bestCycle);
        answer.add(0);
        return minCost;
    }

    // D&C Blind searchs the full State Space
    public static int TSPBlindDC(Integer[][] graph, ArrayList<Integer> answer) {
        // This method can call another private method. return is the total TSP cost and
        // answer is the cycle path starting and finishing at node 0 (returned as call
        // by reference)
        // See jobSchedulingBlindDQOpt, findMinWire, and findPermutation from the
        // lecture codes
        // This is based on the permutation D&C template in the newCode folder
        int n = graph.length;
        Integer[] currentAnswer = new Integer[n];
        boolean[] visited = new boolean[n];
        final int[] minCost = { Integer.MAX_VALUE };
        visited[0] = true;
        currentAnswer[0] = 0;
        tspSolve(graph, answer, currentAnswer, visited, 1, 0, 0, minCost);
        return minCost[0];
    }

    private static void tspSolve(Integer[][] graph, ArrayList<Integer> answer, Integer[] currentAnswer,
            boolean[] visited, int level, int currentCost, int currentCity, int[] minCost) {
        int n = graph.length;
        if (level == n) {
            int totalCost = currentCost + graph[currentCity][0];
            if (totalCost < minCost[0]) {
                minCost[0] = totalCost;
                answer.clear();
                for (int city : currentAnswer) {
                    answer.add(city);
                }
                answer.add(0);
            }
            return;
        }
        for (int nextCity = 0; nextCity < n; nextCity++) {
            if (!visited[nextCity]) {
                visited[nextCity] = true;
                currentAnswer[level] = nextCity;
                tspSolve(graph, answer, currentAnswer, visited, level + 1,
                        currentCost + graph[currentCity][nextCity], nextCity, minCost);
                visited[nextCity] = false;
            }
        }
    }

    private static ArrayList<ArrayList<Integer>> permutation(ArrayList<Integer> sequence) {
        if (sequence.size() == 0) {
            ArrayList<Integer> emptySeq = new ArrayList<Integer>();
            ArrayList<ArrayList<Integer>> solutions = new ArrayList<>();
            solutions.add(emptySeq);
            return solutions;
        }
        int data = sequence.remove(0);
        ArrayList<ArrayList<Integer>> solutionsN1 = permutation(sequence);
        ArrayList<ArrayList<Integer>> solutions = new ArrayList<ArrayList<Integer>>();
        for (ArrayList<Integer> solution : solutionsN1) {
            for (int i = 0; i <= solution.size(); i++) {
                ArrayList<Integer> newSequence = clone(solution);
                newSequence.add(i, data);
                solutions.add(newSequence);
            }
        }
        return solutions;
    }

    private static ArrayList<Integer> clone(ArrayList<Integer> sequence) {
        ArrayList<Integer> sequenceCopy = new ArrayList<Integer>();
        for (Integer item : sequence) {
            sequenceCopy.add(item);
        }
        return sequenceCopy;
    }

    private static int calculateCycleCost(Integer[][] graph, ArrayList<Integer> cycle) {
        int cost = 0;
        int prev = 0;
        for (int node : cycle) {
            cost += graph[prev][node];
            prev = node;
        }
        cost += graph[prev][0];
        return cost;
    }

    private static void permutation(Integer[] sequence, int k, Integer[][] graph, int[] minCost,
            ArrayList<Integer> bestCycle) {
        if (k == sequence.length) {
            int cost = calculateCycleCost(graph, sequence);
            if (cost < minCost[0]) {
                minCost[0] = cost;
                bestCycle.clear();
                bestCycle.addAll(Arrays.asList(sequence));
            }
            return;
        }
        for (int i = k; i < sequence.length; i++) {
            swap(sequence, k, i);
            permutation(sequence, k + 1, graph, minCost, bestCycle);
            swap(sequence, i, k);
        }
    }

    private static int calculateCycleCost(Integer[][] graph, Integer[] cycle) {
        int cost = 0;
        int prev = 0;
        for (int node : cycle) {
            cost += graph[prev][node];
            prev = node;
        }
        cost += graph[prev][0];
        return cost;
    }

    private static void swap(Integer[] sequence, int i, int j) {
        Integer temp = sequence[i];
        sequence[i] = sequence[j];
        sequence[j] = temp;
    }

    private static int dfs(Integer[][] graph, int currentNode, HashSet<Integer> visited, ArrayList<Integer> answer,
            int currentCost) {
        if (visited.size() == graph.length) {
            return currentCost;
        }

        int minCost = Integer.MAX_VALUE;
        int nextNode = -1;
        for (int neighbor = 0; neighbor < graph.length; neighbor++) {
            if (!visited.contains(neighbor) && graph[currentNode][neighbor] != null) {
                int newCost = currentCost + graph[currentNode][neighbor];
                if (newCost < minCost) {
                    minCost = newCost;
                    nextNode = neighbor;
                }
            }
        }
        if (nextNode != -1) {
            visited.add(nextNode);
            answer.add(nextNode);
            int recursiveCost = dfs(graph, nextNode, visited, answer, minCost);
            if (recursiveCost != Integer.MAX_VALUE) {
                return recursiveCost;
            }
            visited.remove(nextNode);
            answer.remove(answer.size() - 1);
        }
        return Integer.MAX_VALUE;
    }
}

class State {
    int node;
    int cost;
    ArrayList<Integer> path;
    boolean[] visited;

    State(int node, int cost, ArrayList<Integer> path, boolean[] visited) {
        this.node = node;
        this.cost = cost;
        this.path = path;
        this.visited = visited;
    }
}
