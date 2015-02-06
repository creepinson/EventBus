package me.xerces.eventbus;

import me.xerces.eventbus.events.Event;

/**
 * @author Xerces
 * @since 18/01/2015
 */
public interface IEventManager {

    public void addEventListener(Object object);

    public void addSpecificEventListener(Object object, Class<?extends Event> eventClass);

    public void removeEventListener(Object object);

    public void fireEvent(Event event);

}
