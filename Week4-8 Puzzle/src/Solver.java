import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.MinPQ;
import edu.princeton.cs.algs4.Stack;
import edu.princeton.cs.algs4.StdOut;

public class Solver {

  private boolean isSolvable;
  private int moves = 0;
  private SearchNode lastNode;

  private class SearchNode implements Comparable<SearchNode> {
    private Board board;
    private final int priority;
    private final int moves;
    private SearchNode parentNode;

    public SearchNode(Board board, SearchNode parentNode, int moves) {
      this.board = board;
      this.parentNode = parentNode;
      this.moves = moves;
      this.priority = board.manhattan() + moves;
    }

    @Override
    public int compareTo(SearchNode other) {
      return this.priority - other.priority;
    }
  }

  // find a solution to the initial board (using the A* algorithm)
  public Solver(Board initial) {
    if (initial == null) {
      throw new IllegalArgumentException();
    }
    MinPQ<SearchNode> minPQ = new MinPQ<>();
    MinPQ<SearchNode> twinMinPQ = new MinPQ<>();
    minPQ.insert(new SearchNode(initial, null, 0));
    twinMinPQ.insert(new SearchNode(initial.twin(), null, 0));

    while (true) {
      SearchNode node = minPQ.delMin();
      if (node.board.isGoal()) {
        lastNode = node;
        isSolvable = true;
        moves = node.moves;
        break;
      }

      SearchNode twinNode = twinMinPQ.delMin();
      if (twinNode.board.isGoal()) {
        isSolvable = false;
        break;
      }

      enqueueNodeNeighbors(minPQ, node);
      enqueueNodeNeighbors(twinMinPQ, twinNode);
    }
  }

  // is the initial board solvable?
  public boolean isSolvable() {
    return isSolvable;
  }

  // min number of moves to solve initial board; -1 if unsolvable
  public int moves() {
    return isSolvable ? moves : -1;
  }

  // sequence of boards in a shortest solution; null if unsolvable
  public Iterable<Board> solution() {
    if (!isSolvable) {
      return null;
    }
    Stack<Board> solution = new Stack<Board>();
    SearchNode node = lastNode;
    while (node != null) {
      solution.push(node.board);
      node = node.parentNode;
    }
    return solution;
  }

  private void enqueueNodeNeighbors(MinPQ<SearchNode> minPQ, SearchNode node) {
    for (Board b : node.board.neighbors()) {
      // critical optimization: when considering the neighbors of a search node,
      // don't enqueue a neighbor if its board is the same as the board of the predecessor search node.
      if (node.parentNode == null || !b.equals(node.parentNode.board)) {
        minPQ.insert(new SearchNode(b, node, node.moves + 1));
      }
    }
  }

  // solve a slider puzzle (given below)
  public static void main(String[] args) {
    // create initial board from file
    In in = new In(args[0]);
    int n = in.readInt();
    int[][] blocks = new int[n][n];
    for (int i = 0; i < n; i++)
      for (int j = 0; j < n; j++)
        blocks[i][j] = in.readInt();
    Board initial = new Board(blocks);

    // solve the puzzle
    Solver solver = new Solver(initial);

    // print solution to standard output
    if (!solver.isSolvable())
      StdOut.println("No solution possible");
    else {
      StdOut.println("Minimum number of moves = " + solver.moves());
      for (Board board : solver.solution())
        StdOut.println(board);
    }
  }
}
