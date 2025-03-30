package io.wookoo.weekly.adapters

import android.annotation.SuppressLint
import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import io.wookoo.models.ui.DisplayableItem
import io.wookoo.weekly.delegates.infocard.cardInfoAdapterDelegate
import io.wookoo.weekly.delegates.other.otherAdapterDelegate
import io.wookoo.weekly.delegates.precipitation.precipitationCardAdapterDelegate
import io.wookoo.weekly.delegates.suncycles.sunCyclesCardAdapterDelegate
import io.wookoo.weekly.delegates.windcard.windCardAdapterDelegate

class MainAdapter : AsyncListDifferDelegationAdapter<DisplayableItem>(diffCallback) {
    init {
        delegatesManager
            .addDelegate(cardInfoAdapterDelegate())
            .addDelegate(windCardAdapterDelegate())
            .addDelegate(precipitationCardAdapterDelegate())
            .addDelegate(sunCyclesCardAdapterDelegate())
            .addDelegate(otherAdapterDelegate())
    }
}

val diffCallback = object : DiffUtil.ItemCallback<DisplayableItem>() {
    override fun areItemsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem): Boolean {
        return oldItem.id() == newItem.id()
    }

    @SuppressLint("DiffUtilEquals")
    override fun areContentsTheSame(oldItem: DisplayableItem, newItem: DisplayableItem): Boolean {
        return oldItem.content() == newItem.content()
    }

    override fun getChangePayload(
        oldItem: DisplayableItem,
        newItem: DisplayableItem,
    ): Any = oldItem.payload(newItem)
}
