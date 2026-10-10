package brickbreaker.util;

/** Class counter for managing the counters in our project. */
public class Counter {

    private int num;

    /** Initialize counter with a given number.
     *
     * @param num number of blocks
     */
    public Counter(int num) {
        this.num = num;
    }

    /** Initialize counter from zero.
     *
     */
    public Counter() {
        num = 0;
    }

    /** add number to current count.
     *
     * @param number increase by number
     */
    public void increase(int number) {
        num += number;
    }

    /** subtract number from current count.
     *
     * @param number
     */
    public void decrease(int number) {
        num -= number;
    }

    /** get current count.
     *
     * @return the amount of blocks remained
     */
    public int getValue() {
        return num;
    }
}