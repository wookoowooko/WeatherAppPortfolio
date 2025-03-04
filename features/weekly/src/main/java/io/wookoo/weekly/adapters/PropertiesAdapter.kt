package io.wookoo.weekly.adapters

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.delegates.properties.propAdapterDelegate

internal class PropertiesAdapter : ListDelegationAdapter<List<DisplayableItem>>(
    propAdapterDelegate()
)
