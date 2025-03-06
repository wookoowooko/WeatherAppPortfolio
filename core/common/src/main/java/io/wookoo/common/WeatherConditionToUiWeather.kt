package io.wookoo.common

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import io.wookoo.domain.enums.WeatherCondition

typealias WeatherIconDrawable = Int
typealias WeatherNameStringRes = Int

@StringRes
@DrawableRes
fun WeatherCondition.toUiWeather(isDay: Boolean): Pair<WeatherIconDrawable, WeatherNameStringRes> {
    if (isDay) {
        return when (this) {
            WeatherCondition.CLEAR_SKY_0 -> io.wookoo.design.system.R.drawable.ic_clear_sky to io.wookoo.androidresources.R.string.clear_sky
            WeatherCondition.PARTLY_CLOUDY_1_OR_2 -> io.wookoo.design.system.R.drawable.ic_partly_cloudy to io.wookoo.androidresources.R.string.partly_cloudy
            WeatherCondition.OVERCAST_3 -> io.wookoo.design.system.R.drawable.ic_overcast to io.wookoo.androidresources.R.string.overcast
            WeatherCondition.FOG_45_OR_48 -> io.wookoo.design.system.R.drawable.ic_fog to io.wookoo.androidresources.R.string.fog
            WeatherCondition.DRIZZLE_LIGHT_51 -> io.wookoo.design.system.R.drawable.ic_drizzle_light to io.wookoo.androidresources.R.string.drizzle_light
            WeatherCondition.DRIZZLE_MODERATE_53 -> io.wookoo.design.system.R.drawable.ic_drizzle_moderate to io.wookoo.androidresources.R.string.drizzle_moderate
            WeatherCondition.DRIZZLE_HEAVY_55 -> io.wookoo.design.system.R.drawable.ic_drizzle_heavy to io.wookoo.androidresources.R.string.drizzle_heavy
            WeatherCondition.FREEZING_DRIZZLE_LIGHT_56 -> io.wookoo.design.system.R.drawable.ic_freezing_drizzle_light to io.wookoo.androidresources.R.string.freezing_drizzle_light
            WeatherCondition.FREEZING_DRIZZLE_HEAVY_57 -> io.wookoo.design.system.R.drawable.ic_freezing_drizzle_heavy to io.wookoo.androidresources.R.string.freezing_drizzle_heavy
            WeatherCondition.RAIN_LIGHT_61 -> io.wookoo.design.system.R.drawable.ic_rain_light to io.wookoo.androidresources.R.string.rain_light
            WeatherCondition.RAIN_MODERATE_63 -> io.wookoo.design.system.R.drawable.ic_rain_moderate to io.wookoo.androidresources.R.string.rain_moderate
            WeatherCondition.HEAVY_RAIN_65 -> io.wookoo.design.system.R.drawable.ic_rain_heavy to io.wookoo.androidresources.R.string.rain_heavy
            WeatherCondition.FREEZING_RAIN_LIGHT_66 -> io.wookoo.design.system.R.drawable.ic_freezing_rain_light to io.wookoo.androidresources.R.string.freezing_rain_light
            WeatherCondition.FREEZING_RAIN_HEAVY_67 -> io.wookoo.design.system.R.drawable.ic_freezing_rain_heavy to io.wookoo.androidresources.R.string.freezing_rain_heavy
            WeatherCondition.SNOW_LIGHT_71 -> io.wookoo.design.system.R.drawable.ic_snow_light to io.wookoo.androidresources.R.string.snow_light
            WeatherCondition.SNOW_MODERATE_73 -> io.wookoo.design.system.R.drawable.ic_snow_moderate to io.wookoo.androidresources.R.string.snow_moderate
            WeatherCondition.SNOW_HEAVY_75 -> io.wookoo.design.system.R.drawable.ic_snow_heavy to io.wookoo.androidresources.R.string.snow_heavy
            WeatherCondition.SNOW_GRAINS_77 -> io.wookoo.design.system.R.drawable.ic_snow_grains to io.wookoo.androidresources.R.string.snow_grains
            WeatherCondition.RAIN_SHOWERS_LIGHT_80 -> io.wookoo.design.system.R.drawable.ic_rain_showers_light to io.wookoo.androidresources.R.string.rain_showers_light
            WeatherCondition.RAIN_SHOWERS_MODERATE_81 -> io.wookoo.design.system.R.drawable.ic_rain_showers_moderate to io.wookoo.androidresources.R.string.rain_showers_moderate
            WeatherCondition.RAIN_SHOWERS_HEAVY_82 -> io.wookoo.design.system.R.drawable.ic_rain_showers_heavy to io.wookoo.androidresources.R.string.rain_showers_heavy
            WeatherCondition.SNOW_SHOWERS_LIGHT_85 -> io.wookoo.design.system.R.drawable.ic_snow_showers_light to io.wookoo.androidresources.R.string.snow_showers_light
            WeatherCondition.SNOW_SHOWERS_HEAVY_86 -> io.wookoo.design.system.R.drawable.ic_snow_showers_heavy to io.wookoo.androidresources.R.string.snow_showers_heavy
            WeatherCondition.THUNDERSTORM_95 -> io.wookoo.design.system.R.drawable.ic_thunderstorm to io.wookoo.androidresources.R.string.thunderstorm
            WeatherCondition.THUNDERSTORM_HAIL_96_OR_99 -> io.wookoo.design.system.R.drawable.ic_thunderstorm_hail to io.wookoo.androidresources.R.string.thunderstorm_hail
            WeatherCondition.UNKNOWN -> io.wookoo.design.system.R.drawable.ic_weather_error to io.wookoo.androidresources.R.string.weather_error
        }
    } else {
        return when (this) {
            WeatherCondition.CLEAR_SKY_0 -> io.wookoo.design.system.R.drawable.ic_clear_sky_night to io.wookoo.androidresources.R.string.clear_sky
            WeatherCondition.PARTLY_CLOUDY_1_OR_2 -> io.wookoo.design.system.R.drawable.ic_partly_cloudy_night to io.wookoo.androidresources.R.string.partly_cloudy
            WeatherCondition.OVERCAST_3 -> io.wookoo.design.system.R.drawable.ic_overcast to io.wookoo.androidresources.R.string.overcast
            WeatherCondition.FOG_45_OR_48 -> io.wookoo.design.system.R.drawable.ic_fog to io.wookoo.androidresources.R.string.fog
            WeatherCondition.DRIZZLE_LIGHT_51 -> io.wookoo.design.system.R.drawable.ic_drizzle_light_night to io.wookoo.androidresources.R.string.drizzle_light
            WeatherCondition.DRIZZLE_MODERATE_53 -> io.wookoo.design.system.R.drawable.ic_drizzle_moderate_night to io.wookoo.androidresources.R.string.drizzle_moderate
            WeatherCondition.DRIZZLE_HEAVY_55 -> io.wookoo.design.system.R.drawable.ic_drizzle_heavy_night to io.wookoo.androidresources.R.string.drizzle_heavy
            WeatherCondition.FREEZING_DRIZZLE_LIGHT_56 -> io.wookoo.design.system.R.drawable.ic_freezing_drizzle_light to io.wookoo.androidresources.R.string.freezing_drizzle_light
            WeatherCondition.FREEZING_DRIZZLE_HEAVY_57 -> io.wookoo.design.system.R.drawable.ic_freezing_drizzle_heavy to io.wookoo.androidresources.R.string.freezing_drizzle_heavy
            WeatherCondition.RAIN_LIGHT_61 -> io.wookoo.design.system.R.drawable.ic_rain_light to io.wookoo.androidresources.R.string.rain_light
            WeatherCondition.RAIN_MODERATE_63 -> io.wookoo.design.system.R.drawable.ic_rain_moderate to io.wookoo.androidresources.R.string.rain_moderate
            WeatherCondition.HEAVY_RAIN_65 -> io.wookoo.design.system.R.drawable.ic_rain_heavy to io.wookoo.androidresources.R.string.rain_heavy
            WeatherCondition.FREEZING_RAIN_LIGHT_66 -> io.wookoo.design.system.R.drawable.ic_freezing_rain_light to io.wookoo.androidresources.R.string.freezing_rain_light
            WeatherCondition.FREEZING_RAIN_HEAVY_67 -> io.wookoo.design.system.R.drawable.ic_freezing_rain_heavy to io.wookoo.androidresources.R.string.freezing_rain_heavy
            WeatherCondition.SNOW_LIGHT_71 -> io.wookoo.design.system.R.drawable.ic_snow_light to io.wookoo.androidresources.R.string.snow_light
            WeatherCondition.SNOW_MODERATE_73 -> io.wookoo.design.system.R.drawable.ic_snow_moderate to io.wookoo.androidresources.R.string.snow_moderate
            WeatherCondition.SNOW_HEAVY_75 -> io.wookoo.design.system.R.drawable.ic_snow_heavy to io.wookoo.androidresources.R.string.snow_heavy
            WeatherCondition.SNOW_GRAINS_77 -> io.wookoo.design.system.R.drawable.ic_snow_grains to io.wookoo.androidresources.R.string.snow_grains
            WeatherCondition.RAIN_SHOWERS_LIGHT_80 -> io.wookoo.design.system.R.drawable.ic_rain_showers_light to io.wookoo.androidresources.R.string.rain_showers_light
            WeatherCondition.RAIN_SHOWERS_MODERATE_81 -> io.wookoo.design.system.R.drawable.ic_rain_showers_moderate to io.wookoo.androidresources.R.string.rain_showers_moderate
            WeatherCondition.RAIN_SHOWERS_HEAVY_82 -> io.wookoo.design.system.R.drawable.ic_rain_showers_heavy to io.wookoo.androidresources.R.string.rain_showers_heavy
            WeatherCondition.SNOW_SHOWERS_LIGHT_85 -> io.wookoo.design.system.R.drawable.ic_snow_showers_light to io.wookoo.androidresources.R.string.snow_showers_light
            WeatherCondition.SNOW_SHOWERS_HEAVY_86 -> io.wookoo.design.system.R.drawable.ic_snow_showers_heavy to io.wookoo.androidresources.R.string.snow_showers_heavy
            WeatherCondition.THUNDERSTORM_95 -> io.wookoo.design.system.R.drawable.ic_thunderstorm to io.wookoo.androidresources.R.string.thunderstorm
            WeatherCondition.THUNDERSTORM_HAIL_96_OR_99 -> io.wookoo.design.system.R.drawable.ic_thunderstorm_hail to io.wookoo.androidresources.R.string.thunderstorm_hail
            WeatherCondition.UNKNOWN -> io.wookoo.design.system.R.drawable.ic_weather_error to io.wookoo.androidresources.R.string.weather_error
        }
    }
}
