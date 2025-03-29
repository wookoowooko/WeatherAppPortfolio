package io.wookoo.weekly.delegates.windcard

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.common.ext.asLocalizedString
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.design.system.databinding.WindCardItemBinding
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.models.units.WindDirection
import io.wookoo.weekly.uimodels.DisplayableItem
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

                windDirection.setText((item.windDirection as WindDirection).asLocalizedString())
            }
        }
    }
