package io.wookoo.database.daos

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import io.wookoo.domain.annotations.CoveredByTest

@Dao
interface DeleteForecastsDao {

    @Transaction
    suspend fun deleteCityWithCurrentAndWeeklyForecasts(geoItemId: Long) {
        deleteWeeklyForecastByGeoId(geoItemId)
        deleteCurrentForecastEntryByGeoId(geoItemId)
    }

    @CoveredByTest
    @Query("DELETE FROM weekly_weather WHERE geo_name_id = :geoItemId")
    suspend fun deleteWeeklyForecastByGeoId(geoItemId: Long)

    @CoveredByTest
    @Query("DELETE FROM geo_entity WHERE geo_name_id = :geoItemId")
    suspend fun deleteCurrentForecastEntryByGeoId(geoItemId: Long)
}
