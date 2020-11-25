package dev.throwouterror.eventbus.test

import dev.throwouterror.eventbus.SimpleEventBus
import dev.throwouterror.eventbus.event.Event
import dev.throwouterror.eventbus.observeEvent
import java.util.concurrent.TimeUnit

class EventBusTest {
    companion object {
        @JvmStatic
        fun main() {
            EventBusTest()
        }
    }

    init {
        val eventManager = SimpleEventBus()
        val eventsToFire = 1000000
        var lastMS = System.nanoTime()
        for (i in 0 until eventsToFire) {
            eventManager.fireEvent(Event("test"))
        }
        lastMS = System.nanoTime() - lastMS
        println(String.format("Firing 1 million events took %dms", TimeUnit.MILLISECONDS.convert(lastMS, TimeUnit.NANOSECONDS)))
        eventManager.observeEvent("test2").subscribe {
            println("Receieved event ${it.type}")
        }
        eventManager.fireEvent(Event("test2"))
    }
}
