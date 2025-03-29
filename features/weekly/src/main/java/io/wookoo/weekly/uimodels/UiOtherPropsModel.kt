package io.wookoo.weekly.uimodels

import io.wookoo.models.units.SecondsDuration

data class UiOtherPropsModel(
    val dayLightDuration: SecondsDuration,
    val sunShineDuration: SecondsDuration,
    val maxUvIndex: String,
) : DisplayableItem {
    override fun id(): Any = dayLightDuration

    override fun content(): Any = Content(
        sunShineDuration,
        maxUvIndex
    )

    private data class Content(
        val sunShiftDuration: SecondsDuration,
        val maxUvIndex: String,
    )
}
