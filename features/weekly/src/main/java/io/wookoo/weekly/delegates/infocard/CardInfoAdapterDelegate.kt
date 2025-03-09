package io.wookoo.weekly.delegates.infocard

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.design.system.databinding.InfoCardItemBinding
import io.wookoo.weekly.uimodels.DisplayableItem
import io.wookoo.weekly.uimodels.UiCardInfoModel

internal fun cardInfoAdapterDelegate() =
    adapterDelegateViewBinding<UiCardInfoModel, DisplayableItem, InfoCardItemBinding>(
        { layoutInflater, root -> InfoCardItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            with(binding) {
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
