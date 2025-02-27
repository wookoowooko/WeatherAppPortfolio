package io.wookoo.domain.repo

import io.wookoo.domain.model.CurrentWeatherResponseModel
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError

interface IMasterWeatherRepo {
    suspend fun getCurrentWeather(): AppResult<CurrentWeatherResponseModel, DataError.Remote>
}
