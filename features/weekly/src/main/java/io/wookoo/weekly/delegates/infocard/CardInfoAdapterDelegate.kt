package io.wookoo.weekly.delegates.infocard

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.design.system.databinding.InfoCardItemBinding
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.uimodels.UiCardInfoModel

internal fun cardInfoAdapterDelegate() =
    adapterDelegateViewBinding<UiCardInfoModel, DisplayableItem, InfoCardItemBinding>(
        { layoutInflater, root -> InfoCardItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            with(binding) {
                tempMin.text = item.tempMin
                tempMax.text = item.tempMax
                feelsLikeMinValue.text = item.feelsLikeMin
                feelsLikeMaxValue.text = item.feelsLikeMax
                weatherCondition.text = item.weatherCondition
            }
        }
    }
