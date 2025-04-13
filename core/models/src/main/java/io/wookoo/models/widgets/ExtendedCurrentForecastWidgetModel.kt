package io.wookoo.models.widgets

data class ExtendedCurrentForecastWidgetModel(
    val currentForecastWidgetModel: CurrentForecastWidgetModel,
    val extendedHourlyWidgetModel: List<ExtendedHourlyWidgetModel>,
) {
    data class ExtendedHourlyWidgetModel(
        val time: String,
        val temp: String,
        val weatherImage: Int,
        val isNow: Boolean
    )
}
