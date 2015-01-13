package me.xerces.eventbus;

import me.xerces.eventbus.annotation.EventHandler;
import me.xerces.eventbus.event.Event;

import java.lang.invoke.MethodHandle;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class EventManager {

    private Map<Class<?>, List<EventHandle>> eventHandleMap = new HashMap<>();

    /**
     * Add an object as an event listener, this method will loop through the objects methods
     * and if the method has {@link me.xerces.eventbus.annotation.EventHandler} present then it will add it has an Event Handler
     * @param object the object to register
     */
    public void addEventListener(Object object)
    {
        for(Method method : object.getClass().getDeclaredMethods())
        {
            if(method.isAnnotationPresent(EventHandler.class))
            {
                if(eventHandleMap.get(method.getParameterTypes()[0]) != null)
                {
                    eventHandleMap.get(method.getParameterTypes()[0]).add(new EventHandle(method, object));
                } else {
                    List<EventHandle> eventHandles = new ArrayList<>();
                    eventHandles.add(new EventHandle(method, object));
                }
                method.setAccessible(true);
            }
        }
    }

    /**
     * Add an object as an event listener, this method will loop through the objects methods
     * and if the method has {@link me.xerces.eventbus.annotation.EventHandler} present then it will add it has an Event Handler
     * @param object the object to register
     * @param eventClass the specific event class we want to filter for
     */
    public void addSpecificEventListener(Object object, Class eventClass)
    {
        for(Method method : object.getClass().getDeclaredMethods())
        {
            if(method.isAnnotationPresent(EventHandler.class) && method.getParameterTypes().length > 0 && method.getParameterTypes()[0].equals(eventClass))
            {
                if(eventHandleMap.get(method.getParameterTypes()[0]) != null)
                {
                    eventHandleMap.get(method.getParameterTypes()[0]).add(new EventHandle(method, object));
                } else {
                    List<EventHandle> eventHandles = new ArrayList<>();
                    eventHandles.add(new EventHandle(method, object));
                }
                method.setAccessible(true);
            }
        }
    }

    /**
     * Removes all methods in the object from the {@link #eventHandleMap}
     * @param object
     */
    public void removeEventListener(Object object)
    {
        Iterator<Map.Entry<Class<?>, List<EventHandle>>> iterator = eventHandleMap.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<Class<?>, List<EventHandle>> entry = iterator.next();
            Iterator<EventHandle> eventHandleIterator = entry.getValue().iterator();
            while(eventHandleIterator.hasNext())
            {
                if(eventHandleIterator.next().getMethodClass().equals(object))
                    eventHandleIterator.remove();
            }
        }
    }

    /**
     * Fire an event to all applicable event handles
     * @param event the event to be fired
     */
    public void fireEvent(Event event)
    {
        List<EventHandle> eventHandles;
        if((eventHandles = eventHandleMap.get(event.getClass())) != null)
        {
            for(EventHandle eventHandle : eventHandles)
            {
                try {
                    eventHandle.getMethod().invoke(eventHandle.getMethodClass(), event );
                } catch (InvocationTargetException | IllegalAccessException e)
                {
                    e.printStackTrace();
                }
            }
        }
    }
}
