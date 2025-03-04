package io.wookoo.weekly.uimodels

import io.wookoo.weekly.DisplayableItem

internal data class UiCalendarDayModel(
    val dayName: String = "Sun",
    val dayNumber: String = "23",
    val isToday: Boolean = false,
    val isDay: Boolean = true,
) : DisplayableItem
