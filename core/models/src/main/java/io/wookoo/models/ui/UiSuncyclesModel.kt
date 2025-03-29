package io.wookoo.models.ui

import io.wookoo.models.units.StringUnit

data class UiSuncyclesModel(
    val sunriseTime: StringUnit,
    val sunsetTime: StringUnit,
) : DisplayableItem {
    override fun id(): Any = "$sunriseTime-$sunsetTime"

    override fun content(): Any = Content(
        sunriseTime,
        sunsetTime
    )

    private data class Content(
        val sunriseTime: StringUnit,
        val sunsetTime: StringUnit,
    )
}
