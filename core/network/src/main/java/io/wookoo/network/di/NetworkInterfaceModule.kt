package io.wookoo.network.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.domain.annotations.GeoCodingApi
import io.wookoo.domain.annotations.ReverseGeoCodingApi
import io.wookoo.domain.annotations.WeatherApi
import io.wookoo.network.api.geocoding.GeocodingServiceImpl
import io.wookoo.network.api.geocoding.IGeoCodingService
import io.wookoo.network.api.reversegeocoding.IReverseGeoCodingService
import io.wookoo.network.api.reversegeocoding.ReverseGeocodingServiceImpl
import io.wookoo.network.api.weather.CurrentWeatherServiceImpl
import io.wookoo.network.api.weather.ICurrentWeatherService

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

    @Binds
    @ReverseGeoCodingApi
    fun bindIReverseGeoCodingService(
        reverseGeocodingServiceImpl: ReverseGeocodingServiceImpl,
    ): IReverseGeoCodingService

}