package brickbreaker.geometry;

import java.util.ArrayList;

/** Class regarding lines and doing things on them. */
public class Line {
    private Point start;
    private Point end;

    /** constructor.
     *
     * @param start starting point
     * @param end ending point
     */
    public Line(Point start, Point end) {
        this.start = start;
        this.end = end;
    }

    /** constructor.
     *
     * @param x1 starting x
     * @param y1 starting y
     * @param x2 starting x
     * @param y2 starting y
     */
    public Line(double x1, double y1, double x2, double y2) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
    }

    /** Calculates the length of the line.
     *
     * @return line length
     */
    public double length() {
        return start.distance(end);
    }

    /** Returns the middle point of the line.
     *
     * @return middle point
     */
    public Point middle() {
        return new Point((start.getX() + end.getX()) / 2, (start.getY() + end.getY()) / 2);
    }

    /** Returns the start point of the line.
     *
     * @return starting point
     */
    public Point start() {
        return start;
    }

    /** Returns the end point of the line.
     *
     * @return ending point
     */
    public Point end() {
        return end;
    }

    /** Check if it is a vertical line.
     *
     * @return true if vertical false otherwise
     */
    public boolean isVertical() {
        return start.getX() == end.getX();
    }

    /** Calculate the slope of the line.
     *
     * @return line's slope
     */
    public double calcSlope() {
        // If the line is not vertical, using a known formula we calculate the slope
        if (!isVertical()) {
            return (end.getY() - start.getY()) / (end.getX() - start.getX());
        }

        // The slope of a vertical line is not defined
        return Double.NaN;
    }

    /** Finds intersection with y of a line.
     *
     * @return intersection with y
     */
    public double calcYIntersect() {
        return start.getY() - calcSlope() * start.getX();
    }

    /** Returns the leftmost point in the line.
     *
     * @return leftmost point
     */
    public double minRange() {
        return Math.min(start.getX(), end.getX());
    }

    /** Returns the rightmost point in the line.
     *
     * @return rightmost point
     */
    public double maxRange() {
        return Math.max(start.getX(), end.getX());
    }

    /** Returns true if the lines intersect, false otherwise.
     *
     * @param other a given line
     * @return true if they intersect false otherwise
     */
    public boolean isIntersecting(Line other) {

        // Checking edge cases

        // Vertical Lines
        // Both are vertical
        if (isVertical() && other.isVertical()) {
            if (start.getX() != other.start.getX()) {
                return false;
            }
            // Both are points
            if (start.getY() == other.start.getY()) {
                return true;
            }

            // if lines "continue" each other
            if (start.getY() == other.end.getY() || other.start.getY() == end.getY()) {
                return true;
            }

            return false;
        }

        // Only one is vertical
        if (isVertical()) {
            double xIntersect = start().getX();
            double yIntersect = other.calcSlope() * xIntersect + other.calcYIntersect();

            return yIntersect >= Math.min(start().getY(), end().getY())
                    && yIntersect <= Math.max(start().getY(), end().getY())
                    && xIntersect >= other.minRange()
                    && xIntersect <= other.maxRange();
        }

        if (other.isVertical()) {
            double xIntersect = other.start().getX();
            double yIntersect = calcSlope() * xIntersect + calcYIntersect();

            return yIntersect >= Math.min(other.start().getY(), other.end().getY())
                    && yIntersect <= Math.max(other.start().getY(), other.end().getY())
                    && xIntersect >= minRange() && xIntersect <= maxRange();
        }

        // Parallel lines
        if (calcSlope() == other.calcSlope()) {
            // Parallel and no intersection
            if (calcSlope() * 1 + calcYIntersect() != other.calcSlope() * 1 + other.calcYIntersect()) {
                return false;
            }

            // Now we know they are described by the same equation
            // And the only we case we want to return true is if they "continue" each other
            // Meaning if one's ending is other's start or the opposite

            return maxRange() == other.minRange() || minRange() == other.maxRange();
        }

        // NOW, the only case we are left with is two lines that can be described by m1x+b1 and m2x+b2
        // meaning they have an intersection, but we just need to check if it is in range

        double xIntersect = (other.calcYIntersect() - calcYIntersect()) / (calcSlope() - other.calcSlope());
        double yIntersect = calcSlope() * xIntersect + calcYIntersect();

        return xIntersect >= minRange() && xIntersect <= maxRange() &&  xIntersect >= other.minRange()
                && xIntersect <= other.maxRange();
    }


    /** Returns true if this 2 lines intersect with this line, false otherwise.
     *
     * @param other1 first given line
     * @param other2 second given line
     * @return true if the 2 given lines intersect with the line we check on
     */
    public boolean isIntersecting(Line other1, Line other2) {
        return isIntersecting(other1) && isIntersecting(other2);
    }

    /** Returns the intersection point if the lines intersect,
     *  and null otherwise.
     *
     * @param other given line
     * @return point of intersection
     */
    public Point intersectionWith(Line other) {
        if ((isVertical() || other.isVertical()) && isIntersecting(other)) {
            // If both are vertical
            if (isVertical() && other.isVertical()) {
                // if ones tail's is other's start
                if (start().getY() == other.end().getY()) {
                    return new Point(start().getX(), start().getY());
                }

                if (end().getY() == other.start().getY()) {
                    return new Point(start().getX(), end().getY());
                }
            }

            if (isVertical()) {
                // This line is vertical, so its slope is undefined: take y from the other line
                double xIntersect = start().getX();
                return new Point(xIntersect, other.calcSlope() * xIntersect + other.calcYIntersect());
            }

            if (other.isVertical()) {
                double xIntersect = other.start().getX();
                return new Point(xIntersect, calcSlope() * xIntersect + calcYIntersect());
            }
        }

        if (isIntersecting(other)) {
            double xIntersect = (other.calcYIntersect() - calcYIntersect()) / (calcSlope() - other.calcSlope());
            double yIntersect = calcSlope() * xIntersect + calcYIntersect();

            return new Point(xIntersect, yIntersect);
        }
        return null;
    }


    /** equals -- return true if the lines are equal, false otherwise.
     *
     * @param other given line
     * @return true if lines are the same otherwise false
     */
    public boolean equals(Line other) {
        return start().equals(other.start()) && end().equals(other.end())
                || start().equals(other.end()) && end().equals(other.start());
    }

    /** Find the closest intersection point of the line with the rectangle.
     *
     * @param rect given rectangle
     * @return closest intersection point of line with rectangle
     */
    public Point closestIntersectionToStartOfLine(Rectangle rect) {
        ArrayList<Point> pts = new ArrayList<>(rect.intersectionPoints(this));

        if (pts.isEmpty()) {
            return null;
        }

        // We need minimum distance
        Point closest = pts.get(0);
        double minDist = start().distance(closest);

        // We check all one by one
        for (Point p : pts) {
            double d = start().distance(p);
            if (d < minDist) {
                minDist = d;
                closest = p;
            }
        }

        // and return
        return closest;
    }
}