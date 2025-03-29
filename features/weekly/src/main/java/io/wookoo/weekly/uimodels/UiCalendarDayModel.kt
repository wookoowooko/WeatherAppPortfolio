package io.wookoo.weekly.uimodels

import io.wookoo.models.units.WeatherCondition

data class UiCalendarDayModel(
    val dayName: String,
    val dayNumber: String,
    val isToday: Boolean,
    val isDay: Boolean,
    val isSelected: Boolean,
    val weatherCondition: WeatherCondition = WeatherCondition.UNKNOWN,
) : DisplayableItem {
    override fun id(): Any {
        return dayName
    }

    override fun content(): Any = Content(
        dayNumber,
        isToday,
        isDay,
        isSelected,
        weatherCondition
    )

    internal data class Content(
        val dayNumber: String,
        val isToday: Boolean,
        val isDay: Boolean,
        val isSelected: Boolean,
        val weatherCondition: WeatherCondition = WeatherCondition.UNKNOWN,
    )
}
