/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.StdOut;

public class RootDetectDirectedPath {
    private Digraph graph;
    private boolean rooted = false;

    public RootDetectDirectedPath(Digraph graph) {
        if (graph == null || graph.V() == 0) {
            throw new IllegalArgumentException("invalid graph");
        }
        this.graph = graph;
        rootDetected();
    }

    public boolean isRooted() {
        return rooted;
    }

    private void rootDetected() {
        for (int i = 0; i < graph.V(); i++) {
            if (graph.outdegree(i) == 0) {
                StdOut.println("zero indegree: " + i);
                DepthFirstDirectedPaths depthFirstDirectedPaths = new DepthFirstDirectedPaths(graph, i);
                for (int j = 0; j < graph.V(); j++) {
                    if (!depthFirstDirectedPaths.hasPathTo(j)) {
                        rooted = false;
                        return;
                    }
                }
            }
        }
        rooted = true;
    }

    private static void rootTest() {
        Digraph graph = new Digraph(3);
        RootDetectDirectedPath rootDetect = new RootDetectDirectedPath(graph);
        assert !rootDetect.rooted : "root detected fail";
    }

    private static void rootTest1() {
        Digraph graph = new Digraph(3);
        graph.addEdge(0, 1);
        graph.addEdge(0, 2);
        RootDetectDirectedPath rootDetect = new RootDetectDirectedPath(graph);
        assert rootDetect.rooted : "root detected fail";
    }

    private static void rootTest2() {
        Digraph graph = new Digraph(3);
        graph.addEdge(0, 1);
        RootDetectDirectedPath rootDetect = new RootDetectDirectedPath(graph);
        assert !rootDetect.rooted : "root detected fail";
    }

    public static void main(String[] args) {
        rootTest();
        rootTest1();
        rootTest2();
    }
}
