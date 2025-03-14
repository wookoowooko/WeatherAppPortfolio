package io.wookoo.database.relations

import androidx.room.Embedded
import androidx.room.Relation
import io.wookoo.database.dbo.CurrentWeatherEntity
import io.wookoo.database.dbo.DailyEntity
import io.wookoo.database.dbo.HourlyEntity
import io.wookoo.database.dbo.GeoEntity

data class WeatherWithDetails(
    @Embedded val geo: GeoEntity,

    @Relation(
        parentColumn = "geo_name_id",
        entityColumn = "currentId"
    )
    val current: CurrentWeatherEntity,

    @Relation(
        parentColumn = "geo_name_id",
        entityColumn = "hourlyId"
    )
    val hourly: HourlyEntity,

    @Relation(
        parentColumn = "geo_name_id",
        entityColumn = "dailyId"
    )
    val daily: DailyEntity,
)
