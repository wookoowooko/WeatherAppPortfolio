package io.wookoo.network.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.network.api.AppOkHttpClient
import io.wookoo.network.api.geocoding.IGeocodingRetrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object GeoCodingModule {

    @Provides
    @Singleton
    @GeoCodingApi
    fun provideGeoCodingApiRetrofit(client: AppOkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppOkHttpClient.baseUrlGeoCoding)
            .client(client.getClient())
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }


    @Provides
    @Singleton
    fun provideIGeocodingSearch(@GeoCodingApi retrofit: Retrofit): IGeocodingRetrofit {
        return retrofit.create(IGeocodingRetrofit::class.java)
    }
}