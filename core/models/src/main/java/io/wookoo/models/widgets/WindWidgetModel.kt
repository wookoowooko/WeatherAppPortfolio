package io.wookoo.models.widgets

data class WindWidgetModel(
    val windSpeed: String,
    val windDirection: String,
    val windGust: String,
    val windSpeedTitle: String,
    val windDirectionTitle: String,
    val windGustTitle: String,
    val widgetTitle : String,
)