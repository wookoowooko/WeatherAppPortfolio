package io.wookoo.data.repo

import io.wookoo.data.di.dispatchers.AppDispatchers
import io.wookoo.data.di.dispatchers.Dispatcher
import io.wookoo.data.mappers.asCurrentWeatherResponseModel
import io.wookoo.domain.model.CurrentWeatherResponseModel
import io.wookoo.domain.repo.IMasterWeatherRepo
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.domain.utils.map
import io.wookoo.network.api.IWeatherService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class MasterRepoImpl @Inject constructor(
    private val remoteDataSource: IWeatherService,
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,

) : IMasterWeatherRepo {
    override suspend fun getCurrentWeather(): AppResult<CurrentWeatherResponseModel, DataError.Remote> {
        return withContext(ioDispatcher) {
            remoteDataSource.getCurrentWeather()
                .map { dto ->
                    dto.asCurrentWeatherResponseModel()
                }
        }
    }
}
