package io.wookoo.models.ui

import io.wookoo.models.units.ApplicationUnit

data class UIWindCardModel(
    val windSpeed: ApplicationUnit,
    val windGust: ApplicationUnit,
    val windDirection: ApplicationUnit,
) : DisplayableItem {
    override fun id(): Any = windSpeed

    override fun content(): Any = Content(
        windGust,
        windDirection
    )

    internal data class Content(
        val windGust: ApplicationUnit,
        val windDirection: ApplicationUnit,
    )
}
