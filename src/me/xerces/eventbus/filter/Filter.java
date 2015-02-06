package me.xerces.eventbus.filter;

import me.xerces.eventbus.events.Event;

/**
 * @author Xerces
 * @since 04/02/2015
 */
public interface Filter<T extends Event> {

    public boolean shouldSend(T t);

}
