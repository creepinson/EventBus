package dev.throwouterror.eventbus

import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.kotlin.ofType


interface EventBus<T> {
    /**
     * Post an event to bus.
     */
    fun fireEvent(event: T)

    /**
     * @return A [Flowable] that will emit event for every [fireEvent] call until subscriber is
     * unsubscribed.
     *
     * Note: This does not emit event on any particular [Scheduler]
     */
    fun observeEvents(): Flowable<T>
}

/**
 * Observer extension that will only emit event of type [T], filtering all other events.
 */
inline fun <reified T : Any> EventBus<T>.observeEvent(): Flowable<T> {
    return observeEvents().ofType()
}
