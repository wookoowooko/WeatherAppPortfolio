package io.wookoo.weekly.delegates.suncycles

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.design.system.databinding.SuncyclesCardItemBinding
import io.wookoo.models.ui.DisplayableItem
import io.wookoo.models.ui.UiSuncyclesModel

internal fun sunCyclesCardAdapterDelegate() =
    adapterDelegateViewBinding<UiSuncyclesModel, DisplayableItem, SuncyclesCardItemBinding>(
        { layoutInflater, root -> SuncyclesCardItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            with(binding) {
                sunriseValue.text = item.sunriseTime.value
                sunsetValue.text = item.sunsetTime.value
            }
        }
    }
