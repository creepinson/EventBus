package dev.throwouterror.eventbus

import dev.throwouterror.eventbus.event.Event
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.processors.PublishProcessor

/**
 * [EventBus] implementation backed by a [PublishProcessor]
 */
class TypedEventBus<T> : EventBus<T> {
    private val publishProcessor = PublishProcessor.create<T>()

    override fun fireEvent(event: T) {
        publishProcessor.onNext(event)
    }

    override fun observeEvents(): Flowable<T> {
        return publishProcessor.serialize()
    }
}

fun TypedEventBus<Event>.observeEvent(type: String): Flowable<Event> {
    return observeEvents().filter { it.type == type }
}