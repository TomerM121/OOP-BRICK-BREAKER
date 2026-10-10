package brickbreaker.collision;

import brickbreaker.geometry.Point;
import brickbreaker.geometry.Rectangle;
import brickbreaker.geometry.Velocity;
import brickbreaker.sprites.Ball;
import brickbreaker.sprites.Block;

/** Collidable interface. */
public interface Collidable {
    /** Return the "collision shape" of the object.
     *
     * @return Return the "collision shape" of the object.
     */
    Rectangle getCollisionRectangle();

    // Notify the object that we collided with it at collisionPoint with
    // a given velocity.
    // The return is the new velocity expected after the hit (based on
    // the force the object inflicted on us).

    /** Hit method analogy.
     *
     * @param hitter hitting ball
     * @param collisionPoint point
     * @param currentVelocity velocity
     * @return new velocity
     */
    Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity);

    /** Return block, possibly null, of a collidable object.
     *
     * @return block of object
     */
    Block getBlock();
}