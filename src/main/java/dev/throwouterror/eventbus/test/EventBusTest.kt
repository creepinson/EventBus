package dev.throwouterror.eventbus.test

import dev.throwouterror.eventbus.IEventManager
import dev.throwouterror.eventbus.SimpleEventManager
import dev.throwouterror.eventbus.test.event.EventTest
import java.util.concurrent.TimeUnit

class EventBusTest {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            EventBusTest()
        }
    }

    init {
        val eventManager: IEventManager = SimpleEventManager()
        eventManager.addEventListener(EventListenerTest())
        val eventsToFire = 1000000
        var lastMS = System.nanoTime()
        for (i in 0 until eventsToFire) {
            eventManager.fireEvent(EventTest())
        }
        lastMS = System.nanoTime() - lastMS
        println(String.format("Firing 1 million events took %dms", TimeUnit.MILLISECONDS.convert(lastMS, TimeUnit.NANOSECONDS)))
    }
}