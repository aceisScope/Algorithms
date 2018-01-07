import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class BruteCollinearPoints {

  private ArrayList<LineSegment> lineSegments = new ArrayList<LineSegment>();

  /**
   * finds all line segments containing 4 points.
   * @param points read from the input file
   */
  public BruteCollinearPoints(Point[] points) {
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

    for (int p = 0; p < copies.length - 3; p++) {
      for (int q = p + 1; q < copies.length - 2; q++) {
        double slopeQtoP = copies[q].slopeTo(copies[p]);
        for (int s = q + 1; s < copies.length - 1; s++) {
          double slopeStoQ = copies[s].slopeTo(copies[q]);
          if (slopeQtoP != slopeStoQ) {
            continue;
          }
          for (int r = s + 1; r < copies.length; r++) {
            double slopeRtoS = copies[r].slopeTo(copies[s]);
            if (slopeRtoS == slopeQtoP) {
              lineSegments.add(new LineSegment(copies[p], copies[s]));
            }
          }
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
