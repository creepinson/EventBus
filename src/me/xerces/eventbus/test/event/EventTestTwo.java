package me.xerces.eventbus.test.event;

import me.xerces.eventbus.EventHandle;
import me.xerces.eventbus.events.Event;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Xerces
 * @since 24/01/2015
 */
public class EventTestTwo extends Event{

    private static final List<EventHandle> eventHandlers = new ArrayList<EventHandle>();

    @Override
    public List<EventHandle> getHandlers() {
        return eventHandlers;
    }
}
