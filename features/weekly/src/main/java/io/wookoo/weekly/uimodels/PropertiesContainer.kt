package io.wookoo.weekly.uimodels

import io.wookoo.weekly.DisplayableItem

internal data class PropertiesContainer(
    val listOfProperties: List<UIPropModel>,
) : DisplayableItem {
    override fun id(): Any {
        return listOfProperties.hashCode()
    }

    override fun content(): Any {
        return listOfProperties
    }
}
