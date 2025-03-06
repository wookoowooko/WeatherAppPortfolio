package io.wookoo.weekly.delegates.other

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.common.asIntAsString
import io.wookoo.common.asUnitValueWithStringRes
import io.wookoo.design.system.databinding.OthersCardItemBinding
import io.wookoo.domain.units.WeatherValueWithUnit
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
                        .hour.value.asUnitValueWithStringRes(
                            item.dayLightDuration.hour.unit,
                            context
                        )

                sunShineDurationValue.text =
                    item.sunShineDuration
                        .hour.value.asUnitValueWithStringRes(
                            item.sunShineDuration.hour.unit,
                            context
                        )

                maxUvIndexValue.text =
                    item.maxUvIndex.let {
                        if (it.unit == null) {
                            it.value.asIntAsString()
                        } else {
                            it.value.asUnitValueWithStringRes(it.unit, context)
                        }
                    }
            }
        }
    }

