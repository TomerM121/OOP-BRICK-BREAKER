package brickbreaker.sprites;

import brickbreaker.geometry.Rectangle;
import brickbreaker.util.Counter;

import biuoop.DrawSurface;

import java.awt.Color;

/** ScoreIndicator class, type of sprite. */
public class ScoreIndicator implements Sprite {
    private final Counter score;
    private final Rectangle frame;
    private final Color background  = new Color(200, 200, 200);
    private final Color font  = Color.BLACK;

    /** Create a new object of ScoreIndicator.
     *
     * @param scoreCounter the counter of score
     * @param bannerArea the background on which we print the text
     */
    public ScoreIndicator(Counter scoreCounter, Rectangle bannerArea) {
        this.score = scoreCounter;
        this.frame = bannerArea;
    }
    @Override
    public void drawOn(DrawSurface d) {
        // Draw background
        d.setColor(background);
        d.fillRectangle((int) frame.getUpperLeft().getX(), (int) frame.getUpperLeft().getY(),
                (int) frame.getWidth(), (int) frame.getHeight());

        // Draw score onto screen
        String msg = "Score: " + score.getValue();
        int x = (int) (frame.getUpperLeft().getX() + frame.getWidth() / 2) - msg.length() * 4;
        int y = (int) (frame.getUpperLeft().getY() + frame.getHeight() * 0.75);
        d.setColor(font);
        d.drawText(x, y, msg, 16);
    }

    @Override
    public void timePassed() {
        return;
    }

    @Override
    public Color getColor() {
        return background;
    }
}
