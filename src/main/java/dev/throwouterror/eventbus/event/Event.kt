package dev.throwouterror.eventbus.event

import org.json.JSONObject

/**
 * The Event class used as a base for other events
 */
open class Event {
    var type: String
        private set
    var parameters: JSONObject
        private set

    constructor(type: String, parameters: JSONObject? = JSONObject()) {
        this.type = type
        this.parameters = parameters!!
    }

    /**
     * isCancelled check if the events has been cancelled, will stop the [dev.throwouterror.eventbus.SimpleEventManager] from firing it to lower priority handlers.
     * @return isCancelled if the events is cancelled
     */
    var isCancelled = false
}