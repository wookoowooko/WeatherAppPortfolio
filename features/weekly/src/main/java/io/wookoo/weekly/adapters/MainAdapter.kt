package io.wookoo.weekly.adapters

import com.hannesdorfmann.adapterdelegates4.ListDelegationAdapter
import io.wookoo.weekly.DisplayableItem
import io.wookoo.weekly.delegates.calendar.calendarContainerAdapter
import io.wookoo.weekly.delegates.infocard.cardInfoAdapterDelegate
import io.wookoo.weekly.delegates.properties.propertiesContainerAdapter

internal class MainAdapter : ListDelegationAdapter<List<DisplayableItem>>(
    calendarContainerAdapter(),
    cardInfoAdapterDelegate(),
    propertiesContainerAdapter()

)
