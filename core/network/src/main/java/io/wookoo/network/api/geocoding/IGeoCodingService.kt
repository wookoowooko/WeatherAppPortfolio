package io.wookoo.network.api.geocoding

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.dto.geocoding.GeocodingSearchDto

interface IGeoCodingService {
    suspend fun searchLocation(
        name: String,
        language: String,
    ): AppResult<GeocodingResponseDto, DataError.Remote>

    suspend fun getInfoByGeoItemId(
        geoItemId: Long,
        language: String,
    ): AppResult<GeocodingSearchDto, DataError.Remote>
}