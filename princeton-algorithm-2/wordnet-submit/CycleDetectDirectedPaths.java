/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Digraph;

public class CycleDetectDirectedPaths {
    private boolean isCycle = false;
    final private Digraph graph;
    private boolean[] onStack;
    private boolean[] marked;
    public CycleDetectDirectedPaths(Digraph graph) {
        onStack = new boolean[graph.V()];
        marked = new boolean[graph.V()];
        this.graph = graph;
        isCycle = false;

        cycleDetected();
    }

    public boolean hasCycle() {
        return isCycle;
    }

    private void cycleDetected() {
        int v = 0;
        for (; v < graph.V(); v++) {
            if (!marked[v] && hasCycleDetect(v)) {
                isCycle = true;
                break;
            }
        }
    }

    private boolean hasCycleDetect(int v) {
        onStack[v] = true;
        for (int w : graph.adj(v)) {
            if (!marked[w]) {
                marked[w] = true;
                if (hasCycleDetect(w)) {
                    // StdOut.println("graph has cycle. v:" + w);
                    return true;
                }
                continue;
            }

            if (onStack[w]) {
                return true;
            }
        }
        onStack[v] = false;
        return false;
    }

    private static void hasCycleTest() {
        Digraph graph = new Digraph(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(2, 1);

        CycleDetectDirectedPaths cycle = new CycleDetectDirectedPaths(graph);
        assert cycle.hasCycle() : "detect cycle fail";
    }

    private static void noCycleTest() {
        Digraph graph = new Digraph(3);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(0, 2);

        CycleDetectDirectedPaths cycle = new CycleDetectDirectedPaths(graph);
        assert !cycle.hasCycle() : "detect cycle fail";
    }

    private static void hasCycleTest2() {
        Digraph graph = new Digraph(5);
        graph.addEdge(0, 1);
        graph.addEdge(1, 2);
        graph.addEdge(3, 4);
        graph.addEdge(4, 3);

        CycleDetectDirectedPaths cycle = new CycleDetectDirectedPaths(graph);
        assert cycle.hasCycle() : "detect cycle fail";
    }

    public static void main(String[] args) {
        hasCycleTest();
        hasCycleTest2();
        noCycleTest();
    }
}
