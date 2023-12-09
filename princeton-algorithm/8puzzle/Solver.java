/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

import java.util.Comparator;

public class Solver {
    private class SearchNode {
        private Board board;
        private int moves;
        private SearchNode prev;

        SearchNode(Board board, int moves, SearchNode prev) {
            this.board = board;
            this.moves = moves;
            this.prev = prev;
        }

        int hammingPriority() {
            return board.hamming() + moves;
        }

        int mahattenPriority() {
            return board.manhattan() + moves;
        }
    }
    private SearchNode result = null;

    // find a solution to the initial board (using the A* algorithm)
    public Solver(Board initial) {
        MinPQ<SearchNode> frontier = new MinPQ<>(new Comparator<SearchNode>() {
            public int compare(SearchNode o1, SearchNode o2) {
                return o1.hammingPriority() - o2.hammingPriority();
            }
        });
        MinPQ<SearchNode> frontierTwin = new MinPQ<>(new Comparator<SearchNode>() {
            public int compare(SearchNode o1, SearchNode o2) {
                return o1.hammingPriority() - o2.hammingPriority();
            }
        });

        SearchNode searchNodeInital = new SearchNode(initial, 0, null);
        frontier.insert(searchNodeInital);

        SearchNode searchNodeTwin = new SearchNode(initial.twin(), 0, null);
        frontierTwin.insert(searchNodeTwin);

        int loop = 0;
        while (!frontier.isEmpty() && !frontierTwin.isEmpty()) {
            boolean isTwin = loop++ % 2 != 0;
            SearchNode node = isTwin ? frontierTwin.delMin() : frontier.delMin();

            if (node.board.isGoal()) {
                if (isTwin) {
                    return;
                }
                result = node;
                return;
            }

            for (Board neighBoard : node.board.neighbors()) {
                if (node.prev != null) {
                    if (neighBoard.equals(node.prev.board)) {
                        continue;
                    }
                }
                int newMoves = node.moves + 1;
                SearchNode neighSearch = new SearchNode(neighBoard, newMoves, node);

                if (isTwin) {
                    frontierTwin.insert(neighSearch);
                } else {
                    frontier.insert(neighSearch);
                }
            }
        }
    }


    // is the initial board solvable? (see below)
    public boolean isSolvable() {
        return result != null;
    }

    // min number of moves to solve initial board; -1 if unsolvable
    public int moves() {
        return isSolvable() ? result.moves : 0;
    }

    // sequence of boards in a shortest solution; null if unsolvable
    public Iterable<Board> solution() {
        if (result == null) {
            return null;
        }

        Stack<Board> path = new Stack<>();
        SearchNode curr = result;
        while (curr != null) {
            path.push(curr.board);
            curr = curr.prev;
        }
        return path;
    }
    public static void main(String[] args) {
        testSolvable();
        // testUnsolvable();
    }

    private static void testUnsolvable() {
        // Test unsolvable board
        int[][] unsolvableTiles = new int[][]{
                {1, 2, 3},
                {4, 6, 5},
                {7, 8, 0}
        };
        long startTime = System.currentTimeMillis();
        Board unsolvableBoard = new Board(unsolvableTiles);
        Solver unsolvableSolver = new Solver(unsolvableBoard);
        long endTime = System.currentTimeMillis();

        StdOut.println("====== Solution:" + " " + (endTime - startTime));
        if (unsolvableSolver.solution() != null) {
            for (Board item : unsolvableSolver.solution()) {
                StdOut.println(item);
            }
        }
        StdOut.println("====== Solution end");
        assert !unsolvableSolver.isSolvable() : "isSolvable failed for unsolvable board";

        assert unsolvableSolver.moves() == -1 : "moves failed for unsolvable board";

        assert unsolvableSolver.solution() == null : "solution failed for unsolvable board";

        System.out.println("All tests passed!");
    }

    private static void testSolvable() {
        // Test Solver constructor and isSolvable, moves, solution methods
        int[][] tiles = new int[][]{
                {1, 2, 3},
                {4, 5, 6},
                {7, 8, 0}
        };
        Board initialBoard = new Board(tiles);
        Solver solver = new Solver(initialBoard);

        assert solver.isSolvable() : "isSolvable failed";

        assert solver.moves() == 0 : "moves failed";

        Iterable<Board> solution = solver.solution();
        assert solution != null : "solution failed";
        Board solutionBoard = solution.iterator().next();
        assert initialBoard.equals(solutionBoard) : "solution failed";

        System.out.println("All tests passed!");
    }
}
