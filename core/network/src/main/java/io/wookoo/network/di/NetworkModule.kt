package io.wookoo.network.di

import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.WeatherApi
import io.wookoo.network.api.AppOkHttpClient
import io.wookoo.network.api.geocoding.GeocodingServiceImpl
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.geocoding.IGeocodingSearchRetrofit
import io.wookoo.network.api.weather.CurrentWeatherServiceImpl
import io.wookoo.network.api.weather.ICurrentWeatherService
import io.wookoo.network.api.weather.IWeatherRetrofit
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Provides
    @Singleton
    fun provideNetworkClient(): AppOkHttpClient {
        return AppOkHttpClient()
    }

    @Provides
    @Singleton
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
        }
    }

    @Provides
    @Singleton
    @WeatherApi
    fun provideWeatherApiRetrofit(client: AppOkHttpClient, json: Json): Retrofit {
        return Retrofit.Builder()
            .baseUrl(AppOkHttpClient.baseUrlWeather)
            .client(client.getClient())
            .addConverterFactory(json.asConverterFactory("application/json; charset=UTF8".toMediaType()))
            .build()
    }

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
    fun provideIWeatherRetrofit(@WeatherApi retrofit: Retrofit): IWeatherRetrofit {
        return retrofit.create(IWeatherRetrofit::class.java)
    }

    @Provides
    @Singleton
    fun provideIGeocodingSearch(@GeoCodingApi retrofit: Retrofit): IGeocodingSearchRetrofit {
        return retrofit.create(IGeocodingSearchRetrofit::class.java)
    }
}


@Module
@InstallIn(SingletonComponent::class)
interface NetworkInterfaceModule {

    @Binds
    @WeatherApi
    fun bindsICurrentWeatherService(
        currentWeatherServiceImpl: CurrentWeatherServiceImpl,
    ): ICurrentWeatherService

    @Binds
    @GeoCodingApi
    fun bindsIGeoCodingService(
        geocodingServiceImpl: GeocodingServiceImpl,
    ): IGeoCodingService

}