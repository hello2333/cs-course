/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

import java.util.Set;
import java.util.TreeSet;

public class PointSET {
    private Set<Point2D> pointsSet;
    public PointSET() {
        pointsSet = new TreeSet<>();
    }

    public boolean isEmpty() {
        return pointsSet.isEmpty();
    }

    public int size() {
        return pointsSet.size();
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point is null");
        }
        pointsSet.add(p);
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point is null");
        }
        return pointsSet.contains(p);
    }

    public void draw() {
        StdDraw.clear();
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        for (Point2D point2D:
             pointsSet) {
            point2D.draw();
        }
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect is null");
        }

        Set<Point2D> point2DIterable = new TreeSet<>();
        for (Point2D point2D:
                pointsSet) {
            if (rect.contains(point2D)) {
                point2DIterable.add(point2D);
            }
        }
        return point2DIterable;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("p is null");
        }
        double minDistance = 100;
        Point2D nearestPoint = null;
        for (Point2D point2D:
                pointsSet) {
            if (point2D.distanceSquaredTo(p) < minDistance) {
                minDistance = point2D.distanceSquaredTo(p);
                nearestPoint = point2D;
            }
        }
        return nearestPoint;
    }


    public static void main(String[] args) {

    }
}
