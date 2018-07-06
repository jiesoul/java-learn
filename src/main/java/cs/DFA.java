package cs;

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.ST;
import edu.princeton.cs.algs4.StdIn;
import edu.princeton.cs.algs4.StdOut;

public class DFA {

    private String[] action;
    private ST<Character, Integer>[] next;

    public DFA(String filename) {
        In in = new In(filename);
        int n = in.readInt();
        String alphabet = in.readString();
        action = new String[n];
        for (int state = 0; state < n; state++) {
            action[state] = in.readString();
            next[state] = new ST<>();
            for (int i=0; i < alphabet.length(); i++) {
                next[state].put(alphabet.charAt(i), in.readInt());
            }
        }
    }

    public String simulate(String input) {
        int state = 0;
        for (int i = 0; i < input.length(); i++) {
            state = next[state].get(input.charAt(i));
        }
        return action[state];
    }

    public static void main(String[] args) {
        DFA dfa = new DFA(args[0]);
        while (StdIn.hasNextLine()) {
            String line = StdIn.readLine();
            StdOut.println(dfa.simulate(line));
        }
    }

}
