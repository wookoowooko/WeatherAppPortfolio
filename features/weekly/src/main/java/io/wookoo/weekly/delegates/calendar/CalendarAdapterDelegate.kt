package io.wookoo.weekly.delegates.calendar

import com.google.android.material.color.MaterialColors
import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.design.system.databinding.CalendarRecyclerItemBinding
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.uimodels.UiCalendarDayModel

internal fun calendarAdapterDelegate(
    onItemClick: (index: Int) -> Unit,
) =
    adapterDelegateViewBinding<UiCalendarDayModel, DisplayableItem, CalendarRecyclerItemBinding>(
        { layoutInflater, root -> CalendarRecyclerItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            binding.root.setOnClickListener { onItemClick(bindingAdapterPosition) }
            with(binding) {
                dayNumber.text = item.dayNumber
                dayName.text = item.dayName

                val cardColor = when {
                    item.isSelected -> MaterialColors.getColor(
                        root,
                        com.google.android.material.R.attr.colorPrimary
                    )
                    item.isToday -> MaterialColors.getColor(
                        root,
                        com.google.android.material.R.attr.colorSecondary
                    )

                    else -> getColor(io.wookoo.design.system.R.color.white)
                }

                val textColor = when {
                    item.isSelected -> MaterialColors.getColor(
                        root,
                        com.google.android.material.R.attr.colorOnPrimary
                    )
                    item.isToday -> MaterialColors.getColor(
                        root,
                        com.google.android.material.R.attr.colorOnSecondary
                    )

                    else -> getColor(io.wookoo.design.system.R.color.light_grey)
                }

                dailyCard.setCardBackgroundColor(cardColor)
                dayName.setTextColor(textColor)
                dayNumber.setTextColor(textColor)

                weatherCodeImage.setImageResource(
                    item.weatherCondition.asLocalizedUiWeatherMap(isDay = item.isDay).first
                )
            }
        }
    }
