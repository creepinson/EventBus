package dev.throwouterror.eventbus.test

import dev.throwouterror.eventbus.annotation.EventHandler
import dev.throwouterror.eventbus.test.event.EventTest


class EventListenerTest {
    @EventHandler
    fun eventTest(event: EventTest) {
    }

    @EventHandler
    fun eventTestTwo(event: EventTest) {
    }
}