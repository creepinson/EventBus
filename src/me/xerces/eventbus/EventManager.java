package me.xerces.eventbus;

import me.xerces.eventbus.annotation.EventHandler;
import me.xerces.eventbus.event.Event;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;

/**
 * Created by Crysk on 12/01/2015.
 */
public class EventManager {

    private ArrayList<Map.Entry<Class, EventHandle>> eventHandlerList = new ArrayList<Map.Entry<Class, EventHandle>>();

    public void addEventListener(Object object)
    {
        for(Method method : object.getClass().getDeclaredMethods())
        {
            if(method.isAnnotationPresent(EventHandler.class))
            {
                Map.Entry<Class, EventHandle> eventHandleEntry = new AbstractMap.SimpleEntry<Class, EventHandle>(method.getParameterTypes()[0], new EventHandle(method, object));
                eventHandlerList.add(eventHandleEntry);
            }
        }
    }

    public void addSpecificEventListener(Object object, Class eventClass)
    {
        for(Method method : object.getClass().getDeclaredMethods())
        {
            if(method.isAnnotationPresent(EventHandler.class) && method.getParameterTypes().length > 0 && method.getParameterTypes()[0].equals(eventClass))
            {
                Map.Entry<Class, EventHandle> eventHandleEntry = new AbstractMap.SimpleEntry<Class, EventHandle>(method.getParameterTypes()[0], new EventHandle(method, object));
                eventHandlerList.add(eventHandleEntry);
            }
        }
    }

    public void removeEventListener(Object object)
    {
        Iterator<Map.Entry<Class, EventHandle>> iterator = eventHandlerList.iterator();
        while(iterator.hasNext())
        {
            Map.Entry<Class, EventHandle> entry = iterator.next();
            if(entry.getValue().getMethodClass().equals(object))
                iterator.remove();
        }
    }

    public void fireEvent(Event event)
    {
        Iterator<Map.Entry<Class, EventHandle>> iterator = eventHandlerList.iterator();
        while(iterator.hasNext())
        {
            Map.Entry<Class, EventHandle> entry = iterator.next();
            if(entry.getKey().equals(event.getClass())) {
                try {
                    EventHandle eventHandle = entry.getValue();
                    eventHandle.getMethod().setAccessible(true);
                    eventHandle.getMethod().invoke(eventHandle.getMethodClass(), event);
                } catch (IllegalAccessException | InvocationTargetException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
