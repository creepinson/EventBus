package dev.throwouterror.eventbus

import dev.throwouterror.eventbus.events.Event

interface IEventManager {
    fun addEventListener(`object`: Any)
    fun addSpecificEventListener(`object`: Any, eventClass: Class<out Event?>)
    fun removeEventListener(`object`: Any?)
    fun fireEvent(event: Event)
}