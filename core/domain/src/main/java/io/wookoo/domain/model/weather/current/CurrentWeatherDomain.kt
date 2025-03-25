package io.wookoo.domain.model.weather.current

import io.wookoo.domain.model.geocoding.GeocodingDomainUI
import io.wookoo.domain.model.weather.current.additional.CurrentDayModel
import io.wookoo.domain.model.weather.current.additional.DailyModel
import io.wookoo.domain.model.weather.current.additional.HourlyModel

data class CurrentWeatherDomain(
    val isCurrentLocation: Boolean,
    val time: Long,
    val geo: GeocodingDomainUI,
    val current: CurrentDayModel,
    val hourly: HourlyModel,
    val daily: DailyModel,
    val utcOffsetSeconds: Long,
)


