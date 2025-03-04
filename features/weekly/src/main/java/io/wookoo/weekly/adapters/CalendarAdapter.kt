package io.wookoo.weekly.adapters

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.delegates.calendar.calendarAdapterDelegate

internal class CalendarAdapter : ListDelegationAdapter<List<DisplayableItem>>(
    calendarAdapterDelegate(),
)
