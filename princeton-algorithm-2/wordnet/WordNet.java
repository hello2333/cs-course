/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.DepthFirstDirectedPaths;
import edu.princeton.cs.algs4.Digraph;
import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WordNet {
    private final Map<String, List<Integer>> nounsMap;
    private final Map<Integer, String> synsetsMap;
    private final Digraph graph;
    private final SAP sap;

    // constructor takes the name of the two input files
    public WordNet(String synsets, String hypernyms) {
        nounsMap = new HashMap<>();
        synsetsMap = new HashMap<>();

        In synsetsFile = new In(synsets);
        String line = synsetsFile.readLine();
        while (line != null) {
            parseSynset(line);
            line = synsetsFile.readLine();
        }

        In hypernymsFile = new In(hypernyms);
        graph = new Digraph(synsetsMap.size());
        line = hypernymsFile.readLine();
        while (line != null) {
            parseHypernym(line);
            line = hypernymsFile.readLine();
        }

        if (hasCycle() || !isRooted()) {
            throw new IllegalArgumentException("graph is cycle or not rooted");
        }

        sap = new SAP(graph);
    }

    // returns all WordNet nouns
    public Iterable<String> nouns() {
        return nounsMap.keySet();
    }

    // is the word a WordNet noun?
    public boolean isNoun(String word) {
        if (word == null) {
            throw new IllegalArgumentException("empty word");
        }
        return nounsMap.containsKey(word);
    }

    // distance between nounA and nounB (defined below)
    public int distance(String nounA, String nounB) {
        validateNoun(nounA);
        validateNoun(nounB);

        Iterable<Integer> nounAIds = nounsMap.get(nounA);
        Iterable<Integer> nounBIds = nounsMap.get(nounB);
        return sap.length(nounAIds, nounBIds);
    }

    // a synset (second field of synsets.txt) that is the common ancestor of nounA and nounB
    // in a shortest ancestral path (defined below)
    public String sap(String nounA, String nounB) {
        Iterable<Integer> nounAIds = nounsMap.get(nounA);
        Iterable<Integer> nounBIds = nounsMap.get(nounB);
        int id = sap.ancestor(nounAIds, nounBIds);
        if (!synsetsMap.containsKey(id)) {
            throw new IllegalArgumentException("ancestor error");
        }
        return synsetsMap.get(id);
    }

    private void validateNoun(String noun) {
        if (noun == null) {
            throw new IllegalArgumentException("noun is empty");
        }

        if (!isNoun(noun)) {
            throw new IllegalArgumentException("noun is empty");
        }
    }

    private void parseSynset(String line) {
        String[] items = line.split(",");
        int id = Integer.parseInt(items[0]);
        String[] nouns = items[1].split(" ");
        for (int i = 0; i < nouns.length; i++) {
            if (nounsMap.get(nouns[i]) == null) {
                List<Integer> list = new ArrayList<>(Collections.singleton(id));
                nounsMap.put(nouns[i], list);
            } else {
                nounsMap.get(nouns[i]).add(id);
            }
        }

        synsetsMap.put(id, items[1]);
    }

    private void parseHypernym(String line) {
        String[] items = line.split(",");
        int id = Integer.parseInt(items[0]);
        for (int i = 1; i < items.length; i++) {
            int hypernym = Integer.parseInt(items[i]);
            graph.addEdge(id, hypernym);
        }
    }

    private boolean hasCycle() {
        CycleDetectDirectedPaths cycle = new CycleDetectDirectedPaths(graph);
        return cycle.hasCycle();
    }

    private boolean isRooted() {
        // find the vertex from graph whose outdegree is zero
        Digraph reverseGraph = graph.reverse();
        int rootVertext = -1;
        for (int i = 0; i < graph.V(); i ++) {
            if (graph.outdegree(i) == 0) {
                rootVertext = i;
                break;
            }
        }
        if (rootVertext == -1) {
            // StdOut.println("not find vertex with zero degree");
            return false;
        }

        // DFS vertex, check if it can reach all vertext
        DepthFirstDirectedPaths depthFirstDirectedPaths = new DepthFirstDirectedPaths(reverseGraph, rootVertext);
        for (int i = 0; i < reverseGraph.V(); i++) {
            if (!depthFirstDirectedPaths.hasPathTo(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        testSynsets6();
        // TestSynsets3InvalidCycle();
        testSynsets3InvalidTwoRoots();
    }

    private static void testSynsets6() {
        WordNet wordNet = new WordNet("synsets6.txt", "hypernyms6TwoAncestors.txt");
        for (String word : wordNet.nouns()) {
            StdOut.println(word + ": " + wordNet.nounsMap.get(word));
        }

        assert !wordNet.isNoun("hello") : "isNoun miss Error";
        assert wordNet.isNoun("c") : "isNoun hit Error";

        String sap = wordNet.sap("c", "d");
        assert sap.equals("d") : "sap error: " + sap;
        int length = wordNet.distance("c", "d");
        assert length == 1 : "distance error";

        assert wordNet.distance("c", "c") == 0;
    }

    // private static void testSynsets3InvalidCycle() {
    //     WordNet wordNet = new WordNet("synsets6.txt", "hypernyms3InvalidCycle.txt");
    //     for (String word : wordNet.nouns()) {
    //         StdOut.println(word + ": " + wordNet.nounsMap.get(word));
    //     }
    // }

    private static void testSynsets3InvalidTwoRoots() {
        WordNet wordNet = new WordNet("synsets6.txt", "hypernyms3InvalidTwoRoots.txt");
        for (String word : wordNet.nouns()) {
            StdOut.println(word + ": " + wordNet.nounsMap.get(word));
        }
    }
}
