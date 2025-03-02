package io.wookoo.network.api.reversegeocoding

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import io.wookoo.network.extensions.safeCall
import javax.inject.Inject

class ReverseGeocodingServiceImpl @Inject constructor(
    private val reverseGeocodingApi: IReverseGeocodingSearchRetrofit,
) : IReverseGeoCodingService {
    override suspend fun getReversedSearchedLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<ReverseGeocodingResponseDto, DataError.Remote> {
        return safeCall {
            reverseGeocodingApi.getReversedSearchedLocation(
                latitude = latitude,
                longitude = longitude,
                lang = language
            )
        }
    }
}