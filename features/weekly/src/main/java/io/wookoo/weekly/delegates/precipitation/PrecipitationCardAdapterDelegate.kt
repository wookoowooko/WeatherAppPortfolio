package io.wookoo.weekly.delegates.precipitation

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.design.system.databinding.PrecipitationsCardItemBinding
import io.wookoo.models.ui.DisplayableItem
import io.wookoo.models.ui.UIPrecipitationCardModel

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
                    item.total.value.asLocalizedUnitValueString(item.total.unit, context)
                totalShowersValue.text =
                    item.showersSum.value.asLocalizedUnitValueString(item.showersSum.unit, context)
                totalRainFallValue.text =
                    item.rainSum.value.asLocalizedUnitValueString(item.rainSum.unit, context)
                totalSnowFallValue.text =
                    item.snowSum.value.asLocalizedUnitValueString(item.snowSum.unit, context)
                precipitationProbabilityValue.text =
                    item.precipitationProbability.value.asLocalizedUnitValueString(
                        item.precipitationProbability.unit,
                        context
                    )
            }
        }
    }
