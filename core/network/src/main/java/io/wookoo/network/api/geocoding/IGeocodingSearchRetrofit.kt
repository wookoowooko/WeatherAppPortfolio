package io.wookoo.network.api.geocoding

import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.dto.geocoding.GeocodingSearchDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query


interface IGeocodingSearchRetrofit {

    @GET("search")
    suspend fun searchLocation(
        @Query("name") name: String,
        @Query("language") language: String,
        @Query("count") count: Int = COUNT,
        @Query("format") latitude: String = FORMAT,
    ): Response<GeocodingResponseDto>


    @GET("get")
    suspend fun getInfoByGeoItemId(
        @Query("id") geoItemId: Long,
        @Query("language") language: String,
    ): Response<GeocodingSearchDto>

    private companion object {
        private const val FORMAT = "json"
        private const val COUNT = 10
    }

}


