package io.wookoo.geolocation

import android.content.Context
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class GeoLocationModule {
    @Singleton
    @Provides
    fun provideLocationManager(
        @ApplicationContext context: Context,
        client: FusedLocationProviderClient,
    ): WeatherLocationManager {
        return WeatherLocationManager(context, client)
    }

    @Singleton
    @Provides
    fun provideFusedLocationClient(@ApplicationContext context: Context): FusedLocationProviderClient {
        return LocationServices.getFusedLocationProviderClient(context)
    }
}
