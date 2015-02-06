package me.xerces.eventbus;

import me.xerces.eventbus.annotation.EventHandler;
import me.xerces.eventbus.events.Event;
import me.xerces.eventbus.annotation.EventPriority;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

public class SimpleEventManager implements IEventManager{

    private Map<Class<?extends Event>, List<EventHandle>> eventHandleMap = new HashMap<>();

    /**
     * Add an object as an events listener, this method will loop through the objects methods
     * and if the method has {@link me.xerces.eventbus.annotation.EventHandler} present then it will add it has an Event Handler
     * @param object the object to register
     */
    public void addEventListener(Object object)
    {
        for(Method method : object.getClass().getDeclaredMethods())
        {
            if(method.isAnnotationPresent(EventHandler.class) && method.getParameterTypes().length > 0)
            {
                EventHandler eventHandler = (EventHandler)method.getDeclaredAnnotations()[0];
                if(eventHandleMap.get(method.getParameterTypes()[0]) != null)
                {
                    eventHandleMap.get(method.getParameterTypes()[0]).add(new EventHandle(method, object, eventHandler.priority()));
                    sortArrayList(eventHandleMap.get(method.getParameterTypes()[0]));
                } else {
                    List<EventHandle> eventHandles = new ArrayList<>();
                    eventHandles.add(new EventHandle(method, object, eventHandler.priority()));
                    eventHandleMap.put((Class<?extends Event>)method.getParameterTypes()[0], eventHandles);
                }
                method.setAccessible(true);
            }
        }
    }

    /**
     * Add an object as an events listener, this method will loop through the objects methods
     * and if the method has {@link me.xerces.eventbus.annotation.EventHandler} present then it will add it has an Event Handler
     * @param object the object to register
     * @param eventClass the specific events class we want to filter for
     */
    public void addSpecificEventListener(Object object, Class<?extends Event> eventClass)
    {
        for(Method method : object.getClass().getDeclaredMethods())
        {
            if(method.isAnnotationPresent(EventHandler.class) && method.getParameterTypes().length > 0 && method.getParameterTypes()[0].equals(eventClass))
            {
                EventHandler eventHandler = (EventHandler)method.getDeclaredAnnotations()[0];
                if(eventHandleMap.get(method.getParameterTypes()[0]) != null)
                {
                    eventHandleMap.get(method.getParameterTypes()[0]).add(new EventHandle(method, object, eventHandler.priority()));
                    sortArrayList(eventHandleMap.get(method.getParameterTypes()[0]));
                } else {
                    List<EventHandle> eventHandles = new ArrayList<>();
                    eventHandles.add(new EventHandle(method, object, eventHandler.priority()));
                    eventHandleMap.put((Class<?extends Event>)method.getParameterTypes()[0], eventHandles);
                }
                method.setAccessible(true);
            }
        }
    }

    /**
     * Sort the ArrayList by the Priority making sure its Highest - Lowest
     * @param arrayList the ArrayList to sort
     */
    private void sortArrayList(List<EventHandle> arrayList)
    {
        Collections.sort(arrayList, new Comparator() {
            @Override
            public int compare(Object objOne, Object objTwo) {
                EventHandle eventHandleFirst = (EventHandle) objOne;
                EventHandle eventHandleSecond = (EventHandle) objTwo;
                return eventHandleSecond.getPriority().ordinal() - eventHandleFirst.getPriority().ordinal();
            }
        });
    }

    /**
     * Removes all methods in the object from the {@link #eventHandleMap}
     * @param object the object of which you want the listeners removed from
     */
    public void removeEventListener(Object object)
    {
        Iterator<Map.Entry<Class<?extends Event>, List<EventHandle>>> iterator = eventHandleMap.entrySet().iterator();
        while(iterator.hasNext())
        {
            Map.Entry<Class<?extends Event>, List<EventHandle>> entry = iterator.next();
            Iterator<EventHandle> eventHandleIterator = entry.getValue().iterator();
            while(eventHandleIterator.hasNext())
            {
                if(eventHandleIterator.next().getMethodClass().equals(object))
                    eventHandleIterator.remove();
            }
        }
    }

    /**
     * Fire an events to all applicable events handles
     * @param event the events to be fired
     */
    public void fireEvent(Event event)
    {
        List<EventHandle> eventHandles;
        if((eventHandles = eventHandleMap.get(event.getClass())) != null)
        {
            for(EventHandle eventHandle : eventHandles)
            {
                if(!event.isCancelled() && eventHandle.getPriority() != EventPriority.MONITOR) {
                    try {
                        eventHandle.getMethod().invoke(eventHandle.getMethodClass(), event);
                    } catch (InvocationTargetException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }
}
