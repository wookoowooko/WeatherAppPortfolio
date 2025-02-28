package io.wookoo.network.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor

class AppOkHttpClient {

    private val networkClient: OkHttpClient = OkHttpClient.Builder()
        .addInterceptor(
            HttpLoggingInterceptor()
                .setLevel(HttpLoggingInterceptor.Level.BODY)
        ).build()

    fun getClient(): OkHttpClient {
        return networkClient
    }

    companion object {
        private const val BASE_URL_WEATHER =
            "https://api.open-meteo.com/v1/"

        private const val BASE_URL_GEOCODING =
            "https://geocoding-api.open-meteo.com/v1/"

        val baseUrlWeather: String
            get() = BASE_URL_WEATHER

        val baseUrlGeoCoding: String
            get() = BASE_URL_GEOCODING
    }
}