package brickbreaker.geometry;

import brickbreaker.util.Util;

/** Class of points and simple stuff about them. */
public class Point {
    private double x;
    private double y;

    /** constructor.
     *
     * @param x given x value
     * @param y given y value
     */
    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /** constructor. Allow us to copy points.
     *
     * @param point point going to be copied
     */
    public Point(Point point) {
        this.x = point.x;
        this.y = point.y;
    }

    /** distance -- return the distance of this point to the other point.
     *
     * @param other given point
     * @return distance between the points
     */
    public double distance(Point other) {
        return Math.sqrt(Math.pow(this.x - other.x, 2) + Math.pow(this.y - other.y, 2));
    }

    /** equals -- return true is the points are equal, false otherwise - need to add threshold.
     *
     * @param other given point
     * @return true if they are equal
     */
    public boolean equals(Point other) {
        return this.x - other.x < Util.threshold() && this.y - other.y < Util.threshold();
    }

    /** Return the x value of this point.
     *
     * @return x of point
     */
    public double getX() {
        return x;
    }

    /** Return the y value of this point.
     *
     * @return y of point
     */
    public double getY() {
        return y;
    }
}