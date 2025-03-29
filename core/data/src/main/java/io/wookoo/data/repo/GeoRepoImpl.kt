package io.wookoo.data.repo

import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.ReverseGeoCodingApi
import io.wookoo.models.geocoding.GeocodingResponseDomainUi
import io.wookoo.domain.repo.IGeoRepo
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.domain.utils.map
import io.wookoo.mappers.geocoding.asGeocodingResponseModel
import io.wookoo.mappers.geocoding.asReverseGeocodingResponseModel
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.reversegeocoding.IReverseGeoCodingService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class GeoRepoImpl @Inject constructor(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    @GeoCodingApi private val geoCodingRemoteDataSource: IGeoCodingService,
    @ReverseGeoCodingApi private val reverseGeoCodingRemoteDataSource: IReverseGeoCodingService
) : IGeoRepo {

    override suspend fun searchLocationFromApiByQuery(
        query: String,
        language: String,
    ): AppResult<io.wookoo.models.geocoding.GeocodingResponseDomainUi, DataError.Remote> {
        return withContext(ioDispatcher) {
            geoCodingRemoteDataSource.searchLocation(
                name = query,
                language = language
            ).map { dto ->
                dto.asGeocodingResponseModel()
            }
        }
    }

    override suspend fun getReverseGeocodingLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<io.wookoo.models.geocoding.GeocodingResponseDomainUi, DataError.Remote> {
        return withContext(ioDispatcher) {
            reverseGeoCodingRemoteDataSource.getReversedSearchedLocation(
                latitude = latitude,
                longitude = longitude,
                language = language
            ).map { dto ->
                dto.asReverseGeocodingResponseModel()
            }
        }
    }
}
