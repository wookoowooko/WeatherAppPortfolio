package io.wookoo.weekly.delegates.infocard

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.common.asUnitValueWithStringRes
import io.wookoo.common.asIntAsString
import io.wookoo.common.toUiWeather
import io.wookoo.design.system.databinding.InfoCardItemBinding
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.uimodels.UiCardInfoModel

internal fun cardInfoAdapterDelegate() =
    adapterDelegateViewBinding<UiCardInfoModel, DisplayableItem, InfoCardItemBinding>(
        { layoutInflater, root -> InfoCardItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            with(binding) {

                tempMin.text = item.tempMin.value.asIntAsString()
                tempMax.text =
                    item.tempMax.value.asUnitValueWithStringRes(item.tempMax.unit, context)
                feelsLikeMinValue.text =
                    item.feelsLikeMax.value.asUnitValueWithStringRes(item.feelsLikeMax.unit, context)
                feelsLikeMaxValue.text =
                    item.feelsLikeMin.value.asUnitValueWithStringRes(item.feelsLikeMax.unit, context)
                weatherConditionText.setText(item.weatherCondition.toUiWeather(item.isDay).second)
                weatherCodeImage.setImageResource(item.weatherCondition.toUiWeather(item.isDay).first)
            }
        }
    }
