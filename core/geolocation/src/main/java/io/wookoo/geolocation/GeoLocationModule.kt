package io.wookoo.geolocation

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class GeoLocationModule {
    @Provides
    fun provideLocationManager(@ApplicationContext context: Context): WeatherLocationManager {
        return WeatherLocationManager(context)
    }
}
