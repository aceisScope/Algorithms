import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.ArrayList;
import java.util.Arrays;

public class FastCollinearPoints {

  private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();

  /**
   * finds all line segments containing 4 points.
   * @param points read from the input file
   */
  public FastCollinearPoints(Point[] points) {
    if (points == null) {
      throw new IllegalArgumentException();
    }
    for (int i = 0; i < points.length; i++) {
      if (points[i] == null) {
        throw new IllegalArgumentException();
      }
    }

    Point[] copies = Arrays.copyOf(points, points.length);
    Arrays.sort(copies);

    for (int i = 0; i < points.length - 1; i++) {
      if (copies[i].compareTo(copies[i + 1]) == 0) {
        throw new IllegalArgumentException();
      }
    }

    for (int p = 0; p < copies.length - 1; p++) {
      Point origin = copies[p];
      Point[] rest = Arrays.copyOfRange(copies, p + 1, copies.length);
      Arrays.sort(rest, origin.slopeOrder());

      int restLength = rest.length;
      double[] slopes = new double[restLength];
      for (int i = 0; i < restLength; i++) {
        slopes[i] = origin.slopeTo(rest[i]);
      }
      Arrays.sort(slopes);

      for (int i = 0, count = 0; i < restLength - 1; i++) {
        if (slopes[i] == slopes[i + 1]) {
          count++;
        }
        if (count >= 2) {
          lineSegments.add(new LineSegment(origin, rest[i + 1]));
          break;
        }
      }
    }
  }

  // the number of line segments
  public int numberOfSegments() {
    return lineSegments.size();
  }

  // the line segments
  public LineSegment[] segments() {
    return lineSegments.toArray(new LineSegment[0]);
  }

  /**
   * Test.
   * @param args name of input file
   */
  public static void main(String[] args) {
    // read the n points from a file
    In in = new In(args[0]);
    int n = in.readInt();
    Point[] points = new Point[n];
    for (int i = 0; i < n; i++) {
      int x = in.readInt();
      int y = in.readInt();
      points[i] = new Point(x, y);
    }

    // draw the points
    StdDraw.enableDoubleBuffering();
    StdDraw.setXscale(0, 32768);
    StdDraw.setYscale(0, 32768);
    for (Point p : points) {
      p.draw();
    }
    StdDraw.show();

    // print and draw the line segments
    BruteCollinearPoints collinear = new BruteCollinearPoints(points);
    for (LineSegment segment : collinear.segments()) {
      StdOut.println(segment);
      segment.draw();
    }
    StdDraw.show();
    StdOut.println(collinear.numberOfSegments());
  }
}
