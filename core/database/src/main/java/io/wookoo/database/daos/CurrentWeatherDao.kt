package io.wookoo.database.daos

import androidx.room.Dao
import androidx.room.Insert
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

    @Query("UPDATE geo_entity SET is_current = 1 WHERE geo_name_id = :geoItemId")
    fun setCurrentLocation(geoItemId: Long)

    @Query("UPDATE geo_entity SET is_current = 0 WHERE geo_name_id != :geoItemId")
    fun clearOtherLocations(geoItemId: Long)

    @Transaction
    fun updateCurrentLocation(geoItemId: Long) {
        setCurrentLocation(geoItemId)
        clearOtherLocations(geoItemId)
    }

    @Transaction
    @Query("SELECT * FROM geo_entity WHERE geo_name_id = :geoItemId")
    fun getCurrentWeather(geoItemId: Long): Flow<WeatherWithDetails?>

    @Query(
        """
    SELECT geo_name_id 
    FROM geo_entity 
    ORDER BY 
        is_current DESC, 
        city_name ASC
    """
    )
    fun getCurrentWeatherIds(): Flow<List<Long>>

    @Transaction
    @Query(
        """
    SELECT * FROM geo_entity 
    ORDER BY 
        is_current DESC, 
        city_name ASC
        """
    )
    fun getAllCitiesCurrentWeather(): Flow<List<WeatherWithDetails>>

    @Transaction
    @Query("DELETE FROM geo_entity WHERE geo_name_id = :geoItemId")
    suspend fun deleteWeatherWithDetailsByGeoId(geoItemId: Long)

    @Query("SELECT last_update FROM current_weather WHERE currentId = :geoItemId")
    suspend fun getLastUpdateForCurrent(geoItemId: Long): Long

//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    suspend fun insertGeo(geo: GeoEntity): Long

    @Query(
        """
    INSERT OR REPLACE INTO geo_entity (geo_name_id, city_name, country_name, utc_offset_seconds, is_current)
    VALUES (:geoNameId, :cityName, :countryName, :utcOffsetSeconds, 
            CASE 
                WHEN (SELECT is_current FROM geo_entity WHERE geo_name_id = :geoNameId) = 1 THEN 1 
                ELSE :isCurrent 
            END)
        """
    )
    suspend fun insertGeo(
        geoNameId: Long,
        cityName: String,
        countryName: String,
        utcOffsetSeconds: Long,
        isCurrent: Boolean,
    ): Long

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
        val geoId = insertGeo(
            geo.geoNameId,
            geo.cityName,
            geo.countryName,
            geo.utcOffsetSeconds,
            geo.isCurrent
        )
        current.currentId = geoId
        hourly.hourlyId = geoId
        daily.dailyId = geoId
        insertCurrent(current)
        insertHourly(hourly)
        insertDaily(daily)
    }
}
