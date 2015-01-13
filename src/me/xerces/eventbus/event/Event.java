package me.xerces.eventbus.event;

/**
 * The Event class used as a base for other events
 * @author Xerces
 */
public abstract class Event {

    private boolean isCancelled;

    /**
     * setCancelled Will set if the event is cancelled or not
     * @param isCancelled Set if the event is cancelled, if it is the event manager will cease to fire it to lower priority handlers.
     */
    private void setCancelled(boolean isCancelled)
    {
        this.isCancelled = isCancelled;
    }

    /**
     * isCancelled check if the event has been cancelled, will stop the {@link me.xerces.eventbus.EventManager} from firing it to lower priority handlers.
     * @return isCancelled if the event is cancelled
     */
    private boolean isCancelled()
    {
        return isCancelled;
    }

}
