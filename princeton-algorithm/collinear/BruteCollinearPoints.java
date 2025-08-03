/* *****************************************************************************
 *  Name:              Ada Lovelace
 *  Coursera User ID:  123456
 *  Last modified:     October 16, 1842
 **************************************************************************** */

import edu.princeton.cs.algs4.LinkedQueue;

import java.util.Arrays;
import java.util.Iterator;

public class BruteCollinearPoints {
    private LineSegment[] segments;
    private LinkedQueue<LineSegment> segmentQueue;
    // finds all line segments containing 4 points
    public BruteCollinearPoints(Point[] points) {
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
        return segments == null ? 0 : segments.length;
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
        for (int i = 0; i <= points.length - 4; i ++) {
            for (int j = i + 1; j <= points.length - 3; j ++) {
                for (int k = j + 1; k <= points.length - 2; k ++) {
                    for (int m = k + 1; m <= points.length - 1; m ++) {
                        Point p = points[i];
                        Point q = points[j];
                        Point r = points[k];
                        Point s = points[m];

                        double slopePQ = p.slopeTo(q);
                        double slopePR = p.slopeTo(r);
                        double slopePS = p.slopeTo(s);

                        if ((slopePQ == slopePS) && (slopePQ == slopePR)) {
                            LineSegment segment = new LineSegment(p, s);
                            segmentQueue.enqueue(segment);
                        }
                    }
                }
            }
        }

        segments = new LineSegment[segmentQueue.size()];
        Iterator<LineSegment> iterator = segmentQueue.iterator();
        int index = 0;
        while (iterator.hasNext()) {
            segments[index++] = iterator.next();
        }
    }

    public static void main(String[] args) {
        Point[] points = new Point[4];
        points[0] = new Point(1, 1);
        points[3] = new Point(2, 2);
        points[2] = new Point(3, 3);
        points[1] = new Point(4, 4);

        BruteCollinearPoints bcp = new BruteCollinearPoints(points);

        assert bcp.numberOfSegments() == 1;
        assert bcp.segments()[0].toString().equals("(1, 1) -> (4, 4)");
    }

}
