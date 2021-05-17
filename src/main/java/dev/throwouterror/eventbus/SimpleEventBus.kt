package dev.throwouterror.eventbus

import dev.throwouterror.eventbus.event.Event
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.subjects.BehaviorSubject

/**
 * [EventBus] implementation backed by a [PublishProcessor]
 */
class SimpleEventBus : EventBus<Event> {
    private val publishProcessor = BehaviorSubject.create<Event>()

    override fun fireEvent(event: Event) {
        publishProcessor.onNext(event)
    }

    override fun observeEvents(): Flowable<Event> {
        return publishProcessor.toFlowable(BackpressureStrategy.BUFFER)
    }

    fun observeEvent(type: String): Flowable<Event> {
        return observeEvents().filter { it.type == type }
    }
}
