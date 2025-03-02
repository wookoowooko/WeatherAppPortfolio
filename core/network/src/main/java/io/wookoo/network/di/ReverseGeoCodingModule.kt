package io.wookoo.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.ReverseGeoCodingApi
import io.wookoo.network.api.AppOkHttpClient
import io.wookoo.network.api.geocoding.IGeocodingSearchRetrofit
import io.wookoo.network.api.reversegeocoding.IReverseGeocodingSearchRetrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ReverseGeoCodingModule {

    @Provides
    @Singleton
    @ReverseGeoCodingApi
    fun provideReverseGeoCodingApiRetrofit(client: AppOkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppOkHttpClient.baseReverseGeoCodingUrl)
            .client(client.getClient())
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }


    @Provides
    @Singleton
    fun provideIReverseGeocodingSearch(@ReverseGeoCodingApi retrofit: Retrofit): IReverseGeocodingSearchRetrofit {
        return retrofit.create(IReverseGeocodingSearchRetrofit::class.java)
    }
}