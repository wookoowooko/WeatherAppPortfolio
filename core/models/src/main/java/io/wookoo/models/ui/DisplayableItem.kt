package io.wookoo.models.ui

interface DisplayableItem {
    fun id(): Any
    fun content(): Any

    fun payload(other: Any): Payload = Payload.None

    interface Payload {
        object None : Payload
    }
}
