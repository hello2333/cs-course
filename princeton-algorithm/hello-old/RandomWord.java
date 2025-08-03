import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;
import edu.princeton.cs.algs4.StdRandom;

public class RandomWord {
    public static void main(String[] args) {
        String champion = "";
        String words = "";
        while (!StdIn.isEmpty()) {
            words = StdIn.readString();
            StdOut.println("words: " + words);
            if (StdRandom.bernoulli()) {
                champion = words;
            }
        }
        if (champion == "") {
            champion = words;
        }
        StdOut.println(champion);
    }
}
