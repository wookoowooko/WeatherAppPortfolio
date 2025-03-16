package io.wookoo.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.wookoo.database.dbo.CurrentWeatherEntity
import io.wookoo.database.dbo.DailyEntity
import io.wookoo.database.dbo.GeoEntity
import io.wookoo.database.dbo.HourlyEntity
import io.wookoo.database.relations.WeatherWithDetails
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao {

    @Transaction
    @Query("SELECT * FROM geo_entity WHERE geo_name_id = :geoItemId")
    fun getCurrentWeather(geoItemId: Long): Flow<WeatherWithDetails?>

    @Query("SELECT geo_name_id FROM geo_entity")
    fun getCurrentWeatherIds(): Flow<List<Long>>

    @Transaction
    @Query("SELECT * FROM geo_entity")
    fun getAllCitiesCurrentWeather(): Flow<List<WeatherWithDetails>>


    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertGeo(geo: GeoEntity): Long

    @Insert
    suspend fun insertCurrent(current: CurrentWeatherEntity)

    @Insert
    suspend fun insertHourly(hourly: HourlyEntity)

    @Insert
    suspend fun insertDaily(daily: DailyEntity)


    @Transaction
    suspend fun insertFullWeather(
        geo: GeoEntity,
        current: CurrentWeatherEntity,
        hourly: HourlyEntity,
        daily: DailyEntity,
    ) {
        val geoId = insertGeo(geo)
        current.currentId = geoId
        hourly.hourlyId = geoId
        daily.dailyId = geoId
        insertCurrent(current)
        insertHourly(hourly)
        insertDaily(daily)
    }
}