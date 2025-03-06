package io.wookoo.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.wookoo.domain.enums.WeatherProperty

private typealias WeatherPropStringResId = Int
private typealias WeatherPropDrawableResId = Int

@StringRes
@DrawableRes
fun WeatherProperty.asResourceMap(): Map<WeatherPropStringResId, WeatherPropDrawableResId> {
    return when (this) {
        WeatherProperty.SUNRISE -> mapOf(
            io.wookoo.androidresources.R.string.sunrise_time to io.wookoo.design.system.R.drawable.ic_sunrise
        )

        WeatherProperty.SUNSET -> mapOf(
            io.wookoo.androidresources.R.string.sunset_time to io.wookoo.design.system.R.drawable.ic_sunset
        )

        WeatherProperty.DAYLIGHT_DURATION -> mapOf(
            io.wookoo.androidresources.R.string.daylight_duration to io.wookoo.design.system.R.drawable.ic_daylight_duration
        )

        WeatherProperty.SUNSHINE_DURATION -> mapOf(
            io.wookoo.androidresources.R.string.sunshine_duration to io.wookoo.design.system.R.drawable.ic_sunshine_duration
        )

        WeatherProperty.UV_INDEX_MAX -> mapOf(
            io.wookoo.androidresources.R.string.maximum_uv_index to io.wookoo.design.system.R.drawable.ic_uv_index
        )

        WeatherProperty.PRECIPITATION_SUM -> mapOf(
            io.wookoo.androidresources.R.string.total_precipitation to io.wookoo.design.system.R.drawable.ic_prec_max
        )

        WeatherProperty.RAIN_SUM -> mapOf(
            io.wookoo.androidresources.R.string.total_rainfall to io.wookoo.design.system.R.drawable.ic_rain_sum
        )

        WeatherProperty.SHOWERS_SUM -> mapOf(
            io.wookoo.androidresources.R.string.total_showers to io.wookoo.design.system.R.drawable.ic_showers_sum
        )

        WeatherProperty.SNOWFALL_SUM -> mapOf(
            io.wookoo.androidresources.R.string.total_snowfall to io.wookoo.design.system.R.drawable.ic_snowfall_sum
        )

        WeatherProperty.PRECIPITATION_PROBABILITY_MAX -> mapOf(
            io.wookoo.androidresources.R.string.precipitation_probability to io.wookoo.design.system.R.drawable.ic_precipitation
        )

        WeatherProperty.WIND_SPEED_10_MAX -> mapOf(
            io.wookoo.androidresources.R.string.maximum_wind_speed_10m to io.wookoo.design.system.R.drawable.ic_wind_speed
        )

        WeatherProperty.WIND_GUSTS_10_MAX -> mapOf(
            io.wookoo.androidresources.R.string.maximum_wind_gusts_10m to io.wookoo.design.system.R.drawable.ic_wind_gust
        )

        WeatherProperty.WIND_DIRECTION_10 -> mapOf(
            io.wookoo.androidresources.R.string.wind_direction_10m to io.wookoo.design.system.R.drawable.ic_wind_direction
        )

        WeatherProperty.UNDEFINED -> mapOf(
            io.wookoo.androidresources.R.string.undetected to io.wookoo.design.system.R.drawable.ic_weather_error
        )
    }
}
