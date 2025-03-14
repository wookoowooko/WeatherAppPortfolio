package io.wookoo.database.weatherdatabase

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.wookoo.database.converters.PrimitiveConverters
import io.wookoo.database.daos.CurrentWeatherDao
import io.wookoo.database.dbo.CurrentWeatherEntity
import io.wookoo.database.dbo.DailyEntity
import io.wookoo.database.dbo.HourlyEntity
import io.wookoo.database.dbo.GeoEntity
import io.wookoo.database.weatherdatabase.WeatherDataBase.Companion.LATEST_VERSION

@Database(
    entities = [
        GeoEntity::class,
        DailyEntity::class,
        HourlyEntity::class,
        CurrentWeatherEntity::class
    ],
    version = LATEST_VERSION,

    autoMigrations = [],
    exportSchema = true,
)
@TypeConverters(
    PrimitiveConverters::class,
)
abstract class WeatherDataBase : RoomDatabase() {

    companion object {
        const val LATEST_VERSION = 1
    }

    abstract fun currentWeatherDao(): CurrentWeatherDao
}
