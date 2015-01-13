package me.xerces.eventbus;

import java.lang.reflect.Method;

/**
 * The event handle which holds the method and method object
 * @author Xerces
 */
public class EventHandle {

    private Method method;

    private Object methodClass;

    public EventHandle(Method method, Object methodClass)
    {
        this.method = method;
        this.methodClass = methodClass;
    }

    public Method getMethod() {
        return method;
    }

    public Object getMethodClass() {
        return methodClass;
    }

    @Override
    public boolean equals(Object obj) {
        return obj instanceof EventHandle && ((EventHandle) obj).getMethod() == method && ((EventHandle) obj).getMethodClass() == methodClass;
    }
}
