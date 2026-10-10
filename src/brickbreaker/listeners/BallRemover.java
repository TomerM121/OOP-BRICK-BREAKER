package brickbreaker.listeners;

import brickbreaker.game.Game;
import brickbreaker.sprites.Ball;
import brickbreaker.sprites.Block;
import brickbreaker.util.Counter;

/** Ball remover class. A type of listener. */
public class BallRemover implements HitListener {

    private final Game game;
    private final Counter remainingBalls;

    /** Constructor for a ball remover listener.
     *
     * @param game game
     * @param remainingBlocks amount of blocks left
     */
    public BallRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBalls = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        hitter.removeFromGame(game);
        hitter.removeHitListener(this);
        remainingBalls.decrease(1);
    }
}
