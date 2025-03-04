package io.wookoo.weekly.delegates.properties

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.design.system.databinding.PropsRecyclerItemBinding
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.uimodels.UIPropModel

internal fun propAdapterDelegate() =
    adapterDelegateViewBinding<UIPropModel, DisplayableItem, PropsRecyclerItemBinding>(
        { layoutInflater, root -> PropsRecyclerItemBinding.inflate(layoutInflater, root, false) }
    ) {
        bind {
            with(binding) {
                propName.text = item.propName
                maxValue.text = item.maxValue
                minValue.text = item.minValue
                weatherCodePropIcon.setImageResource(io.wookoo.design.system.R.drawable.ic_rain_heavy)
            }
        }
    }
