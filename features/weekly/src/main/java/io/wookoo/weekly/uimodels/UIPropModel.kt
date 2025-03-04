package io.wookoo.weekly.uimodels

import androidx.annotation.DrawableRes
import io.wookoo.weekly.DisplayableItem

internal data class UIPropModel(
    val propName: String = "Humidity",
    val minValue: String = "12km/h",
    val maxValue: String = "15km/h",
    @DrawableRes val imageRes: Int = io.wookoo.design.system.R.drawable.ic_rain_heavy,
) : DisplayableItem
