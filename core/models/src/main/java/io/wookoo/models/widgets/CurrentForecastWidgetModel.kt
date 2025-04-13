package io.wookoo.models.widgets

data class CurrentForecastWidgetModel(
    val city: String,
    val temp: String,
    val maxTemp: String,
    val minTemp: String,
    val weatherImage: Int,
    val weatherCondition: String
)
