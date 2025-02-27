package io.wookoo.domain.usecases

import io.wookoo.domain.enums.WeatherCondition
import javax.inject.Inject

class ConvertWeatherCodeToEnumUseCase @Inject constructor() {

    operator fun invoke(weatherCode: Int): WeatherCondition {
        return when (weatherCode) {
            0 -> WeatherCondition.CLEAR_SKY_0
            1, 2 -> WeatherCondition.PARTLY_CLOUDY_1_OR_2
            3 -> WeatherCondition.OVERCAST_3
            45, 48 -> WeatherCondition.FOG_45_OR_48
            51 -> WeatherCondition.DRIZZLE_LIGHT_51
            53 -> WeatherCondition.DRIZZLE_MODERATE_53
            55 -> WeatherCondition.DRIZZLE_HEAVY_55
            56 -> WeatherCondition.FREEZING_DRIZZLE_LIGHT_56
            57 -> WeatherCondition.FREEZING_DRIZZLE_HEAVY_57
            61 -> WeatherCondition.RAIN_LIGHT_61
            63 -> WeatherCondition.RAIN_MODERATE_63
            65 -> WeatherCondition.HEAVY_RAIN_65
            66 -> WeatherCondition.FREEZING_RAIN_LIGHT_66
            67 -> WeatherCondition.FREEZING_RAIN_HEAVY_67
            71 -> WeatherCondition.SNOW_LIGHT_71
            73 -> WeatherCondition.SNOW_MODERATE_73
            75 -> WeatherCondition.SNOW_HEAVY_75
            77 -> WeatherCondition.SNOW_GRAINS_77
            80 -> WeatherCondition.RAIN_SHOWERS_LIGHT_80
            81 -> WeatherCondition.RAIN_SHOWERS_MODERATE_81
            82 -> WeatherCondition.RAIN_SHOWERS_HEAVY_82
            85 -> WeatherCondition.SNOW_SHOWERS_LIGHT_85
            86 -> WeatherCondition.SNOW_SHOWERS_HEAVY_86
            95 -> WeatherCondition.THUNDERSTORM_95
            96, 99 -> WeatherCondition.THUNDERSTORM_HAIL_96_OR_99
            else -> WeatherCondition.UNKNOWN
        }
    }
}
