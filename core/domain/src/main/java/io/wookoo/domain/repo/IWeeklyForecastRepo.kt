package io.wookoo.domain.repo

import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI
import kotlinx.coroutines.flow.Flow

interface IWeeklyForecastRepo {

    fun getWeeklyForecastByGeoItemId(geoNameId: Long): Flow<WeeklyWeatherDomainUI>
}
