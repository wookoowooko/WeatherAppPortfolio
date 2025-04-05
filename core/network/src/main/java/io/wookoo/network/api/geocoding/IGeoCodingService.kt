package io.wookoo.network.api.geocoding

import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.dto.geocoding.GeocodingSearchDto

@CoveredByTest
interface IGeoCodingService {
    suspend fun searchLocation(
        name: String
    ): AppResult<GeocodingResponseDto, DataError.Remote>

    suspend fun getInfoByGeoItemId(
        geoItemId: Long
    ): AppResult<GeocodingSearchDto, DataError.Remote>
}