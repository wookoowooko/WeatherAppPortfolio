package io.wookoo.weekly.delegates.windcard

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.common.asLocalizedString
import io.wookoo.common.asLocalizedUnitValueString
import io.wookoo.design.system.databinding.WindCardItemBinding
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.domain.units.WindDirection
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.uimodels.UIWindCardModel

internal fun windCardAdapterDelegate() =
    adapterDelegateViewBinding<UIWindCardModel, DisplayableItem, WindCardItemBinding>(
        { layoutInflater, root -> WindCardItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            with(binding) {
                windSpeed.text =
                    (item.windSpeed as WeatherValueWithUnit)
                        .value.asLocalizedUnitValueString(
                            (item.windSpeed as WeatherValueWithUnit).unit,
                            context
                        )

                windGustsSpeed.text =
                    (item.windGust as WeatherValueWithUnit)
                        .value.asLocalizedUnitValueString(
                            (item.windGust as WeatherValueWithUnit).unit,
                            context
                        )

                windDirection.text = (item.windDirection as WindDirection).asLocalizedString(context)
            }
        }
    }
