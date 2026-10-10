package brickbreaker.listeners;

import brickbreaker.sprites.Ball;
import brickbreaker.sprites.Block;
import brickbreaker.util.Counter;

/** A type of listener responsible for the score. */
public class ScoreTrackingListener implements HitListener {
    private Counter currentScore;

    /** Construct a new object.
     *
     * @param scoreCounter Counter object
     */
    public ScoreTrackingListener(Counter scoreCounter) {
        this.currentScore = scoreCounter;
    }

    /** Method for taking the signal and changing the counter.
     *
     * @param beingHit the hit block
     * @param hitter the hitting ball
     */
    public void hitEvent(Block beingHit, Ball hitter) {
        if (hitter.getColor() != beingHit.getColor()) {
            currentScore.increase(5);
        }
    }
}