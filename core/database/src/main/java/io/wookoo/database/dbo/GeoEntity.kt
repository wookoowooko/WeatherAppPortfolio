package io.wookoo.database.dbo

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "geo_entity")
data class GeoEntity(

    @PrimaryKey(autoGenerate = false)
    @ColumnInfo("geo_name_id")
    val geoItemId: Long,

    @ColumnInfo("city_name")
    val cityName: String,

    @ColumnInfo("country_name")
    val countryName: String,

    @ColumnInfo("utc_offset_seconds")
    val utcOffsetSeconds: Long,

    @ColumnInfo("is_current")
    val isCurrent: Boolean = false
)
