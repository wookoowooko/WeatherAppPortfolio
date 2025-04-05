package io.wookoo.network.api.reversegeocoding

import io.wookoo.domain.annotations.CoveredByTest
import io.wookoo.network.BuildConfig
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import java.util.Locale

@CoveredByTest
interface IReverseGeocodingRetrofit {

    @GET("findNearbyPlaceNameJSON")
    suspend fun getReversedSearchedLocation(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("lang") lang: String = Locale.getDefault().language.lowercase(),
        @Query("username") username: String = BuildConfig.REVERSE_GEOCODING_API_KEY,
    ): Response<ReverseGeocodingResponseDto>
}


