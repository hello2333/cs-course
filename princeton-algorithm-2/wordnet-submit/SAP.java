/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.BreadthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;

import java.util.ArrayList;
import java.util.List;

public final class SAP {
    final private Digraph graph;

    // constructor takes a digraph (not necessarily a DAG)
    public SAP(Digraph G) {
        if (G == null) {
            throw new IllegalArgumentException("G is null");
        }
        graph = G;
    }

    // length of shortest ancestral path between v and w; -1 if no such path
    public int length(int v, int w) {
        validateVertex(v);
        validateVertex(w);
        List<Integer> arrayV = new ArrayList<>();
        arrayV.add(v);
        List<Integer> arrayW = new ArrayList<>();
        arrayW.add(w);
        return length(arrayV, arrayW);
    }

    // a common ancestor of v and w that participates in a shortest ancestral path; -1 if no such path
    public int ancestor(int v, int w) {
        validateVertex(v);
        validateVertex(w);

        List<Integer> arrayV = new ArrayList<>();
        arrayV.add(v);
        List<Integer> arrayW = new ArrayList<>();
        arrayW.add(w);
        return ancestor(arrayV, arrayW);
    }

    // length of shortest ancestral path between any vertex in v and any vertex in w; -1 if no such path
    public int length(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertexes(v);
        validateVertexes(w);

        if (!v.iterator().hasNext() || !w.iterator().hasNext()) {
            return -1;
        }

        BreadthFirstDirectedPaths pathsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(graph, w);

        int minLength = Integer.MAX_VALUE;
        for (int i = 0; i < graph.V(); i++) {
            if (!pathsV.hasPathTo(i)) {
                continue;
            }

            if (!pathsW.hasPathTo(i)) {
                continue;
            }

            int currLength = pathsV.distTo(i) + pathsW.distTo(i);
            if (currLength < minLength) {
                minLength = currLength;
            }
        }

        if (minLength < Integer.MAX_VALUE) {
            return minLength;
        }

        // System.out.println("length=" + minLength);
        return -1;
    }

    // a common ancestor that participates in shortest ancestral path; -1 if no such path
    public int ancestor(Iterable<Integer> v, Iterable<Integer> w) {
        validateVertexes(v);
        validateVertexes(w);

        if (!v.iterator().hasNext() || !w.iterator().hasNext()) {
            return -1;
        }

        BreadthFirstDirectedPaths pathsV = new BreadthFirstDirectedPaths(graph, v);
        BreadthFirstDirectedPaths pathsW = new BreadthFirstDirectedPaths(graph, w);

        int minLength = Integer.MAX_VALUE;
        int ancestral = -1;
        for (int i = 0; i < graph.V(); i++) {
            if (!pathsV.hasPathTo(i)) {
                continue;
            }

            if (!pathsW.hasPathTo(i)) {
                continue;
            }

            int currLength = pathsV.distTo(i) + pathsW.distTo(i);
            if (currLength < minLength) {
                minLength = currLength;
                ancestral = i;
            }
        }

        // System.out.println("ancestor=" + ancestral);
        // System.out.println(graph);
        return ancestral;
    }

    private void validateVertex(int v) {
        if (v >= graph.V() || v < 0) {
            throw new IllegalArgumentException("invalid v");
        }
    }

    private void validateVertexes(Iterable<Integer> v) {
        if (v == null) {
            throw new IllegalArgumentException("invalid v");
        }
    }

    public static void main(String[] args) {
        // testByCase();
        // TestByFile(args);
    }

    // private static void testByFile(String[] args) {
    //     In in = new In(args[0]);
    //     Digraph G = new Digraph(in);
    //     SAP sap = new SAP(G);
    //     while (!StdIn.isEmpty()) {
    //         int v = StdIn.readInt();
    //         int w = StdIn.readInt();
    //         int length   = sap.length(v, w);
    //         int ancestor = sap.ancestor(v, w);
    //         StdOut.printf("length = %d, ancestor = %d\n", length, ancestor);
    //     }
    // }

    // private static void testByCase() {
    //     Digraph G = new Digraph(8);
    //     G.addEdge(0, 1);
    //     G.addEdge(1, 2);
    //     G.addEdge(0, 3);
    //     G.addEdge(3, 4);
    //     G.addEdge(0, 4);
    //     G.addEdge(1, 5);
    //     G.addEdge(6, 7);
    //
    //     SAP sap = new SAP(G);
    //
    //     // Test case 1: length between 0 and 5
    //     int length = sap.length(0, 1);
    //     assert length == 1 : "Test case 1 failed";
    //
    //     // Test case 2: ancestor between 0 and 5
    //     int ancestor = sap.ancestor(0, 1);
    //     assert ancestor == 1 : "Test case 2 failed";
    //
    //     System.out.println("All test cases passed!");
    // }
}
