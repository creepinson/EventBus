package dev.throwouterror.eventbus

import dev.throwouterror.eventbus.annotation.EventHandler
import dev.throwouterror.eventbus.annotation.EventPriority
import dev.throwouterror.eventbus.events.Event
import java.lang.reflect.InvocationTargetException
import java.util.*
import kotlin.collections.ArrayList

class SimpleEventManager : IEventManager {
    private val eventHandleMap: MutableMap<Class<out Event>, MutableList<EventHandle>> = HashMap()

    /**
     * Add an object as an events listener, this method will loop through the objects methods
     * and if the method has [dev.throwouterror.eventbus.annotation.EventHandler] present then it will add it has an Event Handler
     * @param object the object to register
     */
    override fun addEventListener(`object`: Any) {
        for (method in `object`.javaClass.declaredMethods) if (method.isAnnotationPresent(EventHandler::class.java) && method.parameterTypes.isNotEmpty()) {
            val eventHandler: EventHandler = method.declaredAnnotations[0] as EventHandler
            if (eventHandleMap[method.parameterTypes[0]] != null) {
                eventHandleMap[method.parameterTypes[0]]!!.add(EventHandle(method, `object`, eventHandler.priority))
                sortArrayList(eventHandleMap[method.parameterTypes[0]]!!)
            } else {
                val eventHandles: MutableList<EventHandle> = ArrayList()
                eventHandles.add(EventHandle(method, `object`, eventHandler.priority))
                eventHandleMap[method.parameterTypes[0] as Class<out Event>] = eventHandles
            }
            method.isAccessible = true
        }
    }

    /**
     * Add an object as an events listener, this method will loop through the objects methods
     * and if the method has [dev.throwouterror.eventbus.annotation.EventHandler] present then it will add it has an Event Handler
     * @param object the object to register
     * @param eventClass the specific events class we want to filter for
     */
    override fun addSpecificEventListener(`object`: Any, eventClass: Class<out Event?>) {
        for (method in `object`.javaClass.declaredMethods) if (method.isAnnotationPresent(EventHandler::class.java) && method.parameterTypes.isNotEmpty() && method.parameterTypes[0] == eventClass) {
            val eventHandler: EventHandler = method.declaredAnnotations[0] as EventHandler
            if (eventHandleMap[method.parameterTypes[0]] != null) {
                eventHandleMap[method.parameterTypes[0]]!!.add(EventHandle(method, `object`, eventHandler.priority))
                sortArrayList(eventHandleMap[method.parameterTypes[0]]!!)
            } else {
                val eventHandles: MutableList<EventHandle> = ArrayList()
                eventHandles.add(EventHandle(method, `object`, eventHandler.priority))
                eventHandleMap[method.parameterTypes[0] as Class<out Event>] = eventHandles
            }
            method.isAccessible = true
        }
    }

    /**
     * Sort the ArrayList by the Priority making sure its Highest - Lowest
     * @param arrayList the ArrayList to sort
     */
    private fun sortArrayList(arrayList: MutableList<EventHandle>) {
        arrayList.sortWith(Comparator { objOne, objTwo ->
            objTwo!!.getPriority().ordinal - objOne!!.getPriority().ordinal
        })
    }

    /**
     * Removes all methods in the object from the [.eventHandleMap]
     * @param object the object of which you want the listeners removed from
     */
    override fun removeEventListener(`object`: Any?) {
        val iterator: Iterator<Map.Entry<Class<out Event>, MutableList<EventHandle>>> = eventHandleMap.entries.iterator()
        while (iterator.hasNext()) {
            val entry: Map.Entry<Class<out Event>, MutableList<EventHandle>> = iterator.next()
            val eventHandleIterator: MutableIterator<EventHandle> = entry.value.iterator()
            while (eventHandleIterator.hasNext()) {
                if (eventHandleIterator.next().methodClass == `object`) eventHandleIterator.remove()
            }
        }
    }

    /**
     * Fire an events to all applicable events handles
     * @param event the events to be fired
     */
    override fun fireEvent(event: Event) {
        val eventHandles = getHandlers(event::class.java)
        for (eventHandle in eventHandles) {
            if (!event.isCancelled && eventHandle.getPriority() !== EventPriority.MONITOR) {
                try {
                    eventHandle.method.invoke(eventHandle.methodClass, event)
                } catch (e: InvocationTargetException) {
                    e.printStackTrace()
                } catch (e: IllegalAccessException) {
                    e.printStackTrace()
                }
            }
        }
    }

    fun getHandlers(event: Class<out Event>): MutableList<EventHandle> {
        return if (eventHandleMap[event] != null) eventHandleMap[event]!! else arrayListOf()
    }
}