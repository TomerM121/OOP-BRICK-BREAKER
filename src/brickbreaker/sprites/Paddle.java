package brickbreaker.sprites;

import brickbreaker.collision.Collidable;
import brickbreaker.geometry.Point;
import brickbreaker.geometry.Rectangle;
import brickbreaker.geometry.Velocity;
import brickbreaker.util.Util;

import java.awt.Color;

import biuoop.DrawSurface;
import biuoop.KeyboardSensor;

/** Paddle class. */
public class Paddle implements Sprite, Collidable {

    private final Block block;
    private KeyboardSensor keyboard;
    private final Color color;

    /** Create a paddle around an existing Block.
     *
     * @param paddleblock block of the paddle
     * @param color color of the paddle
     */
    public Paddle(Block paddleblock, Color color) {
        this.block  = paddleblock;
        this.color = color;
    }

    /** This method generalized the moving of the paddle, including a wrap around.
     *
     * @param x move by
     */
    private void move(double x) {
        // Transform into rectangle
        block.toRectangle(x, 0);

        // Update
        double leftX  = block.getCollisionRectangle().getUpperLeft().getX();
        double width  = block.getCollisionRectangle().getWidth();
        double rightX = leftX + width;

        // Wrap around
        if (leftX < 20) {
            block.toRectangle((780 - width) - leftX, 0);
        } else if (rightX > 780) {
            block.toRectangle(20 - leftX, 0);
        }
    }

    /** Move left with keyboard. */
    public void moveLeft() {
        move(-18);
    }
    /** Move right with keyboard. */
    public void moveRight() {
        move(18);
    }

    @Override
    public void timePassed() {
        if (keyboard == null) {
            return;
        }

        if (keyboard.isPressed(KeyboardSensor.LEFT_KEY)) {
            moveLeft();
        }

        if (keyboard.isPressed(KeyboardSensor.RIGHT_KEY)) {
            moveRight();
        }
    }

    @Override
    public void drawOn(DrawSurface d) {
        d.setColor(color);
        Rectangle r = block.getCollisionRectangle();
        d.fillRectangle((int) r.getUpperLeft().getX(), (int) r.getUpperLeft().getY(), (int) r.getWidth(),
                (int) r.getHeight());
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return block.getCollisionRectangle();
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        // The block applies the regular bounce (and colors the ball).
        Velocity bounced = block.hit(hitter, collisionPoint, currentVelocity);

        // Only a ball coming down onto the top of the paddle gets the angle by hit position.
        boolean topHit = currentVelocity.getDY() > 0
                && Math.abs(collisionPoint.getY() - block.getCollisionRectangle().getUpperLeft().getY())
                < Util.threshold();

        return topHit ? funPaddle(collisionPoint, currentVelocity) : bounced;
    }

    /** Make the paddle fun: the paddle is split into 5 equal zones, and the ball leaves each zone at a
     * different angle (measured from straight up): -60, -30, 0, 30, 60 degrees. The speed is kept.
     *
     * @param col collision point
     * @param velocity current velocity
     * @return new velocity
     */
    public Velocity funPaddle(Point col, Velocity velocity) {
        Rectangle r = block.getCollisionRectangle();
        double zoneWidth = r.getWidth() / 5;

        int zone = (int) ((col.getX() - r.getUpperLeft().getX()) / zoneWidth);
        zone = Math.max(0, Math.min(4, zone));

        double[] angles = {-60, -30, 0, 30, 60};
        double angle = Math.toRadians(angles[zone]);
        double speed = Math.hypot(velocity.getDX(), velocity.getDY());

        return new Velocity(speed * Math.sin(angle), -speed * Math.cos(angle));
    }

    /** Read key from gui.
     *
     * @param gui gui
     */
    public void readKey(biuoop.GUI gui) {
        this.keyboard = gui.getKeyboardSensor();
    }

    @Override
    public Block getBlock() {
        return block;
    }

    /** Paddle color getter.
     *
     * @return Color of the paddle.
     */
    public Color getColor() {
        return color;
    }
}
