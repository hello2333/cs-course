/******************************************************************************
 *  Compilation:  javac Point.java
 *  Execution:    java Point
 *  Dependencies: none
 *  
 *  An immutable data type for points in the plane.
 *  For use on Coursera, Algorithms Part I programming assignment.
 *
 ******************************************************************************/

import edu.princeton.cs.algs4.StdDraw;

import java.util.Comparator;

public class Point implements Comparable<Point> {

    private final int x;     // x-coordinate of this point
    private final int y;     // y-coordinate of this point

    /**
     * Initializes a new point.
     *
     * @param  x the <em>x</em>-coordinate of the point
     * @param  y the <em>y</em>-coordinate of the point
     */
    public Point(int x, int y) {
        /* DO NOT MODIFY */
        this.x = x;
        this.y = y;
    }

    /**
     * Draws this point to standard draw.
     */
    public void draw() {
        /* DO NOT MODIFY */
        StdDraw.point(x, y);
    }

    /**
     * Draws the line segment between this point and the specified point
     * to standard draw.
     *
     * @param that the other point
     */
    public void drawTo(Point that) {
        /* DO NOT MODIFY */
        StdDraw.line(this.x, this.y, that.x, that.y);
    }

    /**
     * Returns the slope between this point and the specified point.
     * Formally, if the two points are (x0, y0) and (x1, y1), then the slope
     * is (y1 - y0) / (x1 - x0). For completeness, the slope is defined to be
     * +0.0 if the line segment connecting the two points is horizontal;
     * Double.POSITIVE_INFINITY if the line segment is vertical;
     * and Double.NEGATIVE_INFINITY if (x0, y0) and (x1, y1) are equal.
     *
     * @param  that the other point
     * @return the slope between this point and the specified point
     */
    public double slopeTo(Point that) {
        /* YOUR CODE HERE */
        if ((this.x == that.x) && (this.y == that.y)) {
            return Double.NEGATIVE_INFINITY;
        }

        if (this.x == that.x) {
            return Double.POSITIVE_INFINITY;
        }

        if (this.y == that.y) {
            return +0.0;
        }

        return ((double) that.y - this.y) / (that.x - this.x);
    }

    /**
     * Compares two points by y-coordinate, breaking ties by x-coordinate.
     * Formally, the invoking point (x0, y0) is less than the argument point
     * (x1, y1) if and only if either y0 < y1 or if y0 = y1 and x0 < x1.
     *
     * @param  that the other point
     * @return the value <tt>0</tt> if this point is equal to the argument
     *         point (x0 = x1 and y0 = y1);
     *         a negative integer if this point is less than the argument
     *         point; and a positive integer if this point is greater than the
     *         argument point
     */
    public int compareTo(Point that) {
        /* YOUR CODE HERE */
        if ((this.x == that.x) && (this.y == that.y)) {
            return 0;
        }

        if (this.y == that.y) {
            if (this.x < that.x) {
                return -1;
            }
            return 1;
        }

        if (this.y < that.y) {
            return -1;
        }
        return 1;
    }

    /**
     * Compares two points by the slope they make with this point.
     * The slope is defined as in the slopeTo() method.
     *
     * @return the Comparator that defines this ordering on points
     */
    public Comparator<Point> slopeOrder() {
        /* YOUR CODE HERE */
        return new SlopeOrder();
    }

    private class SlopeOrder implements Comparator<Point> {

        public int compare(Point o1, Point o2) {
            if (Point.this.slopeTo(o1) < Point.this.slopeTo(o2)) {
                return -1;
            }

            if (Point.this.slopeTo(o1) > Point.this.slopeTo(o2)) {
                return 1;
            }
            return 0;
        }
    }

    /**
     * Returns a string representation of this point.
     * This method is provide for debugging;
     * your program should not rely on the format of the string representation.
     *
     * @return a string representation of this point
     */
    public String toString() {
        /* DO NOT MODIFY */
        return "(" + x + ", " + y + ")";
    }

    /**
     * Unit tests the Point data type.
     */
    public static void main(String[] args) {
        /* YOUR CODE HERE */
        testForSlope();
        testForCompareTo();
        testForSlopeOrder();
        testForSpecialSlopeOrder();

        System.out.println("test done");
    }

    private static void testForSlope() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        double slope = p1.slopeTo(p2);
        if (slope != 1.0) {
            System.out.println("Test failed: expected slope 1.0, but got " + slope);
        }

        Point p3 = new Point(0, 0);
        Point p4 = new Point(0, 1);
        slope = p3.slopeTo(p4);
        if (slope != Double.POSITIVE_INFINITY) {
            System.out.println("Test failed: expected slope infinity, but got " + slope);
        }

        Point p5 = new Point(0, 0);
        Point p6 = new Point(0, 0);
        slope = p5.slopeTo(p6);
        if (slope != Double.NEGATIVE_INFINITY) {
            System.out.println("Test failed: expected slope negative infinity, but got " + slope);
        }
    }

    private static void testForCompareTo() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        int cmp = p1.compareTo(p2);
        if (cmp != -1) {
            System.out.println("Test failed: expected p1 to be less than p2, but got " + cmp);
        }

        Point p3 = new Point(0, 1);
        cmp = p1.compareTo(p3);
        if (cmp != -1) {
            System.out.println("Test failed: expected p1 to be less than p3, but got " + cmp);
        }

        Point p4 = new Point(0, 0);
        cmp = p1.compareTo(p4);
        if (cmp != 0) {
            System.out.println("Test failed: expected p1 to be equal to p4, but got " + cmp);
        }

        Point p5 = new Point(1, 0);
        cmp = p2.compareTo(p5);
        if (cmp != 1) {
            System.out.println("Test failed: expected p2 to be greater than p5, but got " + cmp);
        }
    }

    private static void testForSlopeOrder() {
        Point p1 = new Point(0, 0);
        Point p2 = new Point(1, 1);
        Point p3 = new Point(2, 2);
        Comparator<Point> slopeOrder = p1.slopeOrder();
        int cmp = slopeOrder.compare(p2, p3);
        if (cmp != 0) {
            System.out.println("Test failed: expected points to have equal slopes, but got " + cmp);
        }

        Point p4 = new Point(0, 1);
        cmp = slopeOrder.compare(p2, p4);
        if (cmp != -1) {
            System.out.println("Test failed: expected p2 to have smaller slope than p4, but got " + cmp);
        }

        Point p5 = new Point(2, 0);
        cmp = slopeOrder.compare(p3, p5);
        if (cmp != 1) {
            System.out.println("Test failed: expected p3 to have larger slope than p5, but got " + cmp);
        }
    }

    private static void testForSpecialSlopeOrder() {
        Point p1 = new Point(4, 5);
        Point horizon_p2 = new Point(1, 5);
        Point horizon_p3 = new Point(2, 5);
        Comparator<Point> slopeOrder = p1.slopeOrder();
        int cmp = slopeOrder.compare(horizon_p2, horizon_p3);
        if (cmp != 0) {
            System.out.println("Test failed: expected points to have equal slopes, but got " + cmp);
        }

        Point vertical_p2 = new Point(4, 7);
        Point vertical_p3 = new Point(4, 9);
        cmp = slopeOrder.compare(vertical_p2, vertical_p3);
        if (cmp != 0) {
            System.out.println("Test failed: expected points to have equal slopes, but got " + cmp);
        }

        cmp = slopeOrder.compare(horizon_p2, vertical_p3);
        System.out.println("horizon_p2 vs vertical_p3 is: " + cmp);
    }
}
