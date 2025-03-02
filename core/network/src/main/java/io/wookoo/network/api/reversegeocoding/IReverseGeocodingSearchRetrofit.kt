package io.wookoo.network.api.reversegeocoding

import io.wookoo.network.BuildConfig
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface IReverseGeocodingSearchRetrofit {

    @GET("findNearbyPlaceNameJSON")
    suspend fun getReversedSearchedLocation(
        @Query("lat") latitude: Double,
        @Query("lng") longitude: Double,
        @Query("lang") lang: String,
        @Query("username") username: String = BuildConfig.REVERSE_GEOCODING_API_KEY,
    ): Response<ReverseGeocodingResponseDto>
}


