package brickbreaker.collision;

import brickbreaker.geometry.Line;
import brickbreaker.geometry.Point;
import brickbreaker.geometry.Rectangle;

import java.util.ArrayList;
import java.util.List;

/** Game Environment class. */
public class GameEnvironment {
    private final List<Collidable> gameObjects = new ArrayList<>();

    /** Adding a collidable object to the collection.
     *
     * @param c collidable object
     */
    public void addCollidable(Collidable c) {
        gameObjects.add(c);
    }

    /** Removing a collidable object to the collection.
     *
     * @param c collidable object.
     */
    public void removeCollidable(Collidable c) {
        gameObjects.remove(c);
    }

    /** Getter for the size of the collection.
     *
     * @return size of the collection
     */
    public int size() {
        return gameObjects.size();
    }

    /** Given trajectory of a ball, find the closest collision of it with a collidable object.
     *
     * @param trajectory trajectory of the route of the ball
     * @return CollisionInfo object, which stores the collidable and the point of collision
     */
    public CollisionInfo getClosestCollision(Line trajectory) {
        return getClosestCollision(trajectory, 0);
    }

    /** Find the closest collision of a ball of the given radius, whose center moves along the trajectory.
     * Every collidable is grown by the radius, so the collision point is where the CENTER of the ball
     * is when the edge of the ball first touches the collidable.
     *
     * @param trajectory path of the center of the ball
     * @param radius radius of the ball
     * @return CollisionInfo with the center position at the moment of impact, or null if there is none
     */
    public CollisionInfo getClosestCollision(Line trajectory, double radius) {
        CollisionInfo closest = null;
        double minDist = Double.POSITIVE_INFINITY;
        Point start = trajectory.start();

        for (Collidable c : gameObjects) {
            Rectangle grown = grow(c.getCollisionRectangle(), radius);

            // A ball that is already overlapping the object is let out instead of bouncing it again.
            if (strictlyInside(start, grown)) {
                continue;
            }

            Point intersection = trajectory.closestIntersectionToStartOfLine(grown);
            if (intersection == null) {
                continue;
            }

            double dist = start.distance(intersection);

            if (dist < minDist) {
                minDist = dist;
                closest = new CollisionInfo(intersection, c);
            }
        }

        return closest;
    }

    private static Rectangle grow(Rectangle r, double by) {
        Point ul = r.getUpperLeft();
        return new Rectangle(new Point(ul.getX() - by, ul.getY() - by),
                r.getWidth() + 2 * by, r.getHeight() + 2 * by);
    }

    private static boolean strictlyInside(Point p, Rectangle r) {
        return p.getX() > r.getUpperLeft().getX() && p.getX() < r.getLowerRight().getX()
                && p.getY() > r.getUpperLeft().getY() && p.getY() < r.getLowerRight().getY();
    }
}
