/* *****************************************************************************
 *  Name:
 *  Date:
 *  Description:
 **************************************************************************** */

import edu.princeton.cs.algs4.Point2D;
import edu.princeton.cs.algs4.Queue;
import edu.princeton.cs.algs4.RectHV;
import edu.princeton.cs.algs4.StdDraw;

public class KdTree {
    private static class Node {
        private Point2D p;      // the point
        private RectHV rect;    // the axis-aligned rectangle corresponding to this node
        private Node lb;        // the left/bottom subtree
        private Node rt;        // the right/top subtree
        private boolean isX;
    }

    private Node rootNode;
    private int pointSize;
    public KdTree() {
        rootNode = null;
        pointSize = 0;
    }

    public boolean isEmpty() {
        // StdOut.printf("IsEmpty: %b\n", root == null);
        return rootNode == null;
    }

    public int size() {
        // StdOut.printf("size: %d\n", pointSize);
        return pointSize;
    }

    public void insert(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point is null");
        }
        boolean needRect = rootNode == null;
        rootNode = insertHelper(rootNode, p, true);
        if (needRect) {
            RectHV rectHV = new RectHV(0, 0, 1, 1);
            rootNode.rect = rectHV;
        }
    }

    public boolean contains(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("Point is null");
        }
        return containsHelper(rootNode, p);
    }

    public void draw() {
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius();
        StdDraw.rectangle(0.5, 0.5, 0.5, 0.5);
        drawHelper(rootNode);

        StdDraw.show();
    }

    public Iterable<Point2D> range(RectHV rect) {
        if (rect == null) {
            throw new IllegalArgumentException("rect is null");
        }
        Queue<Point2D> interPoints = new Queue<>();
        rangeSearchHelper(rootNode, rect, interPoints);
        return interPoints;
    }

    public Point2D nearest(Point2D p) {
        if (p == null) {
            throw new IllegalArgumentException("query p is null");
        }

        Node nearest = new Node();
        if (rootNode != null) {
            nearest.p = rootNode.p;
            // StdOut.printf("nearset first: %s\n", nearest.p);
        }

        nearsetHelper(rootNode, p, nearest);
        return nearest.p;
    }

    // V1
    // public void nearsetHelper(Node root, Point2D p, Node nearest, double nearestDis) {
    //     if (root == null) {
    //         return;
    //     }
    //
    //     if (root.p == p) {
    //         nearest.p = root.p;
    //         nearestDis = 0;
    //         return;
    //     }
    //
    //     if (!root.rect.contains(p)) {
    //         if (root.rect.distanceSquaredTo(p) > nearestDis) {
    //             return;
    //         }
    //     }
    //
    //     if (root.p.distanceSquaredTo(p) < nearestDis) {
    //         nearestDis = root.p.distanceSquaredTo(p);
    //         nearest.p = root.p;
    //     }
    //
    //     nearsetHelper(root.lb, p, nearest, nearestDis);
    //     nearsetHelper(root.rt, p, nearest, nearestDis);
    // }

    // V2
    private void nearsetHelper(Node root, Point2D p, Node nearest) {
        if (root == null) {
            return;
        }

        if (root.p.equals(p)) {
            nearest.p = root.p;
            return;
        }

        // V2.1
        // double currMin = nearest.p.distanceSquaredTo(p);
        // V2.2
        double currMin = nearest.p.distanceTo(p);
        if (!root.rect.contains(p)) {
            // V2.1
            // if (root.rect.distanceSquaredTo(p) > currMin) {

            // V2.3
            // if (root.rect.distanceSquaredTo(p) > currMin) {
            // V2.2
            // if (root.rect.distanceSquaredTo(p) >= currMin) {
            // V2.4
            if (root.rect.distanceTo(p) >= currMin) {
                // StdOut.printf("break check for sqrt point: %s\n", root.p);
                return;
            } else {
                // StdOut.printf("continue check for point: %s, rect=%s, currDis=%f, minPoint=%s, minDis=%f\n",
                //               root.p, root.rect, root.rect.distanceTo(p), nearest.p, currMin);
            }
        }

        if (root.p.distanceTo(p) < currMin) {
            nearest.p = root.p;
            // StdOut.printf("update nearset: %s\n", nearest.p);
        }

        if (needGoLeft(root, p)) {
            nearsetHelper(root.lb, p, nearest);
            nearsetHelper(root.rt, p, nearest);
        } else {
            nearsetHelper(root.rt, p, nearest);
            nearsetHelper(root.lb, p, nearest);
        }
    }

    private void rangeSearchHelper(Node root, RectHV rect, Queue<Point2D> interPoints) {
        if (root == null) {
            return;
        }

        // V2
        if (rect.contains(root.p)) {
        // V1
        // if (isPointIntersectWithRecv(root, rect)) {
            interPoints.enqueue(root.p);
        }

        if (root.isX) {
            if (root.p.y() <= rect.ymin()) {
                rangeSearchHelper(root.rt, rect, interPoints);
            // V2
            } else if (root.p.y() > rect.ymax()) {
            // V1
            // } else if (root.p.y() >= rect.ymax()) {
                rangeSearchHelper(root.lb, rect, interPoints);
            } else {
                rangeSearchHelper(root.rt, rect, interPoints);
                rangeSearchHelper(root.lb, rect, interPoints);
            }
        } else {
            if (root.p.x() <= rect.xmin()) {
                rangeSearchHelper(root.rt, rect, interPoints);
            // V2
            } else if (root.p.x() > rect.xmax()) {
            // V1
            // } else if (root.p.x() >= rect.xmax()) {
                rangeSearchHelper(root.lb, rect, interPoints);
            } else {
                rangeSearchHelper(root.rt, rect, interPoints);
                rangeSearchHelper(root.lb, rect, interPoints);
            }
        }
    }

    private Node insertHelper(Node root, Point2D p, boolean isX) {
        if (root == null) {
            Node newNode = new Node();
            newNode.p = p;
            newNode.isX = !isX;
            pointSize += 1;
            return newNode;
        }

        if (root.p.equals(p)) {
            return root;
        }

        if (needGoLeft(root, p)) {
            root.lb = insertHelper(root.lb, p, root.isX);
            calculateRecv(root, root.lb, true);
            return root;
        }

        root.rt = insertHelper(root.rt, p, root.isX);
        calculateRecv(root, root.rt, false);
        return root;
    }

    private void calculateRecv(Node root, Node child, boolean isLeft) {
        if (isLeft) {
            calculateRecvForLeft(root, child);
        } else {
            calculateRecvForRight(root, child);
        }
    }

    private void calculateRecvForLeft(Node root, Node child) {
        if (root.isX) {
            RectHV rectHV = new RectHV(root.rect.xmin(), root.rect.ymin(), root.rect.xmax(), root.p.y());
            child.rect = rectHV;
        } else {
            RectHV rectHV = new RectHV(root.rect.xmin(), root.rect.ymin(), root.p.x(), root.rect.ymax());
            child.rect = rectHV;
        }
        return;
    }

    private void calculateRecvForRight(Node root, Node child) {
        if (root.isX) {
            RectHV rectHV = new RectHV(root.rect.xmin(), root.p.y(), root.rect.xmax(), root.rect.ymax());
            child.rect = rectHV;
        } else {
            RectHV rectHV = new RectHV(root.p.x(), root.rect.ymin(), root.rect.xmax(), root.rect.ymax());
            child.rect = rectHV;
        }
    }

    private boolean containsHelper(Node root, Point2D p) {
        if (root == null) {
            return false;
        }

        if (root.p.equals(p)) {
            return true;
        }

        if (needGoLeft(root, p)) {
            return containsHelper(root.lb, p);
        }

        return containsHelper(root.rt, p);
    }

    private void drawHelper(Node root) {
        if (root == null) {
            return;
        }
        StdDraw.setPenColor(StdDraw.BLACK);
        StdDraw.setPenRadius(0.01);
        StdDraw.point(root.p.x(), root.p.y());


        if (root.isX) {
            StdDraw.setPenColor(StdDraw.BLUE);
            StdDraw.setPenRadius();
            StdDraw.line(root.rect.xmin(), root.p.y(), root.rect.xmax(), root.p.y());
        } else {
            StdDraw.setPenColor(StdDraw.RED);
            StdDraw.setPenRadius();
            StdDraw.line(root.p.x(), root.rect.ymin(), root.p.x(), root.rect.ymax());
        }
        drawHelper(root.lb);
        drawHelper(root.rt);
    }

    private boolean needGoLeft(Node root, Point2D p) {
        if (root.isX) {
            return needGoLeftForXOreitation(root, p);
        }
        return needGoLeftForYOreitation(root, p);
    }

    private boolean needGoLeftForXOreitation(Node root, Point2D p) {
        if (p.y() < root.p.y()) {
            return true;
        }
        return false;
    }

    private boolean needGoLeftForYOreitation(Node root, Point2D p) {
        if (p.x() < root.p.x()) {
            return true;
        }
        return false;
    }

    private static void testNearest() {
        KdTree pointSet = new KdTree();

        // Test case 1: Empty set
        Point2D queryPoint1 = new Point2D(0.5, 0.5);
        assert pointSet.nearest(queryPoint1) == null;

        // Test case 2: Insert a single point and check if it is the nearest point
        Point2D point1 = new Point2D(0.1, 0.1);
        pointSet.insert(point1);
        assert pointSet.nearest(queryPoint1) == point1;

        // Test case 3: Insert multiple points and check the nearest point
        Point2D point2 = new Point2D(0.6, 0.6);
        Point2D point3 = new Point2D(0.9, 0.9);
        Point2D point4 = new Point2D(0.5, 0.6);
        pointSet.insert(point2);
        pointSet.insert(point3);
        pointSet.insert(point4);

        assert pointSet.nearest(queryPoint1) == point4;
    }

    private static void testRange() {
        KdTree pointSet = new KdTree();

        // Test case 1: Empty set
        RectHV rect1 = new RectHV(0, 0, 1, 1);
        assert !pointSet.range(rect1).iterator().hasNext();

        // Test case 2: Insert a single point and check if it exists within the given range
        pointSet.insert(new Point2D(0.5, 0.5));
        assert pointSet.range(rect1).iterator().hasNext();

        // Test case 3: Insert multiple points and check if they exist within the given range
        pointSet.insert(new Point2D(0.1, 0.1));
        pointSet.insert(new Point2D(0.9, 0.9));
        pointSet.insert(new Point2D(0.3, 0.3));

        int count = 0;
        for (Point2D point : pointSet.range(rect1)) {
            count++;
        }
        assert count == 4;

        // Test case 4: Check for points outside the given range
        RectHV rect2 = new RectHV(0, 0, 0.2, 0.2);
        count = 0;
        for (Point2D point : pointSet.range(rect2)) {
            count++;
        }
        assert count == 1;
    }

    private static void testRangeCorner() {
        KdTree pointSet = new KdTree();

        // Test case 1: Empty set
        RectHV rect1 = new RectHV(0, 0, 1, 1);
        assert !pointSet.range(rect1).iterator().hasNext();

        // Test case 3: Insert multiple points and check if they exist within the given range
        pointSet.insert(new Point2D(0, 0.1));
        pointSet.insert(new Point2D(0.9, 0));
        pointSet.insert(new Point2D(0, 0));

        int count = 0;
        for (Point2D point : pointSet.range(rect1)) {
            count++;
        }
        assert count == 3;
    }

    private static void testBase() {
        KdTree tree = new KdTree();

        // Test case 1: Empty tree
        assert !tree.contains(new Point2D(0.1, 0.1));
        assert tree.isEmpty();
        assert tree.size() == 0;
        assert !tree.range(new RectHV(0, 0, 1, 1)).iterator().hasNext();

        // Test case 2: Insert a single point and check if it exists
        tree.insert(new Point2D(0.1, 0.1));
        assert !tree.isEmpty();
        assert tree.size() == 1;
        assert tree.contains(new Point2D(0.1, 0.1));
        // assert !tree.range(new RectHV(0,0, 1, 1)).;

        // Test case 3: Insert multiple points and check if they exist
        tree.insert(new Point2D(0.2, 0.2));
        tree.insert(new Point2D(0.3, 0.3));
        tree.insert(new Point2D(0.4, 0.4));
        assert !tree.isEmpty();
        assert tree.size() == 4;

        assert tree.contains(new Point2D(0.2, 0.2));
        assert tree.contains(new Point2D(0.3, 0.3));
        assert tree.contains(new Point2D(0.4, 0.4));

        // Test case 4: Check for points that do not exist
        assert !tree.contains(new Point2D(0.5, 0.5));
        assert !tree.contains(new Point2D(0.6, 0.6));

        tree.insert(new Point2D(0.3, 0.3));
        tree.insert(new Point2D(0.4, 0.4));
        assert !tree.isEmpty();
        assert tree.size() == 4;

        // tree.draw();
    }

    public static void main(String[] args) {
        testBase();
        testRange();
        testRangeCorner();
        testNearest();
    }
}
