package io.wookoo.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.wookoo.database.weatherdatabase.WeatherDataBase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object DatabaseModule {

    private const val DATABASE_NAME = "weather_db"

    @Provides
    @Singleton
    fun providesWeatherDatabase(
        @ApplicationContext context: Context,
    ): WeatherDataBase = Room.databaseBuilder(
        context,
        WeatherDataBase::class.java,
        DATABASE_NAME,
    )
        .addMigrations()
        .build()
}
