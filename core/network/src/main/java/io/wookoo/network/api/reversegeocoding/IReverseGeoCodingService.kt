package io.wookoo.network.api.reversegeocoding

import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
@CoveredByTest
interface IReverseGeoCodingService {
    suspend fun getReversedSearchedLocation(
        latitude: Double,
        longitude: Double
    ): AppResult<ReverseGeocodingResponseDto, DataError.Remote>
}