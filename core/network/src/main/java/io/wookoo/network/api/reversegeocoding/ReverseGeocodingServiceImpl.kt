package io.wookoo.network.api.reversegeocoding

import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import io.wookoo.network.extensions.safeCall
import javax.inject.Inject
@CoveredByTest
class ReverseGeocodingServiceImpl @Inject constructor(
    private val reverseGeocodingApi: IReverseGeocodingRetrofit,
) : IReverseGeoCodingService {
    override suspend fun getReversedSearchedLocation(
        latitude: Double,
        longitude: Double,
    ): AppResult<ReverseGeocodingResponseDto, DataError.Remote> {
        return safeCall {
            reverseGeocodingApi.getReversedSearchedLocation(
                latitude = latitude,
                longitude = longitude
            )
        }
    }
}