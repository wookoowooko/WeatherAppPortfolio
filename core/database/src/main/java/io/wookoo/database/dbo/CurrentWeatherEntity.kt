package io.wookoo.database.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = "current_weather",
    foreignKeys = [
        ForeignKey(
            entity = GeoEntity::class,
            parentColumns = ["geo_name_id"],
            childColumns = ["currentId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["currentId"])]
)
data class CurrentWeatherEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo("currentId")
    var currentId: Long,

    @ColumnInfo("time")
    val time: Long,

    @ColumnInfo("temperature_2m")
    val temperature: Double,

    @ColumnInfo("relative_humidity_2m")
    val relativeHumidity: Int,

    @ColumnInfo("apparent_temperature")
    val feelsLike: Double,

    @ColumnInfo("is_day")
    val isDay: Boolean,

    @ColumnInfo("precipitation")
    val precipitation: Double,

    @ColumnInfo("rain")
    val rain: Double,

    @ColumnInfo("showers")
    val showers: Double,

    @ColumnInfo("snowfall")
    val snowfall: Double,

    @ColumnInfo("cloud_cover")
    val cloudCover: Int,

    @ColumnInfo("pressure_msl")
    val pressureMSL: Double,

    @ColumnInfo("wind_direction_10m")
    val windDirection: Int,

    @ColumnInfo("wind_speed_10m")
    val windSpeed: Double,

    @ColumnInfo("wind_gusts_10m")
    val windGusts: Double,

    @ColumnInfo("weather_code")
    val weatherCode: Int,

    @ColumnInfo("last_update")
    val lastUpdate: Long = System.currentTimeMillis()
)
