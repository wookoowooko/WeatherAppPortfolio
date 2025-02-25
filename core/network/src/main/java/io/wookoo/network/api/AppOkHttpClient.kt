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
        private const val BASE_URL =
            "https://api.open-meteo.com/v1/"

        val baseUrl: String
            get() = BASE_URL
    }
}