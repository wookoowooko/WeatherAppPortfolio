package io.wookoo.database.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    "hourly",
    foreignKeys = [
        ForeignKey(
            entity = GeoEntity::class,
            parentColumns = ["geo_name_id"],
            childColumns = ["hourlyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["hourlyId"])]
)
data class HourlyEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo("hourlyId")
    var hourlyId: Long,

    @ColumnInfo("time")
    val time: List<Long>,

    @ColumnInfo("temperature_2m")
    val temperature: List<Float>,

    @ColumnInfo("weather_code")
    val weatherCode: List<Int>,

    @ColumnInfo("is_day")
    val isDay: List<Int>,
)
