package bearmaps.proj2ab;

import java.util.List;

public class KDTree implements PointSet {

    private Tree root;
    private final int DIMENSION = 2;

    private static class Tree {
        private Point val;
        private Tree leftChild;  // if dimensionSelector == 1, leftChild means downChild
        private Tree rightChild;
        private final int dimensionSelector; // 0: compare by x; 1: compare by y

        private int comparesTo(Tree other) {
            if (dimensionSelector == 0) {
                if (val.getX() > other.val.getX()) {
                    return 1;
                } else {
                    return -1;
                }
            }
            if (val.getY() > other.val.getY()) {
                return 1;
            } else {
                return -1;
            }
        }

        public Tree(Point p, int comparator) {
            val = p;
            this.dimensionSelector = comparator;
        }

        public void createChild(Tree t) {
            if (comparesTo(t) > 0) {
                if (leftChild == null) {
                    leftChild = t;
                }
                else {
                    leftChild.createChild(t);
                }
            }
            else {
                if (rightChild == null) {
                    rightChild = t;
                }
                else {
                    rightChild.createChild(t);
                }
            }
        }

        public Tree getLeftChild() {
            return leftChild;
        }

        public Tree getRightChild() {
            return rightChild;
        }

        private double bestCaseForBadSide(Point goal) {
            double[] coordinates = {val.getX(), val.getY()};
            double[] goalXY = {goal.getX(), goal.getY()};
            double[] potentialNearestXY = new double[2];
            potentialNearestXY[dimensionSelector] = coordinates[dimensionSelector];
            potentialNearestXY[(dimensionSelector + 1) % 2] = goalXY[(dimensionSelector + 1) % 2];
            Point potentialNearest = new Point(potentialNearestXY[0], potentialNearestXY[1]);
            return Point.distance(goal, potentialNearest);
        }
    }


    public KDTree(List<Point> points) {
        int dimensionSelect = 0;
        root = new Tree(points.get(0), dimensionSelect);
        for (int i = 1; i < points.size(); i++) {
            dimensionSelect = (dimensionSelect + 1) % DIMENSION;
            Tree next = new Tree(points.get(i), dimensionSelect);
            root.createChild(next);
        }
    }

    private Tree nearestHelper(Tree t, Point goal, Tree best) {
        if (t == null) {
            return best;
        }
        if (Point.distance(t.val, goal) < Point.distance(goal, best.val)) {
            best = t;
        }
        Tree temp = new Tree(goal, 0);
        Tree goodSide;
        Tree badSide;
        if (t.comparesTo(temp) > 0) {
            goodSide = t.getLeftChild();
            badSide = t.getRightChild();
        }
        else {
            goodSide = t.getRightChild();
            badSide = t.getLeftChild();
        }
        best = nearestHelper(goodSide, goal, best);
        if (Point.distance(best.val, goal) > t.bestCaseForBadSide(goal)) {
            best = nearestHelper(badSide, goal, best);
        }
        return best;

    }


    public Point nearest(double x, double y) {
        Point goal = new Point(x, y);
        return nearestHelper(root, goal, root).val;
    }
}
