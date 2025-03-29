package io.wookoo.domain.repo

import io.wookoo.domain.model.geocoding.GeocodingResponseDomainUi
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError

interface IGeoRepo {

    suspend fun searchLocationFromApiByQuery(
        query: String,
        language: String,
    ): AppResult<GeocodingResponseDomainUi, DataError.Remote>

    suspend fun getReverseGeocodingLocation(
        latitude: Double,
        longitude: Double,
        language: String,
    ): AppResult<GeocodingResponseDomainUi, DataError.Remote>
}
