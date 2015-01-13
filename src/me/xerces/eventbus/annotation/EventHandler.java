package me.xerces.eventbus.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Created by Crysk on 12/01/2015.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface EventHandler {

    enum  EventPriority { MONITOR, LOW, MEDIUM, HIGH};

    EventPriority priority() default EventPriority.LOW;
}
