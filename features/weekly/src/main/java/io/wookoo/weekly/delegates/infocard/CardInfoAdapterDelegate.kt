package io.wookoo.weekly.delegates.infocard

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.common.ext.getWeatherDrawableGradient
import io.wookoo.design.system.databinding.InfoCardItemBinding
import io.wookoo.models.ui.DisplayableItem
import io.wookoo.models.ui.UiCardInfoModel

internal fun cardInfoAdapterDelegate() =
    adapterDelegateViewBinding<UiCardInfoModel, DisplayableItem, InfoCardItemBinding>(
        { layoutInflater, root -> InfoCardItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            with(binding) {
                infoCard.background = getWeatherDrawableGradient(item.weatherCondition)
                tempMin.text = item.tempMin
                tempMax.text =
                    item.tempMax.value.asLocalizedUnitValueString(item.tempMax.unit, context)
                feelsLikeMinValue.text =
                    item.feelsLikeMax.value.asLocalizedUnitValueString(
                        item.feelsLikeMax.unit,
                        context
                    )
                feelsLikeMaxValue.text =
                    item.feelsLikeMin.value.asLocalizedUnitValueString(
                        item.feelsLikeMax.unit,
                        context
                    )
                weatherConditionText.setText(item.weatherCondition.asLocalizedUiWeatherMap(item.isDay).second)
                weatherCodeImage.setImageResource(item.weatherCondition.asLocalizedUiWeatherMap(item.isDay).first)
            }
        }
    }
