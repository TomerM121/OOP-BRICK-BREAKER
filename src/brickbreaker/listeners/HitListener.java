package brickbreaker.listeners;

import brickbreaker.sprites.Ball;
import brickbreaker.sprites.Block;

/** The interface of the listener. */
public interface HitListener {

    /** This method is called whenever the beingHit object is hit
     * The hitter parameter is the Ball that's doing the hitting.
     *
     * @param beingHit the block that's hit
     * @param hitter the ball that's hitting
     */
    void hitEvent(Block beingHit, Ball hitter);
}
