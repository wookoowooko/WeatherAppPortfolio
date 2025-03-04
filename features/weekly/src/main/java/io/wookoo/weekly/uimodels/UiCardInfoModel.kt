package io.wookoo.weekly.uimodels

import io.wookoo.weekly.DisplayableItem

internal data class UiCardInfoModel(
    val tempMax: String = "22",
    val tempMin: String,
    val feelsLikeMin: String,
    val feelsLikeMax: String,
    val weatherCondition: String
) : DisplayableItem
