package io.wookoo.database.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.database.weatherdatabase.WeatherDataBase

@Module
@InstallIn(SingletonComponent::class)
internal object DaosModule {
    @Provides
    fun providesCurrentWeatherDao(database: WeatherDataBase) = database.currentWeatherDao()

    @Provides
    fun providesWeeklyWeatherDao(database: WeatherDataBase) = database.weeklyWeatherDao()

    @Provides
    fun provideDeleteForecastsDao(database: WeatherDataBase) = database.deleteForecastsDao()
}
