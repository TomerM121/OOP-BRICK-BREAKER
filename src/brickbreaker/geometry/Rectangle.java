package brickbreaker.geometry;

import java.util.ArrayList;

/** Rectangle class. */
public class Rectangle {

    private final Point upperLeft;
    private final double width;
    private final double height;

    private final Point lowerRight;
    private final Point upperRight;
    private final Point lowerLeft;

    /** CONSTRUCTOR - Create a new rectangle with location and width/height.
     *
     * @param upperLeft
     * @param width
     * @param height
     */
    public Rectangle(Point upperLeft, double width, double height) {
        this.upperLeft = upperLeft;
        this.width = width;
        this.height = height;

        // Find lower right point
        this.lowerRight = new Point(upperLeft.getX() + width, upperLeft.getY() + height);
        this.upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        this.lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
    }


    /** CONSTRUCTOR - Create a new rectangle with upper and lower points of rectangle.
     *
     * @param upperLeft
     * @param lowerRight
     */
    public Rectangle(Point upperLeft, Point lowerRight) {
        this.upperLeft = upperLeft;
        this.lowerRight = lowerRight;

        // Calculate the width and height of the rectangle
        double width = Math.abs(upperLeft.getX() - lowerRight.getX());
        double height = Math.abs(upperLeft.getY() - lowerRight.getY());

        this.width = width;
        this.height = height;


        // Find other points
        this.upperRight = new Point(upperLeft.getX() + width, upperLeft.getY());
        this.lowerLeft = new Point(upperLeft.getX(), upperLeft.getY() + height);
    }


    /** METHOD - To check for all intersection points of a given line with the rectangle.
     *  with the specified line.
     *
     * @param line a given line to check intersection with
     * @return a (possibly empty) List of intersection points
     */
    public java.util.List<Point> intersectionPoints(Line line) {
        java.util.List<Point> points = new ArrayList<>();

        // Create all lines of rectangle
        Line l1 = new Line(upperLeft, upperRight);
        Line l2 = new Line(lowerRight, lowerLeft);
        Line l3 = new Line(lowerLeft, upperLeft);
        Line l4 = new Line(upperRight, lowerRight);

        Line[] rectangleLines = new Line[]{l1, l2, l3, l4};

        // add all intersection points
        for (int i = 0; i < rectangleLines.length; i++) {
            Point p = line.intersectionWith(rectangleLines[i]);

            if (p != null) {
                points.add(p);
            }
        }

        return points;
    }



    /** GETTER - getter for width.
     *
     * @return width of the rectangle
     */
    public double getWidth() {
        return width;
    }


    /** GETTER - getter for height.
     *
     * @return height of the rectangle
     */
    public double getHeight() {
        return height;
    }


    /** GETTER - getter for the upper-left point of the rectangle.
     *
     * @return upper left point of the rectangle
     */
    public Point getUpperLeft() {
        return new Point(upperLeft.getX(), upperLeft.getY());
    }

    /** GETTER - getter for the lower-left point of the rectangle.
     *
     * @return upper left point of the rectangle
     */
    public Point getLowerLeft() {
        return new Point(lowerLeft.getX(), lowerLeft.getY());
    }

    /** GETTER - getter for the upper-right point of the rectangle.
     *
     * @return upper left point of the rectangle
     */
    public Point getUpperRight() {
        return new Point(upperRight.getX(), upperRight.getY());
    }

    /** GETTER - getter for the lower-right point of the rectangle.
     *
     * @return upper left point of the rectangle
     */
    public Point getLowerRight() {
        return new Point(lowerRight.getX(), lowerRight.getY());
    }

}