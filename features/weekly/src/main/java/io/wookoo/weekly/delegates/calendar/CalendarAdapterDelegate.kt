package io.wookoo.weekly.delegates.calendar

import com.google.android.material.color.MaterialColors
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.design.system.databinding.CalendarRecyclerItemBinding
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.uimodels.UiCalendarDayModel

internal fun calendarAdapterDelegate() =
    adapterDelegateViewBinding<UiCalendarDayModel, DisplayableItem, CalendarRecyclerItemBinding>(
        { layoutInflater, root -> CalendarRecyclerItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            with(binding) {
                dayNumber.text = item.dayNumber
                dayName.text = item.dayName
                val cardColor = if (item.isToday) {
                    MaterialColors.getColor(
                        root,
                        com.google.android.material.R.attr.colorSecondary
                    )
                } else {
                    getColor(io.wookoo.design.system.R.color.white)
                }
                val textColor = if (item.isToday) {
                    getColor(io.wookoo.design.system.R.color.white)
                } else {
                    MaterialColors.getColor(
                        root,
                        com.google.android.material.R.attr.colorSecondary
                    )
                }
                dailyCard.setCardBackgroundColor(cardColor)
                dayName.setTextColor(textColor)
                dayNumber.setTextColor(textColor)
            }
        }
    }
