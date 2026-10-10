package brickbreaker.listeners;

/** The interface of the notifier. */
public interface HitNotifier {
    /** Add hl as a listener to hit events.
     *
     * @param hl hit listener
     */
    void addHitListener(HitListener hl);

    /** Remove hl from the list of listeners to hit events.
     *
     * @param hl hit listener
     */
    void removeHitListener(HitListener hl);
}