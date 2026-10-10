package brickbreaker.sprites;

import biuoop.DrawSurface;

import java.util.ArrayList;

/** Sprite collection class. */
public class SpriteCollection {
    private ArrayList<Sprite> sprites;

    /** Constructor. */
    public SpriteCollection() {
        this.sprites = new ArrayList<>();
    }

    /** Getter.
     *
     * @return sprites
     */
    public ArrayList<Sprite> getSprites() {
        return this.sprites;
    }

    /** Adder.
     *
     * @param s sprite
     */
    public void addSprite(Sprite s) {
        this.sprites.add(s);
    }

    /** Call timePassed on all sprites. */
    public void notifyAllTimePassed() {
        ArrayList<Sprite> copy = new ArrayList<>(this.sprites);
        for (Sprite s : copy) {
            s.timePassed();
        }
    }

    /** Call drawOn on all sprites.
     *
     * @param d
     */
    public void drawAllOn(DrawSurface d) {
        for (Sprite s : this.sprites) {
            d.setColor(s.getColor());
            s.drawOn(d);
        }
    }

    /** Remove sprite from the collection.
     *
     * @param s
     */
    public void removeSprite(Sprite s) {
        sprites.remove(s);
    }

    /** Return the size of the collection.
     *
     * @return the size of the sprite collection
     */
    public int size() {
        return this.sprites.size();
    }
}