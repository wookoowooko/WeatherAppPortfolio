package io.wookoo.database.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.wookoo.database.migrations.DatabaseMigrations
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
    ).addMigrations(
        DatabaseMigrations.MIGRATION_1_2,
        DatabaseMigrations.MIGRATION_2_3,
        DatabaseMigrations.MIGRATION_3_4,
        DatabaseMigrations.MIGRATION_4_5,
        DatabaseMigrations.MIGRATION_5_6,
        DatabaseMigrations.MIGRATION_6_7,
        DatabaseMigrations.MIGRATION_7_8,
        DatabaseMigrations.MIGRATION_8_9,
    )
        .build()
}
