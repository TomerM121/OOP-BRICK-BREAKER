package brickbreaker.util;

import java.awt.*;

/** Utils class. */
public class Util {
    private static final double THRESHOLD = 0.0000001;
    private static Color borderCol = new Color(98, 121, 121);


    /** Get threshold.
     *
     * @return the threshold for our project
     */
    public static double threshold() {
        return THRESHOLD;
    }

    /** Get border color.
     *
     * @return border color
     */
    public static Color borderCol() {
        return borderCol;
    }
}