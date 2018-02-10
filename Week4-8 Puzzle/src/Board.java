import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.StdOut;


public class Board {
  private int[][] blocks;
  private final int dimension;

  // construct a board from an n-by-n array of blocks
  // (where blocks[i][j] = block in row i, column j)
  public Board(int[][] blocks)  {
    if (blocks == null) {
      throw new IllegalArgumentException();
    }

    dimension = blocks.length;
    this.blocks = copy(blocks);
  }

  // board dimension n
  public int dimension() {
    return  dimension;
  }

  // number of blocks out of place
  public int hamming() {
    int hamming = 0;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if (blocks[i][j] != 0 && blocks[i][j] != i * dimension + j + 1) {
          hamming++;
        }
      }
    }
    return hamming;
  }

  // sum of Manhattan distances between blocks and goal
  public int manhattan() {
    int manhattan = 0;
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if (blocks[i][j] != 0 && blocks[i][j] != i * dimension + j + 1) {
          int rowDistance = Math.abs(i - (blocks[i][j] - 1) / dimension);
          int colDistance = Math.abs(j - (blocks[i][j] - 1) % dimension);
          manhattan += rowDistance + colDistance;
        }
      }
    }
    return manhattan;
  }

  // is this board the goal board?
  public boolean isGoal() {
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if (blocks[i][i] == 0) {
          continue;
        }
        if (blocks[i][j] != i * dimension + j + 1) {
          return false;
        }
      }
    }
    return true;
  }

  // a board that is obtained by exchanging any pair of blocks
  public Board twin() {
    if (blocks[0][0] != 0 && blocks[0][1] != 0) {
      return new Board(swap(0,0, 0,1));
    } else {
      return new Board(swap(1,0, 1,1));
    }
  }

  // does this board equal y?
  public boolean equals(Object y) {
    if (y == this) {
      return true;
    }
    if (y == null) {
      return false;
    }
    if (y.getClass() != this.getClass()) {
      return false;
    }
    Board that = (Board)y;
    if (this.dimension() != that.dimension()) {
      return false;
    }

    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if (this.blocks[i][i] != that.blocks[i][j]) {
          return false;
        }
      }
    }

    return true;
  }

  // all neighboring boards
  public Iterable<Board> neighbors() {
    Queue<Board> neighbors = new Queue<Board>();
    outerloop:
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        if (blocks[i][j] == 0) {
          if (i > 0) {
            neighbors.enqueue(new Board(swap(i, j, i - 1, j)));
          }
          if (i < dimension - 1) {
            neighbors.enqueue(new Board(swap(i, j, i + 1, j)));
          }
          if (j > 0) {
            neighbors.enqueue(new Board(swap(i, j, i, j - 1)));
          }
          if (j < dimension - 1) {
            neighbors.enqueue(new Board(swap(i, j, i, j + 1)));
          }
          break outerloop;
        }
      }
    }

    return neighbors;
  }

  // string representation of this board (in the output format specified below)
  public String toString() {
    StringBuilder s = new StringBuilder();
    s.append(dimension + "\n");
    for (int i = 0; i < dimension; i++) {
      for (int j = 0; j < dimension; j++) {
        s.append(String.format("%2d ", blocks[i][j]));
      }
      s.append("\n");
    }
    return s.toString();
  }

  // return a copy of blocks with (row1, col1) and (row2, col2) swapped
  private int[][] swap(int row1, int col1, int row2, int col2) {
    int[][] tempBlocks = copy(blocks);
    int tempValue = tempBlocks[row1][col1];
    tempBlocks[row1][col1] = tempBlocks[row2][col2];
    tempBlocks[row2][col2] = tempValue;
    return tempBlocks;
  }


  private int[][] copy(int[][] array) {
    int[][] copy = new int[array.length][array.length];
    for (int i = 0; i < array.length; i++) {
      for (int j = 0; j < array.length; j++) {
        copy[i][j] = array[i][j];
      }
    }

    return copy;
  }

  // unit tests
  public static void main(String[] args) {
    int[][] blocks = {{8, 1, 3}, {4, 2, 0}, {7, 6, 5}};
    Board a = new Board(blocks);
    StdOut.println(a.dimension());
    StdOut.println(a.hamming());
    StdOut.println(a.manhattan());
    StdOut.println(a);
    StdOut.println(a.twin());
    StdOut.println(a);
    for (Board board: a.neighbors()) {
      StdOut.println("neighbors:"+ board);
    }
  }
}
