package io.wookoo.network.demo

import io.wookoo.domain.annotations.AppDispatchers
import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.domain.annotations.Dispatcher
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.reversegeocoding.IReverseGeoCodingService
import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.dto.geocoding.GeocodingSearchDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
@CoveredByTest
class DemoGeocodingImpl(
    @Dispatcher(AppDispatchers.IO) private val ioDispatcher: CoroutineDispatcher,
    private val demoAssetManager: IDemoAssetManager,
    private val networkJson: Json,
) : IGeoCodingService, IReverseGeoCodingService {

    override suspend fun searchLocation(name: String): AppResult<GeocodingResponseDto, DataError.Remote> {
        return AppResult.Success(
            data = getDataFromJsonFile<GeocodingResponseDto>(LOCATION_RESULTS)
        )
    }

    override suspend fun getInfoByGeoItemId(geoItemId: Long): AppResult<GeocodingSearchDto, DataError.Remote> {
        return AppResult.Success(
            data = getDataFromJsonFile<GeocodingSearchDto>(LOCATION_BY_ID)
        )
    }

    override suspend fun getReversedSearchedLocation(
        latitude: Double,
        longitude: Double,
    ): AppResult<ReverseGeocodingResponseDto, DataError.Remote> {
        return AppResult.Success(
            data = getDataFromJsonFile<ReverseGeocodingResponseDto>(REVERSED_SEARCHED_LOCATION)
        )
    }

    /**
     * Get data from the given JSON [fileName].
     */
    @OptIn(ExperimentalSerializationApi::class)
    private suspend inline fun <reified T> getDataFromJsonFile(fileName: String): T =
        withContext(ioDispatcher) {
            demoAssetManager.open(fileName).use { inputStream ->
                networkJson.decodeFromStream(inputStream)
            }
        }


    companion object {
        private const val LOCATION_RESULTS = "locations_results.json"
        private const val LOCATION_BY_ID = "geo_item_by_id.json"
        private const val REVERSED_SEARCHED_LOCATION = "reversed_searched_location.json"
    }
}