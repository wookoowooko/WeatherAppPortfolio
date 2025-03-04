package io.wookoo.weekly.uimodels

import io.wookoo.weekly.DisplayableItem

internal data class CalendarContainer(
    val listOfDays: List<UiCalendarDayModel>,
) : DisplayableItem
