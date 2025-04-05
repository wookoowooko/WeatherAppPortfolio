package io.wookoo.network.api.geocoding

import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.dto.geocoding.GeocodingSearchDto
import io.wookoo.network.extensions.safeCall
import javax.inject.Inject

@CoveredByTest
class GeocodingServiceImpl @Inject constructor(
    private val geocodingApi: IGeocodingRetrofit,
) : IGeoCodingService {

    override suspend fun searchLocation(
        name: String
    ): AppResult<GeocodingResponseDto, DataError.Remote> {
        return safeCall {
            geocodingApi.searchLocation(name)
        }
    }

    override suspend fun getInfoByGeoItemId(geoItemId: Long)
            : AppResult<GeocodingSearchDto, DataError.Remote> {
        return safeCall {
            geocodingApi.getInfoByGeoItemId(geoItemId)
        }
    }
}