package io.wookoo.database.daos

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import io.wookoo.database.dbo.weekly.WeeklyWeatherEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface WeeklyWeatherDao {

    @Transaction
    @Query("SELECT * FROM weekly_weather WHERE geo_name_id = :geoItemId")
    fun getWeeklyForecastByGeoItemId(geoItemId: Long): Flow<WeeklyWeatherEntity?>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWeeklyForecast(weeklyWeatherEntity: WeeklyWeatherEntity)

    @Query("SELECT last_update FROM weekly_weather WHERE geo_name_id = :geoItemId")
    suspend fun getLastUpdateForWeeklyForecast(geoItemId: Long): Long
}
