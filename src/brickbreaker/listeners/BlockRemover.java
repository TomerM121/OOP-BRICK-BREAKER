package brickbreaker.listeners;

import brickbreaker.game.Game;
import brickbreaker.sprites.Ball;
import brickbreaker.sprites.Block;
import brickbreaker.util.Counter;

/** Block remover class, type of listener. */
public class BlockRemover implements HitListener {

    private final Game game;
    private final Counter remainingBlocks;

    /** Constructor for a block remover listener.
     *
     * @param game game
     * @param remainingBlocks amount of blocks left
     */
    public BlockRemover(Game game, Counter remainingBlocks) {
        this.game = game;
        this.remainingBlocks = remainingBlocks;
    }

    @Override
    public void hitEvent(Block beingHit, Ball hitter) {
        beingHit.removeFromGame(game);
        beingHit.removeHitListener(this);
        remainingBlocks.decrease(1);
    }
}
