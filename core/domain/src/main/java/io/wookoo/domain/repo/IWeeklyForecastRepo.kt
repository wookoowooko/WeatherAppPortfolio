package io.wookoo.domain.repo

import io.wookoo.domain.sync.Syncable
import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI
import kotlinx.coroutines.flow.Flow

interface IWeeklyForecastRepo : Syncable {
    fun getWeeklyForecastByGeoItemId(geoNameId: Long): Flow<WeeklyWeatherDomainUI>
}
