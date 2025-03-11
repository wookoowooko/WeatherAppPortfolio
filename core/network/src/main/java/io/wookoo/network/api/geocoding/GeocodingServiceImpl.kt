package io.wookoo.network.api.geocoding

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.extensions.safeCall
import javax.inject.Inject

class GeocodingServiceImpl @Inject constructor(
    private val geocodingApi: IGeocodingSearchRetrofit,
) : IGeoCodingService {

    override suspend fun getSearchedLocation(
        name: String,
        language: String,
    ): AppResult<GeocodingResponseDto, DataError.Remote> {
        return safeCall {
            geocodingApi.searchLocation(name, language)
        }
    }
}