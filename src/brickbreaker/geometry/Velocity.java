package brickbreaker.geometry;

// Velocity specifies the change in position on the `x` and the `y` axes.
/** Velocity class.
 *
 */
public class Velocity {
    private double dx;
    private double dy;
    private Point point;

    // constructor

    /** Constructor of velocity.
     *
     * @param dx change in x
     * @param dy change in y
     */
    public Velocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
        this.point = null;
    }

    /** Constructor of velocity.
     *
     * @param dx change in x
     * @param dy change in y
     * @param point
     */
    public Velocity(double dx, double dy, Point point) {
        this.dx = dx;
        this.dy = dy;
        this.point = point;
    }

    // Take a point with position (x,y) and return a new point
    // with position (x+dx, y+dy)

    /** Point after velocity.
     *
     * @param p starting point
     * @return point after the change in position was done
     */
    public Point applyToPoint(Point p) {
        return new Point(p.getX() + dx, p.getY() + dy);
    }

    /** First setter for velocity.
     *
     * @param v updated velocity
     */
    public void setVelocity(Velocity v) {

    }

    /** Second setter for velocity.
     *
     * @param dx change in x
     * @param dy change in y
     */
    public void setVelocity(double dx, double dy) {
        this.dx = dx;
        this.dy = dy;
    }

    /** Getter for velocity.
     *
     * @return the current velocity
     */
    public Velocity getVelocity() {
        return new Velocity(getDX(), getDY(), this.point);
    }

    /** Getter for dx of the velocity.
     *
     * @return dx of velocity
     */
    public double getDX() {
        return this.dx;
    }

    /** Getter for dy of the velocity.
     *
     * @return dy of velocity
     */
    public double getDY() {
        return this.dy;
    }

    /** Moving mechanics which we implement for points.
     *
     */
    public void moveOneStep() {
        this.point = this.getVelocity().applyToPoint(this.point);
    }

    /** Getter for point.
     *
     * @return Point of velocity
     */
    public Point getPoint() {
        return this.point;
    }

    /** Setter for point.
     *
     * @param p new point
     */
    public void setPoint(Point p) {
        this.point = p;
    }

    /** Converting from polaric form to algebric form.
     *
     * @param angle the direction
     * @param speed the size of speed
     * @return a new transformed velocity
     */
    public static Velocity fromAngleAndSpeed(double angle, double speed) {
        double angleInRadians = Math.toRadians(angle);
        double dx = speed * Math.cos(angleInRadians);
        double dy = speed * Math.sin(angleInRadians);
        return new Velocity(dx, dy);
    }
}