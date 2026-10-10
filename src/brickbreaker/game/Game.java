package brickbreaker.game;

import brickbreaker.collision.Collidable;
import brickbreaker.collision.GameEnvironment;
import brickbreaker.geometry.Point;
import brickbreaker.geometry.Rectangle;
import brickbreaker.listeners.BallRemover;
import brickbreaker.listeners.BlockRemover;
import brickbreaker.listeners.ScoreTrackingListener;
import brickbreaker.sprites.Ball;
import brickbreaker.sprites.Block;
import brickbreaker.sprites.Paddle;
import brickbreaker.sprites.ScoreIndicator;
import brickbreaker.sprites.Sprite;
import brickbreaker.sprites.SpriteCollection;
import brickbreaker.util.Counter;
import brickbreaker.util.Util;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import biuoop.DrawSurface;
import biuoop.GUI;
import biuoop.Sleeper;

/** Class game manages the game. */
public class Game {

    private SpriteCollection sprites;
    private GameEnvironment environment;
    private GUI gui;
    private final List<Ball> balls = new ArrayList<>();
    private Counter score = new Counter(0);
    private Counter ballCount;
    private Counter blockCount = new Counter(0);

    /** Add a collidable object to the game environment.
     *
     * @param c collidable object
     */
    public void addCollidable(Collidable c) {
        environment.addCollidable(c);
    }

    /** Add a sprite to the sprite collection.
     *
     * @param s sprite object
     */
    public void addSprite(Sprite s) {
        sprites.addSprite(s);
    }

    /** Set up the game environment from zero to fully functioning. */
    public void initialize() {
        gui = new GUI("Arkanoid", 800, 600);
        sprites = new SpriteCollection();
        environment = new GameEnvironment();

        // Create balls
        Ball ball1 = new Ball(new Point(300, 300), 7, Color.WHITE, null);
        ball1.setVelocity(2, -4);
        addSprite(ball1);
        balls.add(ball1);

        Ball ball2 = new Ball(new Point(300, 300), 7, Color.WHITE, null);
        ball2.setVelocity(2, -5);
        addSprite(ball2);
        balls.add(ball2);

        Ball ball3 = new Ball(new Point(300, 300), 7, Color.WHITE, null);
        ball3.setVelocity(1.33, -4.76);
        addSprite(ball3);
        balls.add(ball3);

        // Create paddle
        Color paddleColor = new Color(224, 206, 10);
        Paddle paddle = new Paddle(new Block(new Rectangle(new Point(340, 560), 120, 20),
                paddleColor, null), paddleColor);
        paddle.readKey(gui);
        addSprite(paddle);
        addCollidable(paddle);

        // Connect paddle with balls
        ball1.assignPaddle(paddle);
        ball2.assignPaddle(paddle);

        // Create blocks
        Color orange = new Color(255, 128, 0);
        Color purple = new Color(181, 0, 255);

        this.blockCount.increase(50);
        createBlockRow(730, 180, 50, 150, orange, blockCount);
        createBlockRow(730, 230, 50, 180, Color.RED, blockCount);
        createBlockRow(730, 280, 50, 210, Color.BLUE, blockCount);
        createBlockRow(730, 330, 50, 240, purple, blockCount);
        createBlockRow(730, 380, 50, 270, Color.GREEN, blockCount);

        // Create borders (including death zone)
        createBorder();

        // Inserting blocks into the environment
        for (Ball b : balls) {
            b.addEnv(environment);
        }

        // Print score on screen sprite
        addSprite(new ScoreIndicator(score, new Rectangle(new Point(0, 0), 800, 20)));
    }

    /** Run method is responsible for tha game to run properly until there are no balls or blocks left. */
    public void run() {
        Sleeper sleeper = new Sleeper();
        int framesPerSecond = 60;
        int msPerFrame = 1000 / framesPerSecond;

        while (true) {
            // This part checks everytime if there are still any balls or blocks on screen
            if (ballCount.getValue() == 0 || blockCount.getValue() == 0) {
                if (blockCount.getValue() == 0) {
                    score.increase(100);
                    System.out.println("You Win! Your score is: " + score.getValue());
                } else {
                    System.out.println("You Lose! Your score is: " + score.getValue());
                }

                return;
            }

            // Used for timing
            long start = System.currentTimeMillis();

            // Create new draw surface
            DrawSurface d = gui.getDrawSurface();

            // Background
            d.setColor(new Color(231, 228, 238));
            d.fillRectangle(0, 0, 800, 600);

            // Draw all
            sprites.drawAllOn(d);

            // Update gui on screen
            gui.show(d);

            sprites.notifyAllTimePassed();

            // timing
            long used = System.currentTimeMillis() - start;
            long remaining = msPerFrame - used;
            if (remaining > 0) {
                sleeper.sleepFor(remaining);
            }
        }
    }

    /** Create regular blocks.
     *
     * @param startX the first x value in which the blocks are created at
     * @param endX the last x value in which the blocks are created at
     * @param w the distance between each block
     * @param y the y (constant value) in which blocks are created at
     * @param color the color of the blocks
     * @param blockCount local block counter
     */
    private void createBlockRow(int startX, int endX, int w, int y, Color color, Counter blockCount) {
        for (int x = startX; x >= endX; x -= w) {
            Block brick = new Block(new Rectangle(new Point(x, y), 50, 30), color, null);
            addSprite(brick);
            addCollidable(brick);
            brick.addHitListener(new BlockRemover(this, blockCount));
            brick.addHitListener(new ScoreTrackingListener(score));
            this.blockCount = blockCount;
        }
    }

    /** Create death blocks. */
    private void createDeathZone() {
        /* Death zone are blocks (or block) that has a listener, to be more exact a ball remover type listener.
         * When a ball hit a death zone, the listener picks up the call and initiate the removal of the block
         * very similarly with how we did it with the blocks.
        */
        Color borderCol = new Color(98, 120, 121);

        Block bottom = new Block(new Rectangle(new Point(0, 579), 800, 20), borderCol, null);
        bottom.setColor(borderCol);
        addSprite(bottom);
        addCollidable(bottom);

        Counter ballCounter = new Counter(3);
        bottom.addHitListener(new BallRemover(this, ballCounter));
        this.ballCount = ballCounter;
    }

        /** Create border blocks. */
    private void createBorder() {
        Color borderCol = Util.borderCol();

        Block top = new Block(new Rectangle(new Point(0, 0), 800, 20), borderCol, null);
        top.setColor(borderCol);

        Block bottom = new Block(new Rectangle(new Point(0, 580), 800, 20), borderCol, null);
        bottom.setColor(borderCol);

        Block left = new Block(new Rectangle(new Point(0, 0), 20, 600), borderCol, null);
        left.setColor(borderCol);

        Block right = new Block(new Rectangle(new Point(780, 0), 20, 600), borderCol, null);
        right.setColor(borderCol);

        for (Block b : new Block[]{top, bottom, left, right}) {
            addSprite(b);
            addCollidable(b);
        }

        // Create the death zone blocks are part of the border
        createDeathZone();
    }

    /** Remove a collidable from the collidable list using game.
     *
     * @param c collidable object
     */
    public void removeCollidable(Collidable c) {
        environment.removeCollidable(c);
    }

    /** Remove a sprite from the sprite list using game.
     *
     * @param s sprite object
     */
    public void removeSprite(Sprite s) {
        sprites.removeSprite(s);
    }

    /** Method for closing the game. */
    public void close() {
        gui.close();
    }
}
