package brickbreaker.collision;

import brickbreaker.geometry.Point;

/** CollisionInfo class. */
public class CollisionInfo {

    private final Point collisionPoint;
    private final Collidable collisionObject;

    /** Create object CollisionInfo.
     *
     * @param p Point
     * @param c Collidable object
     */
    public CollisionInfo(Point p, Collidable c) {
        this.collisionPoint  = p;
        this.collisionObject = c;
    }

    /** Returns the exact point of impact (immutable Point preferred).
     *
     * @return Collision point of the object
     */
    public Point collisionPoint() {
        return collisionPoint;
    }

    /** Returns the same Collidable instance that lives in the game world.
     *
     * @return collision block of the object
     */
    public Collidable collisionObject() {
        return collisionObject;
    }
}
