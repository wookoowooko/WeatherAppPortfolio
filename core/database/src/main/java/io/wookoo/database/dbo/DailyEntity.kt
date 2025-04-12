package io.wookoo.database.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    "daily",
    foreignKeys = [
        ForeignKey(
            entity = GeoEntity::class,
            parentColumns = ["geo_name_id"],
            childColumns = ["dailyId"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["dailyId"])]
)
data class DailyEntity(

    @PrimaryKey(autoGenerate = true)
    val id: Long,

    @ColumnInfo("dailyId")
    var dailyId: Long,

    @ColumnInfo("sunrise")
    val sunrise: List<Long>,

    @ColumnInfo("sunset")
    val sunset: List<Long>
)
