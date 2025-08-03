/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.In;
import edu.princeton.cs.algs4.LinkedQueue;
import edu.princeton.cs.algs4.StdDraw;
import edu.princeton.cs.algs4.StdOut;

import java.util.Arrays;
import java.util.Iterator;

public class FastCollinearPoints {
    private LineSegment[] segments;
    private LinkedQueue<LineSegment> segmentQueue;
    // finds all line segments containing 4 points
    public FastCollinearPoints(Point[] points) {
        if (points == null) {
            throw new IllegalArgumentException("points is null");
        }
        if (points.length == 0) {
            throw new IllegalArgumentException("points is empty");
        }
        for (int i = 0; i < points.length; i ++) {
            if (points[i] == null) {
                throw new IllegalArgumentException("points is empty");
            }
        }
        Arrays.sort(points);
        for (int i = 1; i < points.length; i ++) {
            if (points[i].compareTo(points[i - 1]) == 0) {
                throw new IllegalArgumentException("points is duplicated");
            }
        }

        if (points.length >= 4) {
            FindCollinerPoints(points);
        }
    }
    // the number of line segments
    public int numberOfSegments() {
        return segments.length;
    }
    // the line segments
    public LineSegment[] segments() {
        LineSegment[] resultSegments = new LineSegment[segments.length];
        for (int i = 0; i < segments.length; i ++) {
            resultSegments[i] = segments[i];
        }
        return resultSegments;
    }

    private void FindCollinerPoints(Point[] points) {
        segmentQueue = new LinkedQueue<>();

        for (int i = 0; i < points.length; i ++) {
            Arrays.sort(points);
            Point base = points[i];
            Arrays.sort(points, base.slopeOrder());

            int pointSize = 1;
            for (int j = 2; j < points.length; j ++) {
                if (base.slopeTo(points[j]) == base.slopeTo(points[j-1])) {
                    pointSize ++;
                    continue;
                }


                if (pointSize >= 3) {
                    GenerateSegment(points, pointSize, j, base);
                }

                pointSize = 1;
            }

            if (pointSize >= 3) {
                GenerateSegment(points, pointSize, points.length, base);
            }
        }

        segments = new LineSegment[segmentQueue.size()];
        Iterator<LineSegment> iterator = segmentQueue.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            segments[index++] = iterator.next();
        }
    }

    private void GenerateSegment(Point[] points, int pointSize, int end, Point base) {
        Point firstPoint = points[end - pointSize];
        if (base.compareTo(firstPoint) > 0) {
            return;
        }
        LineSegment segment = new LineSegment(base, points[end - 1]);
        segmentQueue.enqueue(segment);
    }

    public static void main(String[] args) {
        testByFile(args);
    }

    private static void testBySimple() {
        Point[] points = new Point[4];
        points[0] = new Point(1, 1);
        points[3] = new Point(2, 2);
        points[2] = new Point(3, 3);
        points[1] = new Point(4, 4);

        FastCollinearPoints bcp = new FastCollinearPoints(points);
        System.out.println("size: " + bcp.numberOfSegments());

        assert bcp.numberOfSegments() == 1;
        assert bcp.segments()[0].toString().equals("(1, 1) -> (4, 4)");
    }

    private static void testByFile(String[] args) {
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
        FastCollinearPoints collinear = new FastCollinearPoints(points);
        for (LineSegment segment : collinear.segments()) {
            StdOut.println(segment);
            segment.draw();
        }
        StdDraw.show();
    }
}
