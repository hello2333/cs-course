/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdOut;

public class Outcast {
    final private WordNet wordNet;
    public Outcast(WordNet wordnet) {
        this.wordNet = wordnet;
    }

    public String outcast(String[] nouns) {
        int maxDis = 0;
        String maxNoun = "";
        for (int i = 0; i < nouns.length; i++) {
            int dis = distance(nouns, nouns[i]);
            if (dis > maxDis) {
                maxDis = dis;
                maxNoun = nouns[i];
            }
        }
        return maxNoun;
    }

    private int distance(String[] nouns, String noun) {
        int dis = 0;
        for (int i = 0; i < nouns.length; i++) {
            dis += wordNet.distance(nouns[i], noun);
        }
        return dis;
    }

    public static void main(String[] args) {
        WordNet wordnet = new WordNet(args[0], args[1]);
        Outcast outcast = new Outcast(wordnet);
        for (int t = 2; t < args.length; t++) {
            In in = new In(args[t]);
            String[] nouns = in.readAllStrings();
            StdOut.println(args[t] + ": " + outcast.outcast(nouns));
        }
    }
}
