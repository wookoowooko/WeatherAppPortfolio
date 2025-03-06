package io.wookoo.weekly.delegates.windcard

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.common.asStringResource
import io.wookoo.common.asUnitValueWithStringRes
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
                        .value.asUnitValueWithStringRes((item.windSpeed as WeatherValueWithUnit).unit, context)

                windGustsSpeed.text =
                    (item.windGust as WeatherValueWithUnit)
                        .value.asUnitValueWithStringRes((item.windGust as WeatherValueWithUnit).unit, context)

                windDirection.setText((item.windDirection as WindDirection).asStringResource())
            }
        }
    }
