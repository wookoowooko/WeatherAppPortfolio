package io.wookoo.common.ext

import android.content.res.Resources
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import io.wookoo.models.units.WeatherCondition

fun getWeatherDrawableGradient(condition: WeatherCondition): GradientDrawable {
    val colors = when (condition) {
        WeatherCondition.CLEAR_SKY_0 -> intArrayOf(0xFF5896FD.toInt(), 0x995896FD.toInt())
        WeatherCondition.PARTLY_CLOUDY_1_OR_2 -> intArrayOf(0xFF8ABEC4.toInt(), 0xFF87CEEB.toInt())
        WeatherCondition.OVERCAST_3 -> intArrayOf(0xFF778796.toInt(), 0xFF93A7BD.toInt())
        WeatherCondition.FOG_45_OR_48 -> intArrayOf(0xFF94C9E0.toInt(), 0xFFC7C6C6.toInt())

        WeatherCondition.DRIZZLE_LIGHT_51,
        WeatherCondition.DRIZZLE_MODERATE_53,
        WeatherCondition.DRIZZLE_HEAVY_55 -> intArrayOf(0xFF4682B4.toInt(), 0xFF5F9EA0.toInt())

        WeatherCondition.FREEZING_DRIZZLE_LIGHT_56,
        WeatherCondition.FREEZING_DRIZZLE_HEAVY_57 -> intArrayOf(0xFF5A77CD.toInt(), 0xFF3D518B.toInt())

        WeatherCondition.RAIN_LIGHT_61,
        WeatherCondition.RAIN_MODERATE_63,
        WeatherCondition.HEAVY_RAIN_65 -> intArrayOf(0xFF7795EC.toInt(), 0xFF050525.toInt())

        WeatherCondition.FREEZING_RAIN_LIGHT_66,
        WeatherCondition.FREEZING_RAIN_HEAVY_67 -> intArrayOf(0xFF002B80.toInt(), 0xFF196670.toInt())

        WeatherCondition.SNOW_LIGHT_71,
        WeatherCondition.SNOW_MODERATE_73,
        WeatherCondition.SNOW_HEAVY_75,
        WeatherCondition.SNOW_GRAINS_77 -> intArrayOf(0xFF144179.toInt(), 0xFFAFC5E0.toInt())

        WeatherCondition.RAIN_SHOWERS_LIGHT_80,
        WeatherCondition.RAIN_SHOWERS_MODERATE_81,
        WeatherCondition.RAIN_SHOWERS_HEAVY_82 -> intArrayOf(0xFF1173D2.toInt(), 0xFF2C3F49.toInt())

        WeatherCondition.SNOW_SHOWERS_LIGHT_85,
        WeatherCondition.SNOW_SHOWERS_HEAVY_86 -> intArrayOf(0xFFCAD0D5.toInt(), 0xFF828286.toInt())

        WeatherCondition.THUNDERSTORM_95,
        WeatherCondition.THUNDERSTORM_HAIL_96_OR_99 -> intArrayOf(0xFF06153D.toInt(), 0xFF000000.toInt())

        WeatherCondition.UNKNOWN -> intArrayOf(0xFFA9A9A9.toInt(), 0xFF808080.toInt())
    }

    return GradientDrawable(
        GradientDrawable.Orientation.TOP_BOTTOM,
        colors
    ).apply {
        cornerRadius = 50f.dpToPx()
    }
}

private fun Float.dpToPx(): Float {
    return TypedValue.applyDimension(
        TypedValue.COMPLEX_UNIT_DIP,
        this,
        Resources.getSystem().displayMetrics
    )
}
