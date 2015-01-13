package me.xerces.eventbus.test;

import me.xerces.eventbus.EventManager;
import me.xerces.eventbus.annotation.EventHandler;
import me.xerces.eventbus.test.event.EventTest;
import me.xerces.eventbus.test.utils.TimeManager;

/**
 * Created by Crysk on 12/01/2015.
 */
public class EventBusTest {

    public static void main(String[] args)
    {
        new EventBusTest();
    }

    public EventBusTest()
    {
        EventManager eventHandler = new EventManager();
        eventHandler.addEventListener(this);
        TimeManager timeManager = new TimeManager();
        int eventNum = 1000000;
        System.out.println("Starting event test...");
        for(int i = 0; i < eventNum; i++)
        {
            eventHandler.fireEvent(new EventTest());
        }
        System.out.println(String.format("%dms", timeManager.getElapsedTime()));
    }

    private int processedEvents = 0;

    @EventHandler
    private void eventTest(final EventTest e)
    {
        processedEvents++;
        if(processedEvents % 100000 == 0)
        {
            System.out.println(String.format("Processed %d amount of events...", processedEvents));
        }
    }

}
