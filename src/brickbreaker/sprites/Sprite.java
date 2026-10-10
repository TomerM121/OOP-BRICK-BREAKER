package brickbreaker.sprites;

import biuoop.DrawSurface;
import java.awt.Color;

/** Sprite interface. */
public interface Sprite {
    /** draw the sprite to the screen.
     *
     * @param d surface
     */
    void drawOn(DrawSurface d);

    /** notify the sprite that time has passed. */
    void timePassed();

    /** Get color.
     *
     * @return color
     */
    Color getColor();
}