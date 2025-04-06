package io.wookoo.common.ext

import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import io.wookoo.models.units.WeatherCondition

fun getWeatherGradient(condition: WeatherCondition): Brush {
    return Brush.linearGradient(
        colors = when (condition) {
            WeatherCondition.CLEAR_SKY_0 -> listOf(
                Color(0xFF5896FD), // Light Sky Blue
                Color(0xFF5896FD).copy(0.6f) // Light Blue
            )

            WeatherCondition.PARTLY_CLOUDY_1_OR_2 -> listOf(
                Color(0xFF8ABEC4), // Powder Blue
                Color(0xFF87CEEB) // Light Sky Blue
            )

            WeatherCondition.OVERCAST_3 -> listOf(
                Color(0xFF778796), // Light Slate Gray
                Color(0xFF93A7BD) // Slate Gray
            )

            WeatherCondition.FOG_45_OR_48 -> listOf(
                Color(0xFF94C9E0), // Dim Gray
                Color(0xFFC7C6C6) // Gray
            )

            WeatherCondition.DRIZZLE_LIGHT_51,
            WeatherCondition.DRIZZLE_MODERATE_53,
            WeatherCondition.DRIZZLE_HEAVY_55,
            -> listOf(
                Color(0xFF4682B4), // Steel Blue
                Color(0xFF5F9EA0) // Cadet Blue
            )

            WeatherCondition.FREEZING_DRIZZLE_LIGHT_56,
            WeatherCondition.FREEZING_DRIZZLE_HEAVY_57,
            -> listOf(
                Color(0xFF5A77CD), // Slate Blue
                Color(0xFF3D518B) // Dark Slate Blue
            )

            WeatherCondition.RAIN_LIGHT_61,
            WeatherCondition.RAIN_MODERATE_63,
            WeatherCondition.HEAVY_RAIN_65,
            -> listOf(
                Color(0xFF7795EC), // Royal Blue
                Color(0xFF050525) // Dark Blue
            )

            WeatherCondition.FREEZING_RAIN_LIGHT_66,
            WeatherCondition.FREEZING_RAIN_HEAVY_67,
            -> listOf(
                Color(0xFF002B80), // Navy
                Color(0xFF196670) // Midnight Blue
            )

            WeatherCondition.SNOW_LIGHT_71,
            WeatherCondition.SNOW_MODERATE_73,
            WeatherCondition.SNOW_HEAVY_75,
            WeatherCondition.SNOW_GRAINS_77,
            -> listOf(
                Color(0xFF144179), // Light Cyan
                Color(0xFFAFC5E0) // Pale Turquoise
            )

            WeatherCondition.RAIN_SHOWERS_LIGHT_80,
            WeatherCondition.RAIN_SHOWERS_MODERATE_81,
            WeatherCondition.RAIN_SHOWERS_HEAVY_82,
            -> listOf(
                Color(0xFF1173D2), // Dodger Blue
                Color(0xFF2C3F49) // Medium Blue
            )

            WeatherCondition.SNOW_SHOWERS_LIGHT_85,
            WeatherCondition.SNOW_SHOWERS_HEAVY_86,
            -> listOf(
                Color(0xFFCAD0D5), // Alice Blue
                Color(0xFF828286) // Lavender
            )

            WeatherCondition.THUNDERSTORM_95,
            WeatherCondition.THUNDERSTORM_HAIL_96_OR_99,
            -> listOf(
                Color(0xFF06153D), // Dark Blue
                Color(0xFF000000) // Black
            )

            WeatherCondition.UNKNOWN -> listOf(
                Color(0xFFA9A9A9), // Dark Gray
                Color(0xFF808080) // Gray
            )
        }
    )
}
