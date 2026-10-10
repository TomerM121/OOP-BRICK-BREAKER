package brickbreaker.sprites;

import brickbreaker.collision.Collidable;
import brickbreaker.game.Game;
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
import java.util.Objects;

/** Block class. */
public class Block implements Collidable, Sprite, HitNotifier {

    private Rectangle shape;
    private Color color;
    private final List<HitListener> listeners = new ArrayList<>();

    /** Build a new block object.
     *
     * @param upperLeft upper left point
     * @param lowerRight lower right point
     * @param color color of the block
     * @param initial initial listeners
     */
    public Block(Point upperLeft, Point lowerRight, Color color, List<HitListener> initial) {
        this.shape = new Rectangle(upperLeft, lowerRight);
        this.color = color;
        if (initial != null) {
            listeners.addAll(initial);
        }
    }

    /** Build a new block object.
     *
     * @param rect rectangle
     * @param color color of the block
     * @param initial initial listeners
     */
    public Block(Rectangle rect, Color color, List<HitListener> initial) {
        this(rect.getUpperLeft(), rect.getLowerRight(), color, initial);
    }

    @Override
    public Rectangle getCollisionRectangle() {
        return new Rectangle(shape.getUpperLeft(), shape.getLowerRight());
    }

    @Override
    public Velocity hit(Ball hitter, Point collisionPoint, Velocity currentVelocity) {
        double newDx = currentVelocity.getDX();
        double newDy = currentVelocity.getDY();
        Point p = collisionPoint;
        double threshold = Util.threshold();
        Color borderCol = Util.borderCol();

        if (Math.abs(p.getY() - shape.getUpperLeft().getY()) < threshold
                || Math.abs(p.getY() - shape.getLowerRight().getY()) < threshold) {
            newDy = -newDy;
        }
        if (Math.abs(p.getX() - shape.getUpperLeft().getX()) < threshold
                || Math.abs(p.getX() - shape.getUpperRight().getX()) < threshold) {
            newDx = -newDx;
        }

        if (!hitter.getColor().equals(color) && !color.equals(borderCol)) {
            notifyHit(hitter);
            hitter.setColor(color);
        }

        return new Velocity(newDx, newDy);
    }

    @Override
    public void drawOn(DrawSurface d) {
        // Block by color
        d.setColor(color);
        d.fillRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());

        // Black border for block
        d.setColor(Color.black);
        d.drawRectangle((int) shape.getUpperLeft().getX(), (int) shape.getUpperLeft().getY(),
                (int) shape.getWidth(), (int) shape.getHeight());
    }

    @Override
    public void timePassed() { }

    @Override
    public Color getColor() {
        return this.color;
    }

    /** Remove this block from the game.
     *
     * @param g game
     */
    public void removeFromGame(Game g) {
        g.removeSprite(this);
        g.removeCollidable(this);
    }

    @Override
    public void addHitListener(HitListener hl) {
        listeners.add(hl);
    }

    @Override
    public void removeHitListener(HitListener hl) {
        listeners.remove(hl);
    }

    /** Notify hit to all listeners.
     *
     * @param hitter the hitting ball
     */
    public void notifyHit(Ball hitter) {
        List<HitListener> copy = new ArrayList<>(listeners);

        for (HitListener hl : copy) {
            hl.hitEvent(this, hitter);
        }
    }

    /** Set color for the block.
     *
     * @param c color
     */
    public void setColor(Color c) {
        this.color = c;
    }

    /** This method properly updates the shape instead of just getting it.
     *
     * @param w width
     * @param h height
     */
    public void toRectangle(double w, double h) {
        Point up = this.shape.getUpperLeft();
        Point low = this.shape.getLowerRight();
        this.shape = new Rectangle(new Point(up.getX() + w, up.getY() + h),
                new Point(low.getX() + w, low.getY() + h));
    }

    @Override
    public Block getBlock() {
        return this;
    }
}
