package dev.throwouterror.eventbus

import dev.throwouterror.eventbus.event.Event
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.processors.PublishProcessor

/**
 * [EventBus] implementation backed by a [PublishProcessor]
 */
class SimpleEventBus : EventBus<Event> {
    private val publishProcessor = PublishProcessor.create<Event>()

    override fun fireEvent(event: Event) {
        publishProcessor.onNext(event)
    }

    override fun observeEvents(): Flowable<Event> {
        return publishProcessor.serialize()
    }

    fun observeEvent(type: String): Flowable<Event> {
        return observeEvents().filter { it.type == type }
    }
}