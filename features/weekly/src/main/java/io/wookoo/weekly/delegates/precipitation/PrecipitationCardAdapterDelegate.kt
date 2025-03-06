package io.wookoo.weekly.delegates.precipitation

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.common.asUnitValueWithStringRes
import io.wookoo.design.system.databinding.PrecipitationsCardItemBinding
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.uimodels.UIPrecipitationCardModel

internal fun precipitationCardAdapterDelegate() =
    adapterDelegateViewBinding<UIPrecipitationCardModel, DisplayableItem, PrecipitationsCardItemBinding>(
        { layoutInflater, root ->
            PrecipitationsCardItemBinding.inflate(
                layoutInflater,
                root,
                false
            )
        }
    ) {
        bind {
            with(binding) {
                totalPrecipitationValue.text =
                    item.total.value.asUnitValueWithStringRes(item.total.unit, context)
                totalShowersValue.text =
                    item.showersSum.value.asUnitValueWithStringRes(item.showersSum.unit, context)
                totalRainFallValue.text =
                    item.rainSum.value.asUnitValueWithStringRes(item.rainSum.unit, context)
                totalSnowFallValue.text =
                    item.snowSum.value.asUnitValueWithStringRes(item.snowSum.unit, context)
                precipitationProbabilityValue.text =
                    item.precipitationProbability.value.asUnitValueWithStringRes(
                        item.precipitationProbability.unit,
                        context
                    )
            }
        }
    }
