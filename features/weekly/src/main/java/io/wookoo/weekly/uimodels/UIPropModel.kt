package io.wookoo.weekly.uimodels

import io.wookoo.domain.enums.WeatherProperty
import io.wookoo.domain.units.ApplicationUnit
import io.wookoo.weekly.DisplayableItem

data class UIPropModel(
    val prop: WeatherProperty = WeatherProperty.UNDEFINED,
    val value: ApplicationUnit,
) : DisplayableItem {
    override fun id(): Any {
        return prop
    }

    override fun content(): Any = Content(
        value = value
    )

    private data class Content(
        val value: ApplicationUnit,
    )
}
