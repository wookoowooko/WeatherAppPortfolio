package io.wookoo.weekly.uimodels

import io.wookoo.domain.units.StringUnit
import io.wookoo.weekly.DisplayableItem

data class UiSuncyclesModel(
    val sunriseTime: StringUnit,
    val sunsetTime: StringUnit,
) : DisplayableItem {
    override fun id(): Any = "$sunriseTime-$sunsetTime"

    override fun content(): Any = Content(
        sunriseTime, sunsetTime
    )

    private data class Content(
        val sunriseTime: StringUnit,
        val sunsetTime: StringUnit,
    )
}