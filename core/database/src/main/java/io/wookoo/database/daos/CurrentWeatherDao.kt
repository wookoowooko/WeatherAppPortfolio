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
import io.wookoo.domain.annotations.CoveredByTest
import kotlinx.coroutines.flow.Flow

@Dao
interface CurrentWeatherDao {

    @CoveredByTest
    @Query("UPDATE geo_entity SET is_current = 1 WHERE geo_name_id = :geoItemId")
    suspend fun setSelectedCurrentForecastLocationAsDefault(geoItemId: Long)

    @CoveredByTest
    @Query("UPDATE geo_entity SET is_current = 0 WHERE geo_name_id != :geoItemId")
    suspend fun clearOtherCurrentForecastLocations(geoItemId: Long)

    @Transaction
    suspend fun updateCurrentForecastLocation(geoItemId: Long) {
        setSelectedCurrentForecastLocationAsDefault(geoItemId)
        clearOtherCurrentForecastLocations(geoItemId)
    }

    @CoveredByTest
    @Transaction
    @Query("SELECT * FROM geo_entity WHERE geo_name_id = :geoItemId")
    fun getCurrentForecast(geoItemId: Long): Flow<WeatherWithDetails?>

    @CoveredByTest
    @Query(
        """
    SELECT geo_name_id 
    FROM geo_entity 
    ORDER BY 
        is_current DESC, 
        city_name ASC
    """
    )
    fun getCurrentForecastGeoItemIds(): Flow<List<Long>>

    @CoveredByTest
    @Transaction
    @Query(
        """
    SELECT * FROM geo_entity 
    ORDER BY 
        is_current DESC, 
        city_name ASC
        """
    )
    fun getAllCurrentForecastLocations(): Flow<List<WeatherWithDetails>>

    @CoveredByTest
    @Transaction
    @Query("DELETE FROM geo_entity WHERE geo_name_id = :geoItemId")
    suspend fun deleteCurrentForecastEntryByGeoId(geoItemId: Long)

    @Query("SELECT last_update FROM current_weather WHERE currentId = :geoItemId")
    suspend fun getLastUpdateForCurrentForecast(geoItemId: Long): Long

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

    @CoveredByTest
    @Transaction
    suspend fun insertCurrentForecastWithDetails(
        geo: GeoEntity,
        current: CurrentWeatherEntity,
        hourly: HourlyEntity,
        daily: DailyEntity,
    ) {
        val geoId = insertGeo(
            geo.geoItemId,
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
