package io.wookoo.weekly.delegates.properties

import com.hannesdorfmann.adapterdelegates4.dsl.adapterDelegateViewBinding
import io.wookoo.design.system.databinding.PropertiesRecyclerBinding
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.adapters.PropertiesAdapter
import io.wookoo.weekly.uimodels.PropertiesContainer

internal fun propertiesContainerAdapter() = adapterDelegateViewBinding<
    PropertiesContainer,
    DisplayableItem,
    PropertiesRecyclerBinding
    >(
    { layoutInflater, root -> PropertiesRecyclerBinding.inflate(layoutInflater, root, false) }
) {
    val propsAdapter = PropertiesAdapter()
    binding.propsRecycler.adapter = propsAdapter

    bind {
        propsAdapter.items = item.listOfProperties
    }
}
