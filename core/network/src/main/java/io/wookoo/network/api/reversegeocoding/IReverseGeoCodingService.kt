package io.wookoo.network.api.reversegeocoding

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto

interface IReverseGeoCodingService {
    suspend fun getReversedSearchedLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<ReverseGeocodingResponseDto, DataError.Remote>
}