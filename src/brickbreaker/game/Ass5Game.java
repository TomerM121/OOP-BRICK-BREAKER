package brickbreaker.game;

/** Class to run the game.
 *
 */
public class Ass5Game {
    /** Main func.
     *
     * @param args args
     */
    public static void main(String[] args) {
        Game game = new Game();
        game.initialize();
        game.run();
        game.close();
    }
}

/* BUGS LIST */

/*

 === NEEDS FIX ===
 1. Sometimes the ball is able to escape the upper border.

 === CURRENTLY WORKING ===
 2. The Block often gets stuck inside the paddle. (The reason for this is the paddle moves over the ball then it
 thinks it is a border it cannot escape).
 FIX: If the ball is leveled with the paddle this should count as a fail because this is an impossible situation.
 Current Situation: Ball doesn't get stuck but there is a weird jump, if it causes any issues with losing check it.

 === NEEDS FIX ===
 3. Paddle is not fun! The paddle needs to reverse velocity in a certain direction according to the hit point.

 */