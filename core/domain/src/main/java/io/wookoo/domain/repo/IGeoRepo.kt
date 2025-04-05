package io.wookoo.domain.repo

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.models.geocoding.GeocodingResponseDomainUi

interface IGeoRepo {

    suspend fun searchLocationFromApiByQuery(
        query: String
    ): AppResult<GeocodingResponseDomainUi, DataError.Remote>

    suspend fun getReverseGeocodingLocation(
        latitude: Double,
        longitude: Double,
    ): AppResult<GeocodingResponseDomainUi, DataError.Remote>
}
