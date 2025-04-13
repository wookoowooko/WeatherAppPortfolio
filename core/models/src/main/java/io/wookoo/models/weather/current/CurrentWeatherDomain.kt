package io.wookoo.models.weather.current

import io.wookoo.models.geocoding.GeocodingDomainUI
import io.wookoo.models.weather.current.additional.CurrentDayModel
import io.wookoo.models.weather.current.additional.DailyModel
import io.wookoo.models.weather.current.additional.HourlyModel

data class CurrentWeatherDomain(
    val isCurrentLocation: Boolean,
    val time: Long,
    val geo: GeocodingDomainUI,
    val current: CurrentDayModel,
    val hourly: HourlyModel,
    val daily: DailyModel,
    val utcOffsetSeconds: Long,

)
