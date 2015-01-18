package me.xerces.eventbus.events;

/**
 * The Event class used as a base for other events
 * @author Xerces
 */
public abstract class Event {

    private boolean isCancelled;

    /**
     * setCancelled Will set if the events is cancelled or not
     * @param isCancelled Set if the events is cancelled, if it is the events manager will cease to fire it to lower priority handlers.
     */
    public void setCancelled(boolean isCancelled)
    {
        this.isCancelled = isCancelled;
    }

    /**
     * isCancelled check if the events has been cancelled, will stop the {@link me.xerces.eventbus.SimpleEventManager} from firing it to lower priority handlers.
     * @return isCancelled if the events is cancelled
     */
    public boolean isCancelled()
    {
        return isCancelled;
    }

}
