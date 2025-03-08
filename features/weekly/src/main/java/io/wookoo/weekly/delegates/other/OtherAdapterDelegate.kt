package io.wookoo.weekly.delegates.other

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.design.system.databinding.OthersCardItemBinding
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.uimodels.UiOtherPropsModel

internal fun otherAdapterDelegate() =
    adapterDelegateViewBinding<UiOtherPropsModel, DisplayableItem, OthersCardItemBinding>(
        { layoutInflater, root -> OthersCardItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            with(binding) {
                dayLightDurationValue.text =
                    item.dayLightDuration
                        .hour.value.asLocalizedUnitValueString(
                            item.dayLightDuration.hour.unit,
                            context
                        )

                sunShineDurationValue.text =
                    item.sunShineDuration
                        .hour.value.asLocalizedUnitValueString(
                            item.sunShineDuration.hour.unit,
                            context
                        )
                maxUvIndexValue.text = item.maxUvIndex
            }
        }
    }
