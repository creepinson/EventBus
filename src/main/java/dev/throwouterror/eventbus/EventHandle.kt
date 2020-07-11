package dev.throwouterror.eventbus

import dev.throwouterror.eventbus.annotation.EventPriority
import java.lang.reflect.Method

/**
 * The events handle which holds the method and method object
 */
class EventHandle(val method: Method, val methodClass: Any, private val priority: EventPriority) {
    fun getPriority(): EventPriority {
        return priority
    }

    override fun equals(other: Any?): Boolean {
        return other is EventHandle && other.method == method && other.methodClass == methodClass
    }
}