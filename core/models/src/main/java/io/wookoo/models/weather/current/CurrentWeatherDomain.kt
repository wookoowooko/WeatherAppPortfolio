package io.wookoo.models.weather.current

import io.wookoo.models.geocoding.GeocodingDomainUI
import io.wookoo.models.weather.current.additional.CurrentDayModel
import io.wookoo.models.weather.current.additional.DailyModel
import io.wookoo.models.weather.current.additional.HourlyModel

data class CurrentWeatherDomain(
    val isCurrentLocation: Boolean,
    val time: Long,
    val geo: io.wookoo.models.geocoding.GeocodingDomainUI,
    val current: io.wookoo.models.weather.current.additional.CurrentDayModel,
    val hourly: io.wookoo.models.weather.current.additional.HourlyModel,
    val daily: io.wookoo.models.weather.current.additional.DailyModel,
    val utcOffsetSeconds: Long,
)
