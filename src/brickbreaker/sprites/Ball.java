package brickbreaker.sprites;

import brickbreaker.collision.Collidable;
import brickbreaker.collision.CollisionInfo;
import brickbreaker.collision.GameEnvironment;
import brickbreaker.game.Game;
import brickbreaker.geometry.Line;
import brickbreaker.geometry.Point;
import brickbreaker.geometry.Rectangle;
import brickbreaker.geometry.Velocity;
import brickbreaker.listeners.HitListener;
import brickbreaker.listeners.HitNotifier;
import brickbreaker.util.Util;

import biuoop.DrawSurface;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

/** Ball class. */
public class Ball implements Sprite, HitNotifier {

    private Point center;
    private final int radius;
    private Color color;
    private Velocity velocity = new Velocity(0, 0);
    private GameEnvironment env;
    private Paddle paddle;
    private final List<HitListener> listeners = new ArrayList<>();

    /** Constructor for ball object.
     *
     * @param center center point
     * @param radius radius of the ball
     * @param color color of the ball
     * @param initial the current listeners a ball has
     */
    public Ball(Point center, int radius, Color color, List<HitListener> initial) {
        this.center = center;
        this.radius = radius;
        this.color = color;
        velocity.setPoint(center);
        if (initial != null) {
            listeners.addAll(initial);
        }
    }

    /** Getter for x of center.
     *
     * @return x value of the center of the ball
     */
    public int getX() {
        return (int) this.center.getX();
    }

    /** Getter for y of center.
     *
     * @return y value of the center of the ball
     */
    public int getY() {
        return (int) this.center.getY();
    }

    /** Getter for the center.
     *
     * @return center
     */
    public Point getCenter() {
        return this.center;
    }

    /** Setter for the center.
     *
     * @param center new center
     */
    public void setCenter(Point center) {
        this.center = center;
    }

    /** Getter for the radius of the ball.
     *
     * @return radius of the ball
     */
    public int getSize() {
        return this.radius;
    }

    /** Getter for the color of the ball.
     *
     * @return color of the ball
     */
    public java.awt.Color getColor() {
        return this.color;
    }

    /** Setter for color.
     *
     * @param color color
     */
    public void setColor(Color color) {
        this.color = color;
    }

    /** Getter for velocity.
     *
     * @return velocity of ball
     */
    public Velocity getVelocity() {
        return this.velocity;
    }

    /** Setter for velocity.
     *
     * @param velocity given velocity
     */
    public void setVelocity(Velocity velocity) {
        this.velocity = velocity;
    }

    /** Setter for velocity.
     *
     * @param dx given x delta
     * @param dy given y delta
     */
    public void setVelocity(double dx, double dy) {
        this.velocity = new Velocity(dx, dy);
        this.velocity.setPoint(center);
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        d.fillCircle(getX(), getY(), radius);
    }

    /** Add a game environment to the game.
     *
     * @param e game environment
     */
    public void addEnv(GameEnvironment e) {
        this.env = e;
    }

    /** Moving a ball. */
    public void moveOneStep() {
        if (velocity.getDX() == 0 && velocity.getDY() == 0) {
            return;
        }

        Point next = velocity.applyToPoint(center);
        Line path = new Line(center, next);
        CollisionInfo info = env.getClosestCollision(path, radius);

        if (info == null) {
            center = next;
        } else {
            Point p = info.collisionPoint();
            Collidable c = info.collisionObject();
            // p is where the CENTER stops (the ball touches the object). The object itself expects the
            // point on its own edge, so we translate p onto the edge that was hit.
            setVelocity(c.hit(this, hitPointOnEdge(p, c.getCollisionRectangle()), velocity));

            double threshold = Util.threshold();
            center = new Point(
                    p.getX() + velocity.getDX() * threshold,
                    p.getY() + velocity.getDY() * threshold);
        }
        velocity.setPoint(center);

        if (insidePaddle()) {
            rescueBall();
        }
    }

    /** Translate the position of the ball's center at impact into a point on the edge of the rectangle that
     * was hit. The center stops on one face of the rectangle grown by the radius, and that face decides the
     * bounce axis: only the coordinate of the face that was hit is put exactly on the real edge. A hit on
     * the rounded-corner strip of a face is therefore a plain bounce off that face, and only a hit
     * exactly on the corner reverses both axes.
     *
     * @param p position of the center at impact
     * @param r the rectangle that was hit
     * @return point on the edge of r, used to tell the object which edge was hit
     */
    private static Point hitPointOnEdge(Point p, Rectangle r) {
        double left = r.getUpperLeft().getX();
        double right = r.getLowerRight().getX();
        double top = r.getUpperLeft().getY();
        double bottom = r.getLowerRight().getY();

        // How far outside the real rectangle the center is on each axis
        double outX = Math.max(Math.max(left - p.getX(), p.getX() - right), 0);
        double outY = Math.max(Math.max(top - p.getY(), p.getY() - bottom), 0);

        double tolerance = Util.threshold();
        double x = p.getX();
        double y = p.getY();

        if (Math.abs(outX - outY) < tolerance) {
            // Exact corner: both axes
            x = p.getX() < left ? left : right;
            y = p.getY() < top ? top : bottom;
        } else if (outY > outX) {
            // Top or bottom face
            y = p.getY() < top ? top : bottom;
        } else {
            // Left or right face
            x = p.getX() < left ? left : right;
        }

        return new Point(x, y);
    }

    @Override
    public void timePassed() {
        moveOneStep();
    }

    /** Determine if a ball is inside the paddle of the game.
     *
     * @return true if it is inside or false otherwise
     */
    private boolean insidePaddle() {
        if (paddle == null) {
            return false;
        }

        Rectangle rect = paddle.getCollisionRectangle();

        return center.getX() > rect.getLowerLeft().getX() && center.getX() < rect.getUpperRight().getX()
                && center.getY() > rect.getUpperRight().getY() && center.getY() < rect.getLowerLeft().getY();
    }

    /** This method manually moves the ball outside the ball in case it is stuck inside. */
    private void rescueBall() {
        if (paddle == null) {
            return;
        }

        // This is the line of the bottom of the paddle, I think it is more correct to use this to move the ball
        // because I can't think of a case in which the ball moves up and gets inside the paddle
        // therefore it is likely that if the paddle wasn't there to 'catch' it it would go down.
        Line bottom = new Line(paddle.getCollisionRectangle().getLowerLeft(),
                paddle.getCollisionRectangle().getLowerRight());

        // Get the collision point
        Point col = new Line(center, velocity.applyToPoint(center)).intersectionWith(bottom);

        // If there is a collision point
        if (col != null) {
            // Move the ball to that collision
            double threshold = Util.threshold();

            center = new Point(
                    col.getX() + velocity.getDX() * threshold,
                    col.getY() + velocity.getDY() * threshold);
        }
    }

    /** Assign a paddle to this ball.
     *
     * @param p paddle
     */
    public void assignPaddle(Paddle p) {
        this.paddle = p;
    }

    @Override
    public void removeHitListener(HitListener bl) {
        listeners.remove(bl);
    }

    @Override
    public void addHitListener(HitListener bl) {
        listeners.add(bl);
    }

    /** Remove this ball from the game.
     *
     * @param g the game
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
    }
}
