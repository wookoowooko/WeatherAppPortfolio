package io.wookoo.weekly.uimodels

interface DisplayableItem {
    fun id(): Any
    fun content(): Any

    fun payload(other: Any): Payload = Payload.None

    /**
     * Simple marker interface for payloads
     */
    interface Payload {
        object None : Payload
    }
}
