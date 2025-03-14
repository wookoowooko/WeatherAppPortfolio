package io.wookoo.database.converters

import androidx.room.TypeConverter

class PrimitiveConverters {

    @TypeConverter
    fun fromLongList(value: List<Long>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toLongList(value: String): List<Long> {
        return value.split(",").map { it.toLong() }
    }

    @TypeConverter
    fun fromFloatList(value: List<Float>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toFloatList(value: String): List<Float> {
        return value.split(",").map { it.toFloat() }
    }

    @TypeConverter
    fun fromIntList(value: List<Int>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toIntList(value: String): List<Int> {
        return value.split(",").map { it.toInt() }
    }

    @TypeConverter
    fun fromDoubleList(value: List<Double>): String {
        return value.joinToString(",")
    }

    @TypeConverter
    fun toDoubleList(value: String): List<Double> {
        return value.split(",").map { it.toDouble() }
    }
}
