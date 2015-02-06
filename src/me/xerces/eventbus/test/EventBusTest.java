package me.xerces.eventbus.test;

import me.xerces.eventbus.IEventManager;
import me.xerces.eventbus.SimpleEventManager;
import me.xerces.eventbus.annotation.EventHandler;
import me.xerces.eventbus.test.event.EventTest;
import me.xerces.eventbus.test.event.EventTestTwo;

import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

/**
 * @author Xerces
 * @since 24/01/2015
 */
public class EventBusTest {

    public static void main(String[] args)
    {
        new EventBusTest();
    }

    public EventBusTest()
    {
        IEventManager eventManager = new SimpleEventManager();
        eventManager.addEventListener(new EventListenerOne());
        eventManager.addEventListener(new EventListenerTwo());
        int eventsToFire = 1000000;
        long lastMS = System.nanoTime();
        for(int i = 0; i < eventsToFire; i++)
        {
            eventManager.fireEvent(new EventTest());
            eventManager.fireEvent(new EventTestTwo());
        }
        lastMS = System.nanoTime() - lastMS;
        System.out.println(String.format("Firing 1 million events took %dms", TimeUnit.MILLISECONDS.convert(lastMS, TimeUnit.NANOSECONDS)));
    }


}
