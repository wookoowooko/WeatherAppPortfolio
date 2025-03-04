package io.wookoo.weekly.delegates.calendar
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.design.system.databinding.CalendarRecyclerBinding
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.adapters.CalendarAdapter
import io.wookoo.weekly.uimodels.CalendarContainer

internal fun calendarContainerAdapter() = adapterDelegateViewBinding<
    CalendarContainer,
    DisplayableItem,
    CalendarRecyclerBinding
    >(
    { layoutInflater, root -> CalendarRecyclerBinding.inflate(layoutInflater, root, false) }
) {
    val calendarAdapter = CalendarAdapter()
    binding.calendarContainer.adapter = calendarAdapter

    bind {
        calendarAdapter.items = item.listOfDays
    }
}
