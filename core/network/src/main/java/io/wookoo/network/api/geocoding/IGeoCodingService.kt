package io.wookoo.network.api.geocoding

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.geocoding.GeocodingResponseDto

interface IGeoCodingService {
    suspend fun getSearchedLocation(
        name: String,
        language: String,
    ): AppResult<GeocodingResponseDto, DataError.Remote>
}