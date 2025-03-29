package io.wookoo.data.repo

import io.wookoo.database.daos.WeeklyWeatherDao
import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.model.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.domain.repo.IWeeklyForecastRepo
import io.wookoo.mappers.weeklyweather.asWeeklyWeatherResponseModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.mapNotNull
import javax.inject.Inject

class WeeklyForecastImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val weeklyWeatherDao: WeeklyWeatherDao,
) : IWeeklyForecastRepo {

    override fun getWeeklyForecastByGeoItemId(geoNameId: Long): Flow<WeeklyWeatherDomainUI> {
        return weeklyWeatherDao.getWeeklyForecastByGeoItemId(geoNameId)
            .mapNotNull {
                it?.asWeeklyWeatherResponseModel()
            }.flowOn(ioDispatcher)
    }
}
