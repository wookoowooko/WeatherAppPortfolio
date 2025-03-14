package io.wookoo.database.dbo.weekly

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "weekly_weather")
data class WeeklyWeatherEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("geo_name_id")
    val geoNameId: Long,

    @ColumnInfo("is_day")
    val isDay: Boolean,

    @ColumnInfo("time")
    val time: List<Long>,

    @ColumnInfo("weather_code")
    val weatherCode: List<Int>,

    @ColumnInfo("temperature_2m_max")
    val tempMax: List<Double>,

    @ColumnInfo("temperature_2m_min")
    val tempMin: List<Double>,

    @ColumnInfo("apparent_temperature_max")
    val apparentTempMax: List<Double>,

    @ColumnInfo("apparent_temperature_min")
    val apparentTempMin: List<Double>,

    @ColumnInfo("sunrise")
    val sunrise: List<Long>,

    @ColumnInfo("sunset")
    val sunset: List<Long>,

    @ColumnInfo("daylight_duration")
    val dayLightDuration: List<Double>,

    @ColumnInfo("sunshine_duration")
    val sunshineDuration: List<Double>,

    @ColumnInfo("uv_index_max")
    val uvIndexMax: List<Double>,

    @ColumnInfo("precipitation_sum")
    val precipitationSum: List<Double>,

    @ColumnInfo("rain_sum")
    val rainSum: List<Double>,

    @ColumnInfo("showers_sum")
    val showersSum: List<Double>,

    @ColumnInfo("snowfall_sum")
    val snowfallSum: List<Double>,

    @ColumnInfo("precipitation_probability_max")
    val precipitationProbabilityMax: List<Double>,

    @ColumnInfo("wind_speed_10m_max")
    val windSpeedMax: List<Double>,

    @ColumnInfo("wind_gusts_10m_max")
    val windGustsMax: List<Double>,

    @ColumnInfo("wind_direction_10m_dominant")
    val windDirectionMax: List<Double>,
)
