package io.wookoo.domain.repo

import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.models.geocoding.GeocodingResponseDomainUi

interface IGeoRepo {

    suspend fun searchLocationFromApiByQuery(
        query: String,
        language: String,
    ): AppResult<io.wookoo.models.geocoding.GeocodingResponseDomainUi, DataError.Remote>

    suspend fun getReverseGeocodingLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<io.wookoo.models.geocoding.GeocodingResponseDomainUi, DataError.Remote>
}
