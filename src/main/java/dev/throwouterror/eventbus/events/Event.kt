package dev.throwouterror.eventbus.events

/**
 * The Event class used as a base for other events
 */
abstract class Event {
    /**
     * isCancelled check if the events has been cancelled, will stop the [dev.throwouterror.eventbus.SimpleEventManager] from firing it to lower priority handlers.
     * @return isCancelled if the events is cancelled
     */
    /**
     * setCancelled Will set if the events is cancelled or not
     * @param isCancelled Set if the events is cancelled, if it is the events manager will cease to fire it to lower priority handlers.
     */
    var isCancelled = false

}