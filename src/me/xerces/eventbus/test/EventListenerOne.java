package me.xerces.eventbus.test;

import me.xerces.eventbus.annotation.EventHandler;
import me.xerces.eventbus.test.event.EventTest;
import me.xerces.eventbus.test.event.EventTestTwo;

/**
 * @author Xerces
 * @since 24/01/2015
 */
public class EventListenerOne {

    @EventHandler
    public void eventTest(final EventTest event)
    {

    }

    @EventHandler
    public void eventTestTwo(final EventTest event)
    {

    }

    @EventHandler
    public void eventTestThree(final EventTestTwo event)
    {

    }

}
